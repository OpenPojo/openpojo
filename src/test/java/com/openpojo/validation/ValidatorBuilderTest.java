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

package com.openpojo.validation;

import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.exception.ValidationException;
import com.openpojo.validation.rule.Rule;
import com.openpojo.validation.test.Tester;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class ValidatorBuilderTest {

  @Test
  public void constructorMustBePrivate() {
    PojoClass validatorBuilderPojoClass = PojoClassFactory.getPojoClass(ValidatorBuilder.class);
    for (PojoMethod constructor : validatorBuilderPojoClass.getPojoConstructors())
      Assert.assertTrue(constructor.isPrivate());
  }

  @Test
  public void createReturnsValidatorBuilder() {
    Object validatorBuilder = ValidatorBuilder.create();
    Assert.assertNotNull(validatorBuilder);
    Assert.assertTrue(ValidatorBuilder.class.isAssignableFrom(validatorBuilder.getClass()));
  }

  @Test
  public void withRules_ignoresNullArray() {
    ValidatorBuilder validatorBuilder = ValidatorBuilder.create().with((Rule[]) null);
    Assert.assertEquals(0, validatorBuilder.getRules().size());
  }

  @Test
  public void withRules_ignoresNullArrayEntries() {
    ValidatorBuilder validatorBuilder = ValidatorBuilder.create().with(new Rule[] { null, null });
    Assert.assertEquals(0, validatorBuilder.getRules().size());
  }

  @Test
  public void withRules_persistRules() {
    Rule anyRule = RandomFactory.getRandomValue(Rule.class);
    ValidatorBuilder validatorBuilder = ValidatorBuilder.create().with(anyRule, null);
    Assert.assertEquals(1, validatorBuilder.getRules().size());
  }

  @Test
  public void withTesters_ignoresNullArray() {
    ValidatorBuilder validatorBuilder = ValidatorBuilder.create().with((Tester[]) null);
    Assert.assertEquals(0, validatorBuilder.getTesters().size());
  }

  @Test
  public void withTesters_ignoresNullArrayEntries() {
    ValidatorBuilder validatorBuilder = ValidatorBuilder.create().with(new Tester[] { null, null });
    Assert.assertEquals(0, validatorBuilder.getTesters().size());
  }

  @Test
  public void withTesters_persistRules() {
    Tester anyTester = RandomFactory.getRandomValue(Tester.class);
    ValidatorBuilder validatorBuilder = ValidatorBuilder.create().with(anyTester, null);
    Assert.assertEquals(1, validatorBuilder.getTesters().size());
  }

  @Test(expected = ValidationException.class)
  public void build_throwsExceptionIfNoRulesOrTestersAdded() {
    ValidatorBuilder validatorBuilder = ValidatorBuilder.create();
    validatorBuilder.build();
  }

  @Test
  public void build_returnsValidator() {
    ValidatorBuilder validatorBuilder = ValidatorBuilder.create();
    validatorBuilder.with(RandomFactory.getRandomValue(Rule.class));

    Object validator = validatorBuilder.build();

    Assert.assertNotNull(validator);
    Assert.assertTrue(Validator.class.isAssignableFrom(validator.getClass()));
  }

}
