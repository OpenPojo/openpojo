/**
 * Copyright (C) 2010 Osman Shoukry
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.openpojo.validation;

import java.lang.annotation.Annotation;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.utils.validation.LoggingRule;
import com.openpojo.utils.validation.LoggingTester;
import com.openpojo.validation.rule.Rule;
import com.openpojo.validation.test.Tester;

/**
 * This is a logging tester used for testing.
 *
 * @author oshoukry
 */
public class PojoValidatorTest {

    @Test
    public void testRunValidation() {
        final PojoValidator pojoValidator = new PojoValidator();

        final LoggingRule loggingRule = new LoggingRule();
        final LoggingTester loggingTester = new LoggingTester();

        pojoValidator.addRule(loggingRule);
        pojoValidator.addTester(loggingTester);

        Assert.assertEquals(0, loggingRule.getLogs().size());
        Assert.assertEquals(0, loggingTester.getLogs().size());

        pojoValidator.runValidation(PojoClassFactory.getPojoClass(PojoValidatorTest.class));

        Assert.assertEquals(1, loggingRule.getLogs().size());
        Assert.assertEquals(1, loggingTester.getLogs().size());
    }

    @Test
    public void shouldEvaluateStructureAndNotRunValidationOnBehaviourSilentlyWithoutExceptions() {
        final PojoValidator pojoValidator = new PojoValidator();
        final RuleTesterMock ruleTesterMock = new RuleTesterMock();
        pojoValidator.addRule(ruleTesterMock);
        pojoValidator.addTester(new RuleTesterMock());
        pojoValidator.runValidation(new AbstractPojoClass());
        Assert.assertTrue("Evaluate not run on non-concrete class", ruleTesterMock.evaluateCalled);
        Assert.assertTrue("Run called on non-concrete class", ruleTesterMock.runCalled == false);
    }

    private static class RuleTesterMock implements Rule, Tester {
        private boolean evaluateCalled = false;
        private boolean runCalled = false;

        public void evaluate(final PojoClass pojoClass) {
            evaluateCalled = true;
        }

        public void run(final PojoClass pojoClass) {
            runCalled = true;
        }

    }

    private static class AbstractPojoClass implements PojoClass {

        public String getName() {
            throw new IllegalStateException("Unimplemented!!");
        }

        public List<? extends Annotation> getAnnotations() {
            throw new IllegalStateException("Unimplemented!!");
        }

        public <T extends Annotation> T getAnnotation(final Class<T> annotationClass) {
            throw new IllegalStateException("Unimplemented!!");
        }

        public boolean isInterface() {
            throw new IllegalStateException("Unimplemented!!");
        }

        public boolean isAbstract() {
            throw new IllegalStateException("Unimplemented!!");
        }

        public boolean isConcrete() {
            return false;
        }

        public boolean isEnum() {
            throw new IllegalStateException("Unimplemented!!");
        }

        public boolean isArray() {
            throw new IllegalStateException("Unimplemented!!");
        }

        public boolean isFinal() {
            throw new IllegalStateException("Unimplemented!!");
        }

        public List<PojoField> getPojoFields() {
            throw new IllegalStateException("Unimplemented!!");
        }

        public List<PojoMethod> getPojoMethods() {
            throw new IllegalStateException("Unimplemented!!");
        }

        public List<PojoMethod> getPojoConstructors() {
            throw new IllegalStateException("Unimplemented!!");
        }

        public boolean extendz(final Class<?> type) {
            throw new IllegalStateException("Unimplemented!!");
        }

        public PojoClass getSuperClass() {
            throw new IllegalStateException("Unimplemented!!");
        }

        public List<PojoClass> getInterfaces() {
            throw new IllegalStateException("Unimplemented!!");
        }

        public Class<?> getClazz() {
            throw new IllegalStateException("Unimplemented!!");
        }

        public boolean isNestedClass() {
            throw new IllegalStateException("Unimplemented!!");
        }

        public void copy(final Object from, final Object to) {
            throw new IllegalStateException("Unimplemented!!");
        }

        public String toString(final Object instance) {
            throw new IllegalStateException("Unimplemented!!");
        }

        public String getSourcePath() {
            throw new IllegalStateException("Unimplemented!!");
        }

    }
}
