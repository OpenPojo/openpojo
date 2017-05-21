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

package com.openpojo.issues.issue85;

import java.lang.reflect.Array;

import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.reflection.utils.ObjectToString;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.Before;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class IssueTest {
  private PojoClass pojoClass;

  @Before
  public void setup() {
    pojoClass = PojoClassFactory.getPojoClass(AClassWithPrimitiveArrays.class);
  }

  @Test
  public void ensureClassIsDefinedCorrectly() {
    Validator validator = ValidatorBuilder.create()
        .with(new GetterMustExistRule())
        .with(new SetterMustExistRule())
        .with(new AllPimitivesAsArraysDeclaredRule())
        .with(new GetterTester())
        .with(new SetterTester())
        .build();
    validator.validate(pojoClass);
  }

  @Test
  public void shouldBeAbleToString() {
    for (int i = 0; i < 100 ; i++) {
      AClassWithPrimitiveArrays primitiveArrays = RandomFactory.getRandomValue(AClassWithPrimitiveArrays.class);
      for (PojoField field : pojoClass.getPojoFields()) {
        Affirm.affirmTrue("Expected field to be array but wasn't [" + field + "]", field.isArray());
        Object contents = field.get(primitiveArrays);
        Affirm.affirmTrue("Expected field to not be null but was [" + field + "]", contents != null);
        Affirm.affirmTrue("Expected array to not be empty [" + field + "]", Array.getLength(contents) > 0);
        String expected = field.getName() + "=" + ObjectToString.toString(contents);
        Affirm.affirmTrue("Expected [" + expected + "] to be included in [" + pojoClass.toString(primitiveArrays) + "]",
            pojoClass.toString(primitiveArrays).contains(expected));
      }
    }
  }
}
