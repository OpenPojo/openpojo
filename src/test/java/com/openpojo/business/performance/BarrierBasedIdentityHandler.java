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

import com.openpojo.business.identity.IdentityHandler;

/**
 * @author oshoukry
 */
public class BarrierBasedIdentityHandler implements IdentityHandler {

  private CountDownLatch latch;

  public BarrierBasedIdentityHandler(final CountDownLatch latch) {
    this.latch = latch;
  }

  public void setLatch(final CountDownLatch latch) {
    this.latch = latch;
  }

  public boolean handlerFor(final Object object) {
    waitForOthers();
    return false;
  }

  @Override
  public boolean equals(Object other) {
    waitForOthers();
    return false;
  }

  @Override
  public int hashCode() {
    waitForOthers();
    return System.identityHashCode(this);
  }

  private void waitForOthers() {
    try {
      latch.countDown();
      latch.await();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public void validate(final Object object) {
    throw new UnsupportedOperationException();
  }

  public boolean areEqual(final Object first, final Object second) {
    throw new UnsupportedOperationException();
  }

  public String toString(Object object) {
    throw new UnsupportedOperationException();
  }

  public int generateHashCode(final Object object) {
    throw new UnsupportedOperationException();
  }
}
