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

package com.openpojo.validation.rule.impl;

import java.util.UUID;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.reflection.java.bytecode.asm.ASMService;
import com.openpojo.reflection.java.bytecode.asm.DefaultSubClassDefinition;
import com.openpojo.validation.rule.impl.sampleclasses.AClassThatIsNotATestButEndsWithTest;
import com.openpojo.validation.rule.impl.sampleclasses.ATestNGClassEndsWithTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class TestClassMustEndWithRuleTest {

  private TestClassMustBeProperlyNamedRule testClassMustEndWithRule;

  @Before
  public void setUp() throws Exception {
    testClassMustEndWithRule = new TestClassMustBeProperlyNamedRule();
  }

  @Test
  public void shouldPassValidation() {
    PojoClass aGoodTestClassPojo = PojoClassFactory.getPojoClass(this.getClass());
    testClassMustEndWithRule.evaluate(aGoodTestClassPojo);
  }

  @Test(expected = AssertionError.class)
  public void shouldFailValidation() {
    Class<?> aBadTestClass = ASMService.getInstance().createSubclassFor(this.getClass(),
        new DefaultSubClassDefinition(this.getClass(), "ABadClassName"));
    PojoClass aBadTestClassPojo = PojoClassFactory.getPojoClass(aBadTestClass);
    testClassMustEndWithRule.evaluate(aBadTestClassPojo);
  }

  @Test(expected = AssertionError.class)
  public void aClassThatHasTestNotAsStartOrEndShouldFailValidation() {
    Class<?> aBadTestClass = ASMService.getInstance().createSubclassFor(this.getClass(),
        new DefaultSubClassDefinition(this.getClass(), getUniqueClassName("ABadTestClassName")));
    PojoClass aBadTestClassPojo = PojoClassFactory.getPojoClass(aBadTestClass);
    testClassMustEndWithRule.evaluate(aBadTestClassPojo);
  }

  @Test
  public void givenAClassThatEndsWithTestCaseShouldPass() {
    Class<?> testClass = ASMService.getInstance().createSubclassFor(this.getClass(),
        new DefaultSubClassDefinition(this.getClass(), "AClassTestCase"));
    PojoClass aBadTestClassPojo = PojoClassFactory.getPojoClass(testClass);
    testClassMustEndWithRule.evaluate(aBadTestClassPojo);
  }

  @Test(expected = AssertionError.class)
  public void aClassThatHasTestSuiteButDoesntEndWithTestCaseShouldFailValidation() {
    Class<?> testClass = ASMService.getInstance().createSubclassFor(this.getClass(),
        new DefaultSubClassDefinition(this.getClass(), "AClassTestCaseAndSomethingElse"));
    PojoClass aBadTestClassPojo = PojoClassFactory.getPojoClass(testClass);
    testClassMustEndWithRule.evaluate(aBadTestClassPojo);
  }

  @Test
  public void aClassThatEndWithTestMayNotBeATest() {
    Class<?> aTestClassThatEndsWithTest = AClassThatIsNotATestButEndsWithTest.class;
    Assert.assertTrue("Should end with Test (was the class refactored?)", aTestClassThatEndsWithTest.getName().endsWith("Test"));
    testClassMustEndWithRule.evaluate(PojoClassFactory.getPojoClass(aTestClassThatEndsWithTest));
  }

  @Test
  public void aClassThatDoesNotEndWithTestAndIsNotATestShouldPass() {
    Class<?> aClassThatDoesNotEndWithTest = String.class;
    testClassMustEndWithRule.evaluate(PojoClassFactory.getPojoClass(aClassThatDoesNotEndWithTest));
  }

  @Test
  public void aTestNGClassThatEndsWithTestShouldPass() {
    Class<?> aTestClassThatEndsWithTest = ATestNGClassEndsWithTest.class;
    Assert.assertTrue("Should end with Test (was the class refactored?)", aTestClassThatEndsWithTest.getName().endsWith("Test"));
    testClassMustEndWithRule.evaluate(PojoClassFactory.getPojoClass(aTestClassThatEndsWithTest));
  }

  @Test(expected = AssertionError.class)
  public void aTestNGClassThatDoesntEndWithTestShouldFail() {
    final Class<ATestNGClassEndsWithTest> parentClass = ATestNGClassEndsWithTest.class;
    Class<?> aBadTestClass = ASMService.getInstance().createSubclassFor(parentClass,
        new DefaultSubClassDefinition(parentClass, getUniqueClassName("ABadTestClassName")));
    PojoClass aBadTestClassPojo = PojoClassFactory.getPojoClass(aBadTestClass);
    testClassMustEndWithRule.evaluate(aBadTestClassPojo);
  }

  private String getUniqueClassName(String prefix) {
    return prefix + "_" + UUID.randomUUID().toString().replace("-", "_");
  }

}