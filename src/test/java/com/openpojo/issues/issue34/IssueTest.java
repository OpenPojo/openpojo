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

package com.openpojo.issues.issue34;

import com.openpojo.issues.issue34.sample.ClassWithBooleanFields;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class IssueTest {

  @Test
  public void testBooleanVariations() {
    PojoClass pojoClass = PojoClassFactory.getPojoClass(ClassWithBooleanFields.class);

    Affirm.affirmEquals("Fields must be 4", 4, pojoClass.getPojoFields().size());

    int countOfbooleans = 0;
    int countOfBooleans = 0;

    for (PojoField pojoField : pojoClass.getPojoFields()) {
      if (pojoField.isPrimitive() && pojoField.getType() == boolean.class) {
        countOfbooleans++;
      }
      if (!pojoField.isPrimitive() && pojoField.getType() == Boolean.class) {
        countOfBooleans++;
      }
    }

    Affirm.affirmEquals("2 boolean fields must exist", 2, countOfbooleans);
    Affirm.affirmEquals("2 Boolean fields must exist", 2, countOfBooleans);

    Validator pojoValidator = ValidatorBuilder.create()
        .with(new GetterMustExistRule())
        .with(new SetterMustExistRule())
        .with(new GetterTester())
        .with(new SetterTester())
        .build();

    pojoValidator.validate(pojoClass);
  }
}
