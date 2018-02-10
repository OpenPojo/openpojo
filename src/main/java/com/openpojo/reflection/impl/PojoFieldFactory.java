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

package com.openpojo.reflection.impl;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.openpojo.reflection.PojoField;

/**
 * This Field Factory returns a list of PojoFields for a given Class.
 *
 * @author oshoukry
 */
public final class PojoFieldFactory {
  /**
   * Get all PojoFields in a given Class.
   *
   * @param clazz
   *     The class to Introspect.
   * @return List of All Fields as PojoFields in that class.
   */
  public static List<PojoField> getPojoFields(final Class<?> clazz) {
    final List<PojoField> pojoFields = new LinkedList<PojoField>();
    for (final Field field : clazz.getDeclaredFields()) {
      pojoFields.add(new PojoFieldImpl(field));
    }
    return Collections.unmodifiableList(pojoFields);
  }

  private PojoFieldFactory() {
      throw new UnsupportedOperationException(PojoFieldFactory.class.getName() + " should not be constructed!");
    }
}
