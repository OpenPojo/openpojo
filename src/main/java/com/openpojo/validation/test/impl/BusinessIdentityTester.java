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

package com.openpojo.validation.test.impl;

import com.openpojo.business.BusinessIdentity;
import com.openpojo.business.identity.IdentityFactory;
import com.openpojo.business.identity.IdentityHandler;
import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.test.Tester;
import com.openpojo.validation.utils.ValidationHelper;

/**
 * This rules ensures that object.equals(Object) and object.hashCode() are dispatching their calls to BusinessIdentity.
 * Use this tester to if you are using @BusinessKey and passing on your equals and hashCode calls to
 * {@link BusinessIdentity}.
 *
 * This Tester is NOT thread safe.
 *
 * @author oshoukry
 */
public final class BusinessIdentityTester implements Tester {

  private Object firstPojoClassInstance;
  private Object secondPojoClassInstance;
  private final IdentityHandlerStub identityHandlerStub = new IdentityHandlerStub();

  public void run(final PojoClass pojoClass) {
    IdentityFactory.registerIdentityHandler(identityHandlerStub);

    firstPojoClassInstance = ValidationHelper.getMostCompleteInstance(pojoClass);
    secondPojoClassInstance = ValidationHelper.getMostCompleteInstance(pojoClass);

    // check one way
    identityHandlerStub.areEqualReturn = RandomFactory.getRandomValue(Boolean.class);
    checkEquality();

    identityHandlerStub.areEqualReturn = !identityHandlerStub.areEqualReturn;
    checkEquality();

    identityHandlerStub.doGenerateReturn = RandomFactory.getRandomValue(Integer.class);
    checkHashCode();

    identityHandlerStub.doGenerateReturn = RandomFactory.getRandomValue(Integer.class);
    checkHashCode();

    IdentityFactory.unregisterIdentityHandler(identityHandlerStub);
  }

  private void checkHashCode() {
    Affirm.affirmTrue(String.format("Class=[%s] not dispatching 'hashCode()' calls to BusinessIdentity",
        firstPojoClassInstance.getClass()), identityHandlerStub.doGenerateReturn == firstPojoClassInstance.hashCode());
  }

  private void checkEquality() {
    Affirm.affirmTrue(String.format("Class=[%s] not dispatching 'equals()' calls to BusinessIdentity",
        firstPojoClassInstance.getClass()), identityHandlerStub.areEqualReturn == firstPojoClassInstance.equals(secondPojoClassInstance));
  }

  private class IdentityHandlerStub implements IdentityHandler {
    private boolean areEqualReturn;
    private int doGenerateReturn;

    public boolean areEqual(final Object first, final Object second) {
      return areEqualReturn;
    }

    public void validate(final Object object) {
    }

    public int generateHashCode(final Object object) {
      return doGenerateReturn;
    }

    public boolean handlerFor(final Object object) {
      return object == firstPojoClassInstance || object == secondPojoClassInstance;
    }
  }

}
