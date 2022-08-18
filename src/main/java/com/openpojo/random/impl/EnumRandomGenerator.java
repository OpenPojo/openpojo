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

package com.openpojo.random.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import com.openpojo.random.RandomGenerator;
import com.openpojo.random.util.SomeEnum;

/**
 * This random generator generates for Enum type.
 *
 * @author oshoukry
 */
public final class EnumRandomGenerator implements RandomGenerator {
  private static final Class<?>[] TYPES = new Class<?>[] { Enum.class };

  private static final Random RANDOM = new Random(new Date().getTime());

  private EnumRandomGenerator() {
  }

  public static EnumRandomGenerator getInstance() {
    return Instance.INSTANCE;
  }

  public Object doGenerate(final Class<?> type) {
    return SomeEnum.values()[RANDOM.nextInt(SomeEnum.values().length)];
  }

  public Collection<Class<?>> getTypes() {
    return Arrays.asList(TYPES);
  }

  private static class Instance {
    private static final EnumRandomGenerator INSTANCE = new EnumRandomGenerator();
  }

}
