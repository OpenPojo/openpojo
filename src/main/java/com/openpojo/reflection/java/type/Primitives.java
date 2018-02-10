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

package com.openpojo.reflection.java.type;

import java.util.HashMap;
import java.util.Map;

/**
 * @author oshoukry
 */
public class Primitives {
  private Map<Class<?>, Class<?>> primitivesToWrappers = new HashMap<Class<?>, Class<?>>(9);
  private static final Primitives INSTANCE = new Primitives();

  public static Primitives getInstance() {
    return INSTANCE;
  }

  private Primitives() {
    primitivesToWrappers.put(Boolean.TYPE, Boolean.class);
    primitivesToWrappers.put(Byte.TYPE, Byte.class);
    primitivesToWrappers.put(Character.TYPE, Character.class);
    primitivesToWrappers.put(Double.TYPE, Double.class);
    primitivesToWrappers.put(Float.TYPE, Float.class);
    primitivesToWrappers.put(Integer.TYPE, Integer.class);
    primitivesToWrappers.put(Long.TYPE, Long.class);
    primitivesToWrappers.put(Short.TYPE, Short.class);
    primitivesToWrappers.put(Void.TYPE, Void.class);
  }

  @SuppressWarnings("unchecked")
  public <T> Class<T> autoBox(Class<T> primitive) {
    if (primitive == null || !primitive.isPrimitive())
      return primitive;
    return (Class<T>) primitivesToWrappers.get(primitive);
  }
}
