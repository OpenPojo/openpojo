/*
 * Copyright (c) 2010-2015 Osman Shoukry
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License or any
 *    later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.openpojo.business.performance;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.concurrent.CountDownLatch;

import com.openpojo.business.identity.IdentityFactory;
import com.openpojo.business.identity.IdentityHandler;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class IssueTest {

    @Test
    public void identityFactoryGetHandler_MustAllowAccessWhileBeingModified() throws InterruptedException {

        int count = 50000;
        CountDownLatch startSignal = new CountDownLatch(2);

        RegisterUnRegisterJob firstJob = new RegisterUnRegisterJob(startSignal, count);
        Thread firstWorker = new Thread(firstJob);
        firstWorker.start();

        RegisterUnRegisterJob secondJob = new RegisterUnRegisterJob(startSignal, count);
        Thread secondWorker = new Thread(secondJob);
        secondWorker.start();

        firstWorker.join();
        secondWorker.join();

        boolean firstJobResult = firstJob.hasCompletedSuccessfully();
        boolean secondJobResult = secondJob.hasCompletedSuccessfully();
        Assert.assertTrue(String.format("Threads failed to completed [1: %s, 2: %s]",firstJobResult, secondJobResult),
                firstJobResult && secondJobResult);
    }

    @Test
    public void identityFactoryUnRegister_MustBeMultiThreaded() throws NoSuchMethodException {
        Method unregisterMethod = getIdentityFactoryDeclaredMethod("unregisterIdentityHandler", IdentityHandler.class);
        verifyMethodIsSynchronized(unregisterMethod);
    }

    @Test
    public void identityFactoryRegister_MustBeMultiThreaded() throws NoSuchMethodException {
        Method registerMethod = getIdentityFactoryDeclaredMethod("registerIdentityHandler", IdentityHandler.class);
        verifyMethodIsSynchronized(registerMethod);
    }

    private void verifyMethodIsSynchronized(Method method) {
        Assert.assertTrue(Modifier.isSynchronized(method.getModifiers()));
    }

    @Test
    public void identityFactoryGetHandler_MustBeMultiThreaded() throws InterruptedException, NoSuchMethodException {
        CountDownLatch latch = new CountDownLatch(0);
        BarrierBasedIdentityHandler first = new BarrierBasedIdentityHandler(latch);
        BarrierBasedIdentityHandler second = new BarrierBasedIdentityHandler(latch);
        IdentityFactory.registerIdentityHandler(first);
        IdentityFactory.registerIdentityHandler(second);

        int numberOfThreads = 2;
        latch = new CountDownLatch(numberOfThreads);
        first.setLatch(latch);
        second.setLatch(latch);

        Method getHandlerForMethod = getIdentityFactoryDeclaredMethod("getIdentityHandler", Object.class);
        verifyMethodIsThreaded(numberOfThreads, getHandlerForMethod);
    }

    private void verifyMethodIsThreaded(int numberOfThreads, Method method) throws InterruptedException {
        IdentityFactoryThreadedness identityFactory = new IdentityFactoryThreadedness(method);
        identityFactory.execute(numberOfThreads);
        Assert.assertTrue("IdentityFactory." + method.getName() + " is not MultiThreaded!!", identityFactory.isMultiThreaded());
    }

    private Method getIdentityFactoryDeclaredMethod(String methodName, Class<?>... parameters) throws NoSuchMethodException {
        return IdentityFactory.class.getDeclaredMethod(methodName, parameters);
    }


}
