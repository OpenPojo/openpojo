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

package com.openpojo.random.generator.time;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.openpojo.random.RandomFactory;
import com.openpojo.random.RandomGenerator;
import com.openpojo.reflection.java.load.ClassUtil;

import static com.openpojo.random.generator.time.util.ReflectionHelper.getMethod;
import static com.openpojo.random.generator.time.util.ReflectionHelper.invokeMethod;

/**
 * @author oshoukry
 */
public class InstantRandomGenerator implements RandomGenerator {
  private static final String TYPE = "java.time.Instant";
  private static final InstantRandomGenerator INSTANCE = new InstantRandomGenerator();
  private Class<?> instantClass;

  public static InstantRandomGenerator getInstance() {
    return INSTANCE;
  }

  public Collection<Class<?>> getTypes() {
    List<Class<?>> types = new ArrayList<Class<?>>();
    if (instantClass != null)
      types.add(instantClass);
    return types;
  }

  public Object doGenerate(Class<?> type) {
    Object zonedDateTime = RandomFactory.getRandomValue(ClassUtil.loadClass("java.time.ZonedDateTime"));

    Method toInstant = getMethod(zonedDateTime.getClass(), "toInstant");
    return invokeMethod(toInstant, zonedDateTime);
  }

  private InstantRandomGenerator() {
    instantClass = ClassUtil.loadClass(TYPE);
  }
}
