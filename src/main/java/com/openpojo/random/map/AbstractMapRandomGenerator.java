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

package com.openpojo.random.map;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.openpojo.random.map.util.BaseMapRandomGenerator;
import com.openpojo.random.map.util.MapHelper;
import com.openpojo.random.util.Helper;
import com.openpojo.random.util.SerializableComparableObject;

/**
 * @author oshoukry
 */
public class AbstractMapRandomGenerator extends BaseMapRandomGenerator {
  private static final Class<?>[] TYPES = new Class<?>[] { AbstractMap.class };
  private static final AbstractMapRandomGenerator INSTANCE = new AbstractMapRandomGenerator();

  public static AbstractMapRandomGenerator getInstance() {
    return INSTANCE;
  }

  public Collection<Class<?>> getTypes() {
    return Arrays.asList(TYPES);
  }

  @Override
  protected Map getBasicInstance(Class<?> type) {
    Helper.assertIsAssignableTo(type, getTypes());
    return MapHelper.buildMap(new HashMap(), SerializableComparableObject.class, SerializableComparableObject.class);
  }

  private AbstractMapRandomGenerator() {
  }

}
