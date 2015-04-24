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