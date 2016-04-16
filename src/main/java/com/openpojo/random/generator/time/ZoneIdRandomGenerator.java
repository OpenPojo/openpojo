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

package com.openpojo.random.generator.time;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import com.openpojo.random.RandomGenerator;
import com.openpojo.reflection.java.load.ClassUtil;

/**
 * @author oshoukry
 */
public class ZoneIdRandomGenerator implements RandomGenerator {
  private static final String TYPE = "java.time.ZoneId";
  private static final ZoneIdRandomGenerator INSTANCE = new ZoneIdRandomGenerator();
  private static final Random RANDOM = new Random(System.currentTimeMillis());

  public static ZoneIdRandomGenerator getInstance() {
    return INSTANCE;
  }

  public Collection<Class<?>> getTypes() {
    List<Class<?>> types = new ArrayList<Class<?>>();
    if (ClassUtil.isClassLoaded(TYPE))
      types.add(ClassUtil.loadClass(TYPE));
    return types;
  }

  @SuppressWarnings("Since15")
  public Object doGenerate(Class<?> type) {
    Object[] availableZoneIds = java.time.ZoneId.getAvailableZoneIds().toArray();

    return java.time.ZoneId.of(availableZoneIds[RANDOM.nextInt(availableZoneIds.length)].toString());
  }

  private ZoneIdRandomGenerator() {
  }

}