/*
 * Copyright (c) 2010-2017 Osman Shoukry
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

package com.openpojo.validation.impl;

import java.util.ArrayList;
import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;
import com.openpojo.reflection.impl.PojoClassFactory;
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
    String packageName = this.getClass().getPackage().getName() + ".sample";
    List<PojoClass> validatedPojoClasses = defaultValidator.validate(packageName, filterSpy);

    Assert.assertEquals(1, validatedPojoClasses.size());
    Assert.assertEquals(validatedPojoClasses.get(0), PojoClassFactory.getPojoClass(DummyClass.class));
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
    String packageName = this.getClass().getPackage().getName() + ".sample";
    List<PojoClass> validatedPojoClasses = defaultValidator.validateRecursively(packageName, filterSpy);

    Assert.assertEquals(2, validatedPojoClasses.size());
    Assert.assertTrue(validatedPojoClasses.contains(PojoClassFactory.getPojoClass(DummyClass.class)));
    Assert.assertTrue(validatedPojoClasses.contains(PojoClassFactory.getPojoClass(AnotherDummyClass.class)));

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

    List<String> getInvocations() {
      return invocations;
    }
  }

  private static class RuleSpy implements Rule {
    private List<String> invocations = new ArrayList<String>();

    public void evaluate(PojoClass pojoClass) {
      invocations.add(pojoClass.getName());
    }

    List<String> getInvocations() {
      return invocations;
    }
  }

  private static class FilterSpy implements PojoClassFilter {
    private List<String> invocations = new ArrayList<String>();

    public boolean include(PojoClass pojoClass) {
      invocations.add(pojoClass.getName());
      return true;
    }

    List<String> getInvocations() {
      return invocations;
    }
  }
}
