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

import com.openpojo.business.identity.IdentityFactory;
import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.test.Tester;
import com.openpojo.validation.utils.IdentityHandlerStub;

/**
 * @author oshoukry
 */
public class ToStringTester implements Tester {

  public void run(PojoClass pojoClass) {
    Object instance = RandomFactory.getRandomValue(pojoClass.getClazz());

    IdentityHandlerStub identityHandlerStub = new IdentityHandlerStub(instance);
    identityHandlerStub.setToStringReturn(RandomFactory.getRandomValue(String.class));

    IdentityFactory.registerIdentityHandler(identityHandlerStub);

    Affirm.affirmEquals("Expected string mismatch", identityHandlerStub.getToStringReturn(), instance.toString());
  }
}
