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

package com.openpojo.validation.impl;

import java.util.ArrayList;
import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;
import com.openpojo.validation.impl.sample.DummyClass;
import com.openpojo.validation.impl.sample.subpackage.AnotherDummyClass;
import com.openpojo.validation.rule.Rule;
import com.openpojo.validation.test.Tester;
import org.junit.Assert;
import org.junit.Test;

public class DefaultValidatorTest {

    @Test
    public void rulesAndTestersAreTriggeredWhenValidation() {
        TesterSpy testerSpy = new TesterSpy();
        List<Tester> testers = new ArrayList<Tester>();
        testers.add(testerSpy);

        RuleSpy ruleSpy = new RuleSpy();
        List<Rule> rules = new ArrayList<Rule>();
        rules.add(ruleSpy);

        FilterSpy filterSpy = new FilterSpy();

        DefaultValidator defaultValidator = new DefaultValidator(rules, testers);
        defaultValidator.validate(this.getClass().getPackage().getName() + ".sample", filterSpy);

        assertInvokedClasses(testerSpy.getInvocations(), DummyClass.class.getName());
        assertInvokedClasses(ruleSpy.getInvocations(), DummyClass.class.getName());
        assertInvokedClasses(filterSpy.getInvocations(), DummyClass.class.getName());
    }

    @Test
    public void rulesAndTestersAreTriggeredWhenValidationIsRunRecursively() {
        TesterSpy testerSpy = new TesterSpy();
        List<Tester> testers = new ArrayList<Tester>();
        testers.add(testerSpy);

        RuleSpy ruleSpy = new RuleSpy();
        List<Rule> rules = new ArrayList<Rule>();
        rules.add(ruleSpy);

        FilterSpy filterSpy = new FilterSpy();

        DefaultValidator defaultValidator = new DefaultValidator(rules, testers);
        defaultValidator.validateRecursively(this.getClass().getPackage().getName() + ".sample", filterSpy);

        assertInvokedClasses(testerSpy.getInvocations(), DummyClass.class.getName(), AnotherDummyClass.class.getName());
        assertInvokedClasses(ruleSpy.getInvocations(), DummyClass.class.getName(), AnotherDummyClass.class.getName());
        assertInvokedClasses(filterSpy.getInvocations(), DummyClass.class.getName(), AnotherDummyClass.class.getName());
    }

    private void assertInvokedClasses(List<String> invocations, String... classNames) {
        Assert.assertEquals(classNames.length, invocations.size());

        for (String className : classNames)
            Assert.assertTrue("Could not find call for class [" + className + "]", invocations.contains(className));
    }

    private static class TesterSpy implements Tester {
        private List<String> invocations = new ArrayList<String>();

        public void run(PojoClass pojoClass) {
            invocations.add(pojoClass.getName());
        }

        public List<String> getInvocations() {
            return invocations;
        }
    }

    private static class RuleSpy implements Rule {
        private List<String> invocations = new ArrayList<String>();

        public void evaluate(PojoClass pojoClass) {
            invocations.add(pojoClass.getName());
        }

        public List<String> getInvocations() {
            return invocations;
        }
    }

    private static class FilterSpy implements PojoClassFilter {
        private List<String> invocations = new ArrayList<String>();

        public boolean include(PojoClass pojoClass) {
            invocations.add(pojoClass.getName());
            return true;
        }

        public List<String> getInvocations() {
            return invocations;
        }
    }
}
