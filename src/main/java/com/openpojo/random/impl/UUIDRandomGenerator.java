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
import java.util.UUID;

import com.openpojo.random.RandomGenerator;

/**
 * @author oshoukry
 */
public class UUIDRandomGenerator implements RandomGenerator {
  private static final Class<?>[] TYPES = new Class<?>[] { UUID.class };

  private UUIDRandomGenerator() {

  }

  public static UUIDRandomGenerator getInstance() {
    return Instance.INSTANCE;
  }

  public Collection<Class<?>> getTypes() {
    return Arrays.asList(TYPES);
  }

  public Object doGenerate(Class<?> type) {
    return UUID.randomUUID();
  }

  private static class Instance {
    private static final UUIDRandomGenerator INSTANCE = new UUIDRandomGenerator();
  }
}
