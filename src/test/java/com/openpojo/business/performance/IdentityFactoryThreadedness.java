/*
 * Copyright (c) 2010-2018 Osman Shoukry
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

  private boolean threadsFinished(Thread[] threads) {
    boolean areTerminated = true;
    for (Thread thread : threads) {
      areTerminated &= thread.getState() == Thread.State.TERMINATED;
    }
    return areTerminated;
  }

  private boolean oneWaitingAndOthersBlocked(Thread[] threads) {
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