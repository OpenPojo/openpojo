/*
 * Copyright (c) 2010-2015 Osman Shoukry
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License or any
 *    later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
