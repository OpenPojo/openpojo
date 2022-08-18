/*
 * Copyright (c) 2010-2018 Osman Shoukry
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

package com.openpojo.random.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.impl.PojoClassFactory;

/**
 * This Factory returns a proxy pojo for any provided interface.
 * The Proxy pojo will then trap all method calls to the interface and return random generated values for the calls.
 *
 * @author oshoukry
 */
public class RandomInstanceFromInterfaceRandomGenerator {

  public static RandomInstanceFromInterfaceRandomGenerator getInstance() {
    return Instance.INSTANCE;
  }

  /**
   * This method returns a random instance for a given interface.
   * The instance will return random values upon method invocations.
   *
   * @param <T>
   *     The type to generate an instance of.
   * @param clazz
   *     The interface to generate the implementations on.
   * @return An instance of the interface.
   */
  @SuppressWarnings("unchecked")
  public <T> T doGenerate(final Class<T> clazz) {
    PojoClass pojoClass = PojoClassFactory.getPojoClass(clazz);
    if (!pojoClass.isInterface()) {
      throw ReflectionException.getInstance(
          String.format("[%s] is not an interface, can't create a proxy for concrete or abstract types.", pojoClass.getName()));
    }

    InvocationHandler handler = RandomReturnInvocationHandler.getInstance();

    return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
        new Class<?>[] { pojoClass.getClazz() }, handler);
  }

  private static class Instance {
    private static final RandomInstanceFromInterfaceRandomGenerator INSTANCE = new RandomInstanceFromInterfaceRandomGenerator();
  }

}
