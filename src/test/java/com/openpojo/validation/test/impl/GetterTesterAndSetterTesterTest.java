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

package com.openpojo.validation.test.impl;

import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.test.Tester;
import com.openpojo.validation.test.impl.sampleclasses.Good_AnAbstractClassWithAbstractSetterGetter;
import org.junit.Assert;
import org.junit.Test;

public class GetterTesterAndSetterTesterTest {
  private static final String TESTPACKAGE = GetterTesterAndSetterTesterTest.class.getPackage().getName() + ".sampleclasses";

  public List<PojoClass> getGoodPojoClasses() {
    return PojoClassFactory.getPojoClasses(TESTPACKAGE, new PojoClassFilter() {
      public boolean include(PojoClass pojoClass) {
        return pojoClass.getClazz().getSimpleName().startsWith("Good_");
      }
    });
  }

  public List<PojoClass> getBadPojoClasses() {
    return PojoClassFactory.getPojoClassesRecursively(TESTPACKAGE, new PojoClassFilter() {

      public boolean include(final PojoClass pojoClass) {
        return pojoClass.getClazz().getSimpleName().startsWith("Bad_");
      }
    });
  }

  @Test
  public void shouldPassSetterTest() {
    List<PojoClass> goodPojoClasses = getGoodPojoClasses();

    Assert.assertEquals("Classes added/removed?", 3, goodPojoClasses.size());
    for (final PojoClass pojoClass : goodPojoClasses) {
      invokeRun(pojoClass, new SetterTester());
      invokeRun(pojoClass, new GetterTester());
    }
  }

  @Test
  public void setterShouldSkipTestingAbstractMethods() {
    PojoClass pojoClass = PojoClassFactory.getPojoClass(Good_AnAbstractClassWithAbstractSetterGetter.class);
    invokeRun(pojoClass, new SetterTester());
  }

  @Test
  public void shouldFailSetterTest() {
    List<PojoClass> badPojoClasses = getBadPojoClasses();
    Assert.assertEquals("Classes added/removed?", 1, badPojoClasses.size());
    for (final PojoClass pojoClass : badPojoClasses) {
      try {
        invokeRun(pojoClass, new SetterTester());
        Assert.fail("Should not have passed");
      } catch (AssertionError ignored) {

      }
    }
  }

  @Test(expected = AssertionError.class)
  public void shouldFailGetterTest() {
    for (final PojoClass pojoClass : getBadPojoClasses()) {
      invokeRun(pojoClass, new GetterTester());
    }
  }

  private void invokeRun(final PojoClass classToTest, final Tester tester) {
    tester.run(classToTest);
  }

}
