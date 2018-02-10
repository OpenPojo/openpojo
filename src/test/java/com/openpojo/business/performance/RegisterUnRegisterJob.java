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

import java.util.concurrent.CountDownLatch;

import com.openpojo.business.identity.IdentityFactory;

/**
 * @author oshoukry
 */
public class RegisterUnRegisterJob implements Runnable {

  private final CountDownLatch startSignal;
  private volatile int count;
  private final NoOpIdentityHandler noOpIdentityHandler = new NoOpIdentityHandler();

  public RegisterUnRegisterJob(final CountDownLatch startSignal, final int count) {
    this.startSignal = startSignal;
    this.count = count;
  }

  public void run() {
    startSignal.countDown();
    try {
      startSignal.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }

    try {
      while (count > 0) {
        --count;
        IdentityFactory.registerIdentityHandler(noOpIdentityHandler);
        IdentityFactory.unregisterIdentityHandler(noOpIdentityHandler);
        IdentityFactory.getIdentityHandler(new Object());
      }

    } catch (Throwable t) {
      t.printStackTrace();
    } finally {
      IdentityFactory.unregisterIdentityHandler(noOpIdentityHandler);
    }
  }

  public boolean hasCompletedSuccessfully() {
    return count <= 0;
  }
}
