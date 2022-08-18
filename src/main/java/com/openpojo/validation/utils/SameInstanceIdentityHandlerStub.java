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

package com.openpojo.validation.utils;

import com.openpojo.business.identity.IdentityFactory;
import com.openpojo.business.identity.IdentityHandler;

/**
 * @author oshoukry
 */
public class SameInstanceIdentityHandlerStub implements IdentityHandler {
  private Object handlerForObject;

  public static void registerIdentityHandlerStubForValue(Object value) {
    final SameInstanceIdentityHandlerStub identityHandlerStub = new SameInstanceIdentityHandlerStub();
    identityHandlerStub.setHandlerForObject(value);
    IdentityFactory.registerIdentityHandler(identityHandlerStub);
  }

  public static void unregisterIdentityHandlerStubForValue(Object value) {
    IdentityHandler identityHandler = IdentityFactory.getIdentityHandler(value);
    IdentityFactory.unregisterIdentityHandler(identityHandler);
  }

  private void setHandlerForObject(final Object handlerForObject) {
    this.handlerForObject = handlerForObject;
  }

  public boolean areEqual(final Object first, final Object second) {
    return first == second;
  }

  public String toString(Object object) {
    throw new UnsupportedOperationException();
  }

  public void validate(final Object object) {
  }

  public int generateHashCode(final Object object) {
    return System.identityHashCode(object);
  }

  public boolean handlerFor(final Object object) {
    return handlerForObject == object;
  }
}
