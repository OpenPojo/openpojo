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

package com.openpojo.issues.issue14enumset;

import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;
import com.openpojo.reflection.filters.FilterChain;
import com.openpojo.reflection.filters.FilterEnum;
import com.openpojo.reflection.filters.FilterPackageInfo;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.utils.log.LogHelper;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.NoPublicFieldsRule;
import com.openpojo.validation.rule.impl.NoStaticExceptFinalRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.DefaultValuesNullTester;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BeanTest {

  private List<PojoClass> pojoClasses;
  private Validator pojoValidator;

  @Before
  public void setup() {
    LogHelper.initializeLoggers();
    PojoClassFilter pojoClassFilter = new FilterChain(new FilterEnum(), new FilterPackageInfo());
    pojoClasses = PojoClassFactory.getPojoClassesRecursively(this.getClass().getPackage().getName() + ".sampleclasses",
        pojoClassFilter);

    ValidatorBuilder validatorBuilder = ValidatorBuilder.create();

    // Create Rules to validate structure for POJO_PACKAGE
    validatorBuilder.with(new NoPublicFieldsRule());
    validatorBuilder.with(new NoStaticExceptFinalRule());
    validatorBuilder.with(new GetterMustExistRule());
    validatorBuilder.with(new SetterMustExistRule());

    // Create Testers to validate behaviour for POJO_PACKAGE
    validatorBuilder.with(new DefaultValuesNullTester());
    validatorBuilder.with(new SetterTester());
    validatorBuilder.with(new GetterTester());

    pojoValidator = validatorBuilder.build();
  }

  @After
  public void restoreLogging() {
    LogHelper.resetLoggers();
  }

  @Test
  public void testPojoStructureAndBehavior() {
    pojoValidator.validate(pojoClasses);
  }
}
