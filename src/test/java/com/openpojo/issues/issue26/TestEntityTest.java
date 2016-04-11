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

package com.openpojo.issues.issue26;

import java.util.List;

import com.openpojo.log.utils.MessageFormatter;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.rule.impl.BusinessKeyMustExistRule;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.NoNestedClassRule;
import com.openpojo.validation.rule.impl.NoPrimitivesRule;
import com.openpojo.validation.rule.impl.NoPublicFieldsRule;
import com.openpojo.validation.rule.impl.NoStaticExceptFinalRule;
import com.openpojo.validation.test.impl.BusinessIdentityTester;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.Before;
import org.junit.Test;

public class TestEntityTest {
  private static final int EXPECTED_CLASS_COUNT = 2;

  private static final String POJO_PACKAGE = "com.openpojo.issues.issue26.pojo";

  private List<PojoClass> pojoClasses;
  private Validator pojoValidator;

  @Before
  public void setup() {
    pojoClasses = PojoClassFactory.getPojoClasses(POJO_PACKAGE);

    ValidatorBuilder validatorBuilder = ValidatorBuilder.create();

    // Create Rules to validate structure for POJO_PACKAGE
    validatorBuilder.with(new NoPublicFieldsRule());
    validatorBuilder.with(new NoPrimitivesRule());
    validatorBuilder.with(new NoStaticExceptFinalRule());
    validatorBuilder.with(new GetterMustExistRule());
    validatorBuilder.with(new NoNestedClassRule());
    validatorBuilder.with(new BusinessKeyMustExistRule());

    // Create Testers to validate behavior for POJO_PACKAGE
    validatorBuilder.with(new SetterTester());
    validatorBuilder.with(new GetterTester());
    validatorBuilder.with(new BusinessIdentityTester());

    pojoValidator = validatorBuilder.build();
  }

  @Test
  public void ensureExpectedPojoCount() {
    Affirm.affirmEquals(MessageFormatter.format("Classes added / removed? [{0}]", pojoClasses), EXPECTED_CLASS_COUNT,
        pojoClasses.size());
  }

  @Test
  public void testPojoStructureAndBehavior() {
    pojoValidator.validate(pojoClasses);
  }

}
