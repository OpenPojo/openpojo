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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.utils.validation.LoggingRule;
import com.openpojo.utils.validation.LoggingTester;
import com.openpojo.validation.rule.Rule;
import com.openpojo.validation.test.Tester;
import org.junit.Assert;
import org.junit.Test;

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
        pojoValidator.addTester(ruleTesterMock);
        pojoValidator.runValidation(PojoStubFactory.getStubPojoClass());
        Assert.assertTrue("Evaluate not run on non-concrete class", ruleTesterMock.evaluateCalled);
        Assert.assertTrue("Run called on non-concrete class", !ruleTesterMock.runCalled);
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

    private static class PojoStubFactory {

        public static PojoClass getStubPojoClass() {
            return (PojoClass) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[] { PojoClass.class },
                    new StubInvocationHandler());
        }
    }

    private static class StubInvocationHandler implements InvocationHandler {

        public Object invoke(Object o, Method method, Object[] objects) throws Throwable {

            if (method.getName().equals("isConcrete"))
                return false;

            if (method.getName().equals("isSynthetic"))
                return false;

            if (method.getName().equals("getClazz"))
                return this.getClass();

            throw new RuntimeException("UnImplemented!!");
        }
    }
}
