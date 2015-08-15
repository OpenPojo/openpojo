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

package com.openpojo.business.identity.impl;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.openpojo.business.identity.impl.sampleclasses.PojoClassWithHashCodeBusinessIdentity;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.*;
import com.openpojo.validation.test.impl.BusinessIdentityTester;
import com.openpojo.validation.test.impl.DefaultValuesNullTester;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class IdentityFactoryRaceConditionTest {
    @Test
    public void runWithManyThreads() {
        int numberOfThreads = 1500;

        ThreadPoolExecutor tpe = new ThreadPoolExecutor(numberOfThreads, numberOfThreads, 3 * 60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(numberOfThreads) );

        CyclicBarrier cb = new CyclicBarrier(numberOfThreads);
        for (int i = 0; i < numberOfThreads; i++)
            tpe.execute(new Worker(cb));
        tpe.shutdown();

        while (!tpe.isTerminated()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {

            }
        }

        Assert.assertEquals("Some threads failed to complete successfully", numberOfThreads, tpe.getCompletedTaskCount());
    }

    class Worker implements Runnable {
        private final CyclicBarrier cyclicBarrier;

        public Worker(final CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        public void run() {
            try {
                Validator pv = ValidatorBuilder.create()
                        .with(new BusinessKeyMustExistRule())
                        .with(new GetterMustExistRule())
                        .with(new NoFieldShadowingRule())
                        .with(new NoNestedClassRule())
                        .with(new NoPrimitivesRule())
                        .with(new NoPublicFieldsExceptStaticFinalRule())
                        .with(new NoPublicFieldsRule())
                        .with(new NoStaticExceptFinalRule())
                        .with(new SerializableMustHaveSerialVersionUIDRule())
                        .with(new SetterMustExistRule())
                        .with(new BusinessIdentityTester())
                        .with(new DefaultValuesNullTester())
                        .with(new GetterTester())
                        .with(new SetterTester())
                        .build();

                PojoClass pojoClass = PojoClassFactory.getPojoClass(PojoClassWithHashCodeBusinessIdentity.class);
                cyclicBarrier.await();
                pv.validate(pojoClass);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
