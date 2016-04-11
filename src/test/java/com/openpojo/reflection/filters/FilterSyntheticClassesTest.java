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
import org.junit.Assert;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class FilterSyntheticClassesTest {

  @Test
  public void shouldIncludeNonSyntheticPojos() {
    PojoClass notSynthetic = PojoStubFactory.getStubPojoClass(false);
    Assert.assertTrue(new FilterSyntheticClasses().include(notSynthetic));
  }

  @Test
  public void shouldExcludeSyntheticPojos() {
    PojoClass notSynthetic = PojoStubFactory.getStubPojoClass(true);
    Assert.assertFalse(new FilterSyntheticClasses().include(notSynthetic));
  }

  @Test
  public void twoInstancesShouldbeEqual() {
    Assert.assertEquals(new FilterSyntheticClasses(), new FilterSyntheticClasses());
  }

  @Test
  public void twoInstancesShouldHaveTheSameHashCode() {
    FilterSyntheticClasses instanceOne = new FilterSyntheticClasses();
    FilterSyntheticClasses instanceTwo = new FilterSyntheticClasses();

    Assert.assertEquals(instanceOne.hashCode(), instanceTwo.hashCode());
  }

  private static class PojoStubFactory {

    public static PojoClass getStubPojoClass(boolean isSynthetic) {
      return (PojoClass) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
          new Class<?>[] { PojoClass.class }, new StubInvocationHandler(isSynthetic));
    }
  }

  private static class StubInvocationHandler implements InvocationHandler {

    private boolean isSynthetic;

    public StubInvocationHandler(boolean isSynthetic) {
      this.isSynthetic = isSynthetic;
    }

    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {

      if (method.getName().equals("isSynthetic"))
        return isSynthetic;

      throw new RuntimeException("UnImplemented!!");
    }
  }
}
