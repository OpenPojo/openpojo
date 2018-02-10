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

import com.openpojo.log.LoggerFactory;
import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.test.Tester;
import com.openpojo.validation.utils.SameInstanceIdentityHandlerStub;
import com.openpojo.validation.utils.ValidationHelper;

import static com.openpojo.validation.utils.ToStringHelper.safeToString;

/**
 * Test the getter and ensure it retrieves from the field being tested if and only if it has a getter defined.
 *
 * @author oshoukry
 */
public class GetterTester implements Tester {

  public void run(final PojoClass pojoClass) {
    final Object classInstance = ValidationHelper.getBasicInstance(pojoClass);
    for (final PojoField fieldEntry : pojoClass.getPojoFields()) {
      if (fieldEntry.hasGetter()) {
        Object value = fieldEntry.get(classInstance);

        if (!fieldEntry.isFinal()) {
          value = RandomFactory.getRandomValue(fieldEntry);
          fieldEntry.set(classInstance, value);
        }

        SameInstanceIdentityHandlerStub.registerIdentityHandlerStubForValue(value);

        LoggerFactory.getLogger(this.getClass()).debug("Testing Field [{0}] with value [{1}]", fieldEntry, safeToString(value));

        Affirm.affirmEquals("Getter returned non equal value for field=[" + fieldEntry + "]", value, fieldEntry.invokeGetter(classInstance));
        SameInstanceIdentityHandlerStub.unregisterIdentityHandlerStubForValue(value);
      } else {
        LoggerFactory.getLogger(this.getClass()).debug("Field [{0}] has no getter skipping", fieldEntry);
      }
    }
  }
}
