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

package com.openpojo.random.impl;

import java.util.Collection;

import com.openpojo.random.RandomGenerator;

/**
 * This random generator generates for EnumSet types.
 *
 * @author oshoukry
 */
@Deprecated
public final class EnumSetRandomGenerator implements RandomGenerator {
  private static final com.openpojo.random.collection.set.EnumSetRandomGenerator delegate = com.openpojo.random.collection.set
      .EnumSetRandomGenerator.getInstance();

  public static EnumSetRandomGenerator getInstance() {
    return Instance.INSTANCE;
  }

  public Object doGenerate(final Class<?> type) {
    return delegate.doGenerate(type);
  }

  public Collection<Class<?>> getTypes() {
    return delegate.getTypes();
  }

  private static class Instance {
    private static final EnumSetRandomGenerator INSTANCE = new EnumSetRandomGenerator();
  }

}
