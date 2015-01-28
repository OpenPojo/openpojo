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
import java.util.concurrent.CountDownLatch;

public class IdentityFactoryThreadedness {

    private boolean multiThreaded = true;
    private Method method;

    public IdentityFactoryThreadedness(Method method) {
        this.method = method;
    }

    void execute(int numberOfThreads) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        Thread[] threads = new Thread[numberOfThreads];
        for (int i = 0; i < numberOfThreads; i++) {
            BarrierBasedIdentityHandler barrierBasedIdentityHandler = new BarrierBasedIdentityHandler(latch);
            threads[i] = new Thread(new MethodInvokeJob(null, method, barrierBasedIdentityHandler));
            threads[i].start();
        }

        while (!threadsFinished(threads)) {
            Thread.sleep(10);
            if (oneWaitingAndOthersBlocked(threads)) {
                interruptThreads(threads);
                multiThreaded = false;
            }
        }
    }

    public boolean isMultiThreaded() {
        return multiThreaded;
    }

    private boolean threadsFinished(Thread [] threads) {
        boolean areTerminated = true;
        for (Thread thread : threads) {
            areTerminated &= thread.getState() == Thread.State.TERMINATED;
        }
        return areTerminated;
    }

    private boolean oneWaitingAndOthersBlocked(Thread [] threads) {
        int threadsWaiting = 0;
        int threadsBlocked = 0;
        for (Thread thread : threads) {
            if (thread.getState() == Thread.State.BLOCKED) {
                threadsBlocked++;
            }
            if (thread.getState() == Thread.State.WAITING) {
                threadsWaiting++;
            }
        }
        return (threadsWaiting == 1 && (threadsBlocked == threads.length - 1));
    }

    private void interruptThreads(Thread[] threads) {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }
}