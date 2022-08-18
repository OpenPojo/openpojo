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

package com.openpojo.issues.issue113;

import com.openpojo.issues.issue113.sample.AClassWithBufferedImage;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.Test;

import static com.openpojo.reflection.impl.PojoClassFactory.getPojoClass;

/**
 * @author oshoukry
 */
public class IssueTest {

  @Test
  public void testGetterAndSetter() {
     Validator validator = ValidatorBuilder
         .create()
         .with(new GetterMustExistRule())
         .with(new GetterTester())
         .with(new SetterMustExistRule())
         .with(new SetterTester())
         .build();
    validator.validate(getPojoClass(AClassWithBufferedImage.class));
  }
}
