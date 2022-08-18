/*
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

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.impl.ParameterizableFactory;

/**
 * This Class is responsible for creating on the fly random instances from Interfaces.
 * These random instances will return random data on all invocations for their methods.
 * equals, hashCode &amp; toString however will behave consistently with java's default behaviour.
 *
 * @author oshoukry
 */
public class RandomReturnInvocationHandler implements InvocationHandler, Serializable {

  private RandomReturnInvocationHandler() {
  }

  public Object invoke(final Object proxy, final Method method, final Object[] args) {
    if (method.getName().equals("toString")) {
      return objectToString(proxy);
    }

    if (method.getName().equals("equals")) {
      return objectEquals(proxy, args[0]);
    }

    if (method.getName().equals("hashCode")) {
      return objectHashCode(proxy);
    }

    return RandomFactory.getRandomValue(ParameterizableFactory.getInstance(method.getGenericReturnType()));
  }

  private String objectToString(final Object proxy) {
    return proxy.getClass().getName() + '@' + System.identityHashCode(proxy);
  }

  private int objectHashCode(final Object proxy) {
    return System.identityHashCode(proxy);
  }

  private boolean objectEquals(final Object proxy, final Object other) {
    return System.identityHashCode(proxy) == System.identityHashCode(other);
  }

  public static InvocationHandler getInstance() {
    return Instance.INSTANCE;
  }

  private static class Instance {
    private static final InvocationHandler INSTANCE = new RandomReturnInvocationHandler();
  }

}
