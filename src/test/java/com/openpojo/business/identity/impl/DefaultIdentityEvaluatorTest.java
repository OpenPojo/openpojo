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

package com.openpojo.business.identity.impl;

import com.openpojo.business.identity.HashCodeGenerator;
import com.openpojo.business.identity.IdentityEvaluator;
import com.openpojo.business.identity.impl.sampleclasses.StringArrayCaseInsensitive;
import com.openpojo.business.identity.impl.sampleclasses.StringArrayCaseSensitive;
import com.openpojo.business.identity.impl.sampleclasses.StringArrayOfArrayCaseInsensitive;
import com.openpojo.business.identity.impl.sampleclasses.StringArrayOfArrayCaseSensitive;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.BusinessKeyMustExistRule;
import com.openpojo.validation.test.impl.BusinessIdentityTester;
import org.junit.Test;
import org.testng.Assert;

/**
 * @author oshoukry
 */
public class DefaultIdentityEvaluatorTest {
  private IdentityEvaluator defaultIdentityEvaluator = DefaultIdentityEvaluator.getInstance();
  private HashCodeGenerator defaultHashCodeGenerator = DefaultHashCodeGenerator.getInstance();

  @Test
  @SuppressWarnings("UnnecessaryLocalVariable")
  public void whenPointersToSameInstance_areEqualsTrue() {
    Object firstPointer = new Object();
    Object secondPointer = firstPointer;

    Assert.assertEquals(true, defaultIdentityEvaluator.areEqual(firstPointer, secondPointer));
  }

  @Test
  public void SampleClassMustUseBusinessIdentity() {
    Validator pojoValidator = ValidatorBuilder.create()
        .with(new BusinessKeyMustExistRule())
        .with(new BusinessIdentityTester())
        .build();
    pojoValidator.validate(PojoClassFactory.getPojoClass(StringArrayCaseSensitive.class));
    pojoValidator.validate(PojoClassFactory.getPojoClass(StringArrayCaseInsensitive.class));
  }

  @Test
  public void whenBusinessKeysAreOfEqualArrays_areEqualsIsTrue() {
    StringArrayCaseSensitive firstInstance = new StringArrayCaseSensitive(new String[] { "First", "Middle", "Last" });
    StringArrayCaseSensitive secondInstance = new StringArrayCaseSensitive(new String[] { "First", "Middle", "Last" });

    Assert.assertTrue(defaultIdentityEvaluator.areEqual(firstInstance, secondInstance));
    Assert.assertEquals(defaultHashCodeGenerator.doGenerate(firstInstance), defaultHashCodeGenerator.doGenerate(secondInstance));
  }

  @Test
  public void whenBusinessKeysAreOfCaseInsensitiveArrays_thenPojosAreEqual() {
    StringArrayCaseInsensitive firstInstance = new StringArrayCaseInsensitive(new String[] { "fIrSt", "MiDdLe", "LaSt" });
    StringArrayCaseInsensitive secondInstance = new StringArrayCaseInsensitive(new String[] { "first", "middle", "last" });
    Assert.assertTrue(defaultIdentityEvaluator.areEqual(firstInstance, secondInstance));
    Assert.assertEquals(defaultHashCodeGenerator.doGenerate(firstInstance), defaultHashCodeGenerator.doGenerate(secondInstance));
  }

  @Test
  public void whenBusinessKeysAreOfCaseInsensitiveArraysOfArrays_thenPojosAreEqual() {
    StringArrayOfArrayCaseInsensitive firstInstance = new StringArrayOfArrayCaseInsensitive(
        new String[][] { { "first" }, { "middle" }, { "last" } });
    StringArrayOfArrayCaseInsensitive secondInstance = new StringArrayOfArrayCaseInsensitive(
        new String[][] { { "first" }, { "middle" }, { "last" } });
    Assert.assertTrue(defaultIdentityEvaluator.areEqual(firstInstance, secondInstance));
    Assert.assertEquals(defaultHashCodeGenerator.doGenerate(firstInstance), defaultHashCodeGenerator.doGenerate(secondInstance));
  }

  @Test
  public void whenArraysOfNonEqualLength_thenPojosAreNotEqual() {
    StringArrayCaseSensitive firstInstance = new StringArrayCaseSensitive(new String[] { "First", "Second" });
    StringArrayCaseSensitive secondInstance = new StringArrayCaseSensitive(new String[] { "First" });
    Assert.assertFalse(defaultIdentityEvaluator.areEqual(firstInstance, secondInstance));
    Assert.assertFalse(defaultHashCodeGenerator.doGenerate(firstInstance) == defaultHashCodeGenerator.doGenerate(secondInstance));
  }

  @Test
  public void whenBusinessKeysAreOfEqualArraysOfArrays_areEqualsIsTrue() {
    StringArrayOfArrayCaseSensitive firstInstance = new StringArrayOfArrayCaseSensitive(
        new String[][] { { "First" }, { "Middle" }, { "Last" } });
    StringArrayOfArrayCaseSensitive secondInstance = new StringArrayOfArrayCaseSensitive(
        new String[][] { { "First" }, { "Middle" }, { "Last" } });

    Assert.assertTrue(defaultIdentityEvaluator.areEqual(firstInstance, secondInstance));
    Assert.assertEquals(defaultHashCodeGenerator.doGenerate(firstInstance), defaultHashCodeGenerator.doGenerate(secondInstance));
  }


  @Test
  public void whenEmptyArray_hashCodeIsOne() {
    StringArrayCaseSensitive instance = new StringArrayCaseSensitive(new String[] {});
    Assert.assertEquals(32, defaultHashCodeGenerator.doGenerate(instance));
  }

  @Test
  public void emptyArrayHashCode_isNotSameAsNullHashCode() {
    StringArrayCaseSensitive instance = new StringArrayCaseSensitive(new String[] { null });
    Assert.assertEquals(63, defaultHashCodeGenerator.doGenerate(instance));
  }

  @Test
  public void caseInsensitiveArraysHaveTheSameHashCode() {
    StringArrayCaseInsensitive firstInstance = new StringArrayCaseInsensitive(new String[] { "fIrSt", "MiDdLe", "LaSt" });
    StringArrayCaseInsensitive secondInstance = new StringArrayCaseInsensitive(new String[] { "first", "middle", "last" });

    Assert.assertEquals(defaultHashCodeGenerator.doGenerate(firstInstance), defaultHashCodeGenerator.doGenerate(secondInstance));

  }
}
