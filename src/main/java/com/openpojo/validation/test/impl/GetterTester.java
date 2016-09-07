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

import java.util.HashSet;
import java.util.Set;

import com.openpojo.log.LoggerFactory;
import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.test.Tester;
import com.openpojo.validation.utils.IdentityHandlerStub;
import com.openpojo.validation.utils.ValidationHelper;

/**
 * Test the getter and ensure it retrieves from the field being tested if and only if it has a getter defined.
 *
 * @author oshoukry
 */
public class GetterTester implements Tester {

  private Set<String> skippedFields = new HashSet<String>();
	
  public void run(final PojoClass pojoClass) {
    final Object classInstance = ValidationHelper.getBasicInstance(pojoClass);
    for (final PojoField fieldEntry : pojoClass.getPojoFields()) {
      if (skippedFields.contains(fieldEntry.getName())) {
        LoggerFactory.getLogger(this.getClass()).debug("Skipping field [{0}]", fieldEntry);
        continue;
      }
		
      if (fieldEntry.hasGetter()) {
        Object value = fieldEntry.get(classInstance);

        if (!fieldEntry.isFinal()) {
          value = RandomFactory.getRandomValue(fieldEntry);
          fieldEntry.set(classInstance, value);
        }

        IdentityHandlerStub.registerIdentityHandlerStubForValue(value);

        LoggerFactory.getLogger(this.getClass()).debug("Testing Field [{0}] with value [{1}]", fieldEntry, value);

        Affirm.affirmEquals("Getter returned non equal value for field=[" + fieldEntry + "]", value,
            fieldEntry.invokeGetter(classInstance));
        IdentityHandlerStub.unregisterIdentityHandlerStubForValue(value);
      } else {
        LoggerFactory.getLogger(this.getClass()).debug("Field [{0}] has no getter skipping", fieldEntry);
      }
    }
  }
  
  /**
   * Instructs GetterTester to skip the field when running pojoClass
   * validation.
   * 
   * @param fieldToBeSkipped
   *            Field name (e.g. Into Person class, to skip getName, provide
   *            "name" as a field to be skipped.
   */
	public void addField(String fieldToBeSkipped) {
		if (fieldToBeSkipped == null || fieldToBeSkipped.trim().isEmpty()) {
			throw new IllegalArgumentException("The argument 'fieldToBeSkipped' cannot be null or empty.");
		}

		this.skippedFields.add(fieldToBeSkipped);
	}
}
