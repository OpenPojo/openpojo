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

  public int generateHashCode(final Object object) {
    throw new UnsupportedOperationException();
  }

}
