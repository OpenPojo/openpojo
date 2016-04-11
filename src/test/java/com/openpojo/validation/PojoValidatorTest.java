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

package com.openpojo.validation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.utils.validation.LoggingRule;
import com.openpojo.utils.validation.LoggingTester;
import com.openpojo.validation.rule.Rule;
import com.openpojo.validation.sample.AnAbstractClassWithGetterSetter;
import com.openpojo.validation.test.Tester;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.Assert;
import org.junit.Test;

/**
 * This is a logging tester used for testing.
 *
 * @author oshoukry
 */
public class PojoValidatorTest {

  private Validator pojoValidator;

  @Test
  public void testRunValidation() {
    final LoggingRule loggingRule = new LoggingRule();
    final LoggingTester loggingTester = new LoggingTester();

    pojoValidator = ValidatorBuilder.create()
        .with(loggingRule)
        .with(loggingTester)
        .build();

    Assert.assertEquals(0, loggingRule.getLogs().size());
    Assert.assertEquals(0, loggingTester.getLogs().size());

    pojoValidator.validate(PojoClassFactory.getPojoClass(PojoValidatorTest.class));

    Assert.assertEquals(1, loggingRule.getLogs().size());
    Assert.assertEquals(1, loggingTester.getLogs().size());
  }

  @Test
  public void shouldNotInvokeRulesOnInterfaces() {
    MethodValueReturn methodValueReturn = new MethodValueReturn();
    methodValueReturn.isInterface = true;
    String pojoType = "Interface";

    ensureRuleInvokedTesterNotInvoked(methodValueReturn, pojoType);
  }

  @Test
  public void shouldNotInvokeRulesOnEnums() {
    MethodValueReturn methodValueReturn = new MethodValueReturn();
    methodValueReturn.isEnum = true;
    String pojoType = "Enum";

    ensureRuleInvokedTesterNotInvoked(methodValueReturn, pojoType);
  }

  private void ensureRuleInvokedTesterNotInvoked(MethodValueReturn methodValueReturn, String pojoType) {
    RuleTesterMock ruleTesterMock = new RuleTesterMock();
    pojoValidator = ValidatorBuilder.create()
        .with((Rule) ruleTesterMock)
        .with((Tester) ruleTesterMock)
        .build();
    pojoValidator.validate(PojoStubFactory.getStubPojoClass(methodValueReturn));
    Assert.assertTrue("Evaluate not run on " + pojoType + " class", ruleTesterMock.evaluateCalled);
    Assert.assertTrue("Rule called on " + pojoType + " class", !ruleTesterMock.runCalled);
  }

  @Test
  public void shouldInvokeRuleOnAbstract() {
    MethodValueReturn methodValueReturn = new MethodValueReturn();
    methodValueReturn.isAbstract = true;
    RuleTesterMock ruleTesterMock = new RuleTesterMock();

    pojoValidator = ValidatorBuilder.create()
        .with((Tester) ruleTesterMock)
        .build();

    pojoValidator.validate(PojoStubFactory.getStubPojoClass(methodValueReturn));
    Assert.assertTrue("Tester not called", ruleTesterMock.runCalled);
  }

  @Test
  public void abstractClassTestingEndToEnd() {
    pojoValidator = ValidatorBuilder.create()
        .with(new GetterTester())
        .with(new SetterTester())
        .build();

    pojoValidator.validate(PojoClassFactory.getPojoClass(AnAbstractClassWithGetterSetter.class));
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

    public static PojoClass getStubPojoClass(MethodValueReturn methodValueReturn) {
      return (PojoClass) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
          new Class<?>[] { PojoClass.class }, new StubInvocationHandler(methodValueReturn));
    }
  }

  private static class MethodValueReturn {
    public boolean isConcrete = false;
    public boolean isAbstract = false;
    public boolean isInterface = false;
    public boolean isEnum = false;
    public boolean isSynthetic = false;
  }

  private static class StubInvocationHandler implements InvocationHandler {

    private final MethodValueReturn methodValueReturn;

    public StubInvocationHandler(MethodValueReturn methodValueReturn) {
      this.methodValueReturn = methodValueReturn;
    }

    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {

      if (method.getName().equals("isConcrete")) return methodValueReturn.isConcrete;
      if (method.getName().equals("isAbstract")) return methodValueReturn.isAbstract;
      if (method.getName().equals("isInterface")) return methodValueReturn.isInterface;
      if (method.getName().equals("isEnum")) return methodValueReturn.isEnum;
      if (method.getName().equals("isSynthetic")) return methodValueReturn.isSynthetic;

      if (method.getName().equals("getClazz")) return this.getClass();

      throw new RuntimeException("UnImplemented!!");
    }
  }
}