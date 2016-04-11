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

package com.openpojo.reflection.filters;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;
import com.openpojo.validation.affirm.Affirm;
import org.junit.Test;

public class FilterClassNameTest extends IdentitiesAreEqual {

  @Test
  public void testRegExToIncludeClassesNamedTest() {

    // Include classNames that have the word Test in the name.
    final PojoClassFilter filter = new FilterClassName("\\w*Test\\w*$");

    String[] classNames = new String[] { "EndingWithTest", "TestStartingWith", "WithTestInTheMiddleClass",
        "package.path.TestClass", "package.path.ClassTest", "package.path.ClassTestClass" };

    for (final String className : classNames) {
      final PojoClass pojoClassStub = PojoStubFactory.getStubPojoClass(className);
      Affirm.affirmTrue(String.format("[%s] didn't include class [%s]!!", filter, className), filter.include(pojoClassStub));
    }

    classNames = new String[] { "package.Test.Class", "TestClass.package" };
    for (final String className : classNames) {
      final PojoClass pojoClassStub = PojoStubFactory.getStubPojoClass(className);
      Affirm.affirmFalse(String.format("[%s] didn't exclude class [%s]!!", filter, className), filter.include(pojoClassStub));
    }
  }

  @Test
  public void testShouldExcludeFileName() {

    // Include only class names that are in the format of xSample
    final PojoClassFilter filter = new FilterClassName(".+Sample$");

    final String[] classNames = new String[] { "packagepath.packageSample.MyClass", "SampleClass", "Sample", "sample",
        "Somesample", "someSampleClass" };

    for (final String className : classNames) {
      PojoClass pojoClassStub = PojoStubFactory.getStubPojoClass(className);
      Affirm.affirmFalse(String.format("[%s] didn't exclude class [%s]!!", filter, className), filter.include(pojoClassStub));
    }

  }

  @Test
  public void shouldBeIdentityEqual() {
    FilterClassName instanceOne = new FilterClassName("\\*Test");
    FilterClassName instanceTwo = new FilterClassName("\\*Test");

    checkEqualityAndHashCode(instanceOne, instanceTwo);
  }

  @Test
  public void shouldFilterBasedOnPackageName() {

    // Include classNames that have the word Test in the name.
    final PojoClassFilter filter = new FilterClassName("^(?:.*\\.)?model(\\..*)+");

    String[] classNames = new String[] { "com.model.MyClass", "model.pojos.MyClass", "com.model.pojos.MyClass" };

    for (final String className : classNames) {
      final PojoClass pojoClassStub = PojoStubFactory.getStubPojoClass(className);
      Affirm.affirmTrue(String.format("[%s] didn't include class [%s]!!", filter, className), filter.include(pojoClassStub));
    }

    classNames = new String[] { "Testmodel", "com.mypackage.modelClass", "model", "pojomodel.model" };
    for (final String className : classNames) {
      final PojoClass pojoClassStub = PojoStubFactory.getStubPojoClass(className);
      Affirm.affirmFalse(String.format("[%s] didn't exclude class [%s]!!", filter, className), filter.include(pojoClassStub));
    }
  }

  private static class PojoStubFactory {

    public static PojoClass getStubPojoClass(String className) {
      return (PojoClass) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
          new Class<?>[] { PojoClass.class }, new StubInvocationHandler(className));
    }
  }

  private static class StubInvocationHandler implements InvocationHandler {
    private String className;

    public StubInvocationHandler(String className) {
      this.className = className;
    }

    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {

      if (method.getName().equals("getName"))
        return className;

      throw new RuntimeException("UnImplemented!!");
    }
  }
}
