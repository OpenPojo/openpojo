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

package com.openpojo.validation.test.impl;

import com.openpojo.business.BusinessIdentity;
import com.openpojo.business.identity.IdentityFactory;
import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.test.Tester;
import com.openpojo.validation.utils.IdentityHandlerStub;
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

  public void run(final PojoClass pojoClass) {
    Object instance1 = ValidationHelper.getMostCompleteInstance(pojoClass);
    Object instance2 = ValidationHelper.getMostCompleteInstance(pojoClass);

    IdentityHandlerStub identityHandlerStub = new IdentityHandlerStub(instance1, instance2);
    IdentityFactory.registerIdentityHandler(identityHandlerStub);

    // check one way
    identityHandlerStub.setAreEqualReturn(RandomFactory.getRandomValue(Boolean.class));
    checkEquality(instance1, instance2, identityHandlerStub);

    identityHandlerStub.setAreEqualReturn(!identityHandlerStub.getAreEqualReturn());
    checkEquality(instance1, instance2, identityHandlerStub);

    identityHandlerStub.setHashCodeReturn(RandomFactory.getRandomValue(Integer.class));
    checkHashCode(instance1, identityHandlerStub);

    identityHandlerStub.setHashCodeReturn(RandomFactory.getRandomValue(Integer.class));
    checkHashCode(instance1, identityHandlerStub);

    IdentityFactory.unregisterIdentityHandler(identityHandlerStub);
  }

  private void checkHashCode(Object firstPojoClassInstance, IdentityHandlerStub identityHandlerStub) {
    Affirm.affirmTrue(String.format("Class=[%s] not dispatching 'hashCode()' calls to BusinessIdentity",
        firstPojoClassInstance.getClass()), identityHandlerStub.getHashCodeReturn() == firstPojoClassInstance.hashCode());
  }

  private void checkEquality(Object instance1, Object instance2, IdentityHandlerStub identityHandlerStub) {
    Affirm.affirmTrue(String.format("Class=[%s] not dispatching 'equals()' calls to BusinessIdentity",
        instance1.getClass()), identityHandlerStub.getAreEqualReturn() == instance1.equals(instance2));
  }

}
