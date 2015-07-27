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

import java.util.ArrayList;
import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.rule.Rule;
import com.openpojo.validation.test.Tester;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SimpleValidatorTest {

    private SimpleValidator simpleValidator;
    PojoClass pojoClass = PojoClassFactory.getPojoClass(this.getClass());
    List<PojoClass> pojoClasses;

    @Before
    public void setUp() {
        simpleValidator = new SimpleValidator();
        pojoClasses = new ArrayList<PojoClass>();
        pojoClasses.add(pojoClass);
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void withNullRuleShouldThrowIllegalArgumentException() {
        Rule[] rules = new Rule[] { null };
        simpleValidator.with(rules);
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void withNullRulesArrayShouldThrowIllegalArgumentException() {
        simpleValidator.with((Rule[]) null);
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void withNullTesterShouldThrowIllegalArgumentException() {
        Tester[] testers = new Tester[] { null };
        simpleValidator.with(testers);
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void withNullTestsArrayShouldThrowIllegalArgumentException() {
        simpleValidator.with((Tester[]) null);
    }

    @Test(expected = com.openpojo.validation.exception.ValidationException.class)
    public void withOutTesterOrRuleShouldFailValidation() {
        simpleValidator.validate(pojoClasses);
    }

    @Test(expected = com.openpojo.validation.exception.ValidationException.class)
    public void shouldFailWhenPojoClassesIsNull() {
        simpleValidator.validate(null);
    }

    @Test(expected = com.openpojo.validation.exception.ValidationException.class)
    public void shouldFailWhenPojoClassesIsEmpty() {
        simpleValidator.validate(new ArrayList<PojoClass>());
    }

    @Test
    public void whenValidateRulesAndTestsAreInvoked() {

        RuleSpy ruleSpy = new RuleSpy();
        TesterSpy testerSpy = new TesterSpy();

        simpleValidator.with(ruleSpy).with(testerSpy).validate(pojoClasses);

        Assert.assertEquals(1, ruleSpy.invocations.size());
        Assert.assertEquals(pojoClass.toString(), ruleSpy.invocations.get(0));

        Assert.assertEquals(1, testerSpy.invocations.size());
        Assert.assertEquals(pojoClass.toString(), testerSpy.invocations.get(0));
    }

    private class RuleSpy implements Rule {
        public List<String> invocations = new ArrayList<String>();

        public void evaluate(PojoClass pojoClass) {
            invocations.add(pojoClass.toString());
        }
    }

    private class TesterSpy implements Tester {
        public List<String> invocations = new ArrayList<String>();

        public void run(PojoClass pojoClass) {
            invocations.add(pojoClass.toString());
        }
    }

}
