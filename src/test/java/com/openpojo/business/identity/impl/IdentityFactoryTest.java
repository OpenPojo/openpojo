/*
 * Copyright (c) 2010-2016 Osman Shoukry
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

package com.openpojo.business.identity.impl;

import java.util.ArrayList;
import java.util.List;

import com.openpojo.business.exception.BusinessException;
import com.openpojo.business.identity.IdentityFactory;
import com.openpojo.business.identity.IdentityHandler;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class IdentityFactoryTest {

  @Test(expected = java.lang.IllegalArgumentException.class)
  public void shouldNotAllowRegistrationOfNullHandler() {
    IdentityFactory.registerIdentityHandler(null);
  }

  @Test
  public void whenNoIdentityHandlerFoundShouldThrowException() {
    Object o = new Object();
    List<IdentityHandler> identityHandlers = new ArrayList<IdentityHandler>();

    try {
      while (IdentityFactory.getIdentityHandler(o) != null) {
        identityHandlers.add(IdentityFactory.getIdentityHandler(o));
        IdentityFactory.unregisterIdentityHandler(IdentityFactory.getIdentityHandler(o));

      }
    } catch (BusinessException ignored) {
      return;
    } finally {
      // restore setting;
      for (int i = identityHandlers.size() - 1; i >= 0; i--) {
        IdentityFactory.registerIdentityHandler(identityHandlers.get(i));
      }
    }
    Assert.fail("BusinessException should have been thrown");
  }
}
