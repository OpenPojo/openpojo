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

package com.openpojo.random.collection.list;

import java.util.AbstractSequentialList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import com.openpojo.random.collection.util.BaseCollectionRandomGenerator;
import com.openpojo.random.util.Helper;

/**
 * @author oshoukry
 */
public class AbstractSequentialListRandomGenerator extends BaseCollectionRandomGenerator {
  private static final Class<?>[] TYPES = new Class<?>[] { AbstractSequentialList.class };
  private static final AbstractSequentialListRandomGenerator INSTANCE = new AbstractSequentialListRandomGenerator();

  public static AbstractSequentialListRandomGenerator getInstance() {
    return INSTANCE;
  }

  public Collection<Class<?>> getTypes() {
    return Arrays.asList(TYPES);
  }

  @Override
  protected Collection getBasicInstance(Class<?> type) {
    Helper.assertIsAssignableTo(type, getTypes());
    return new LinkedList();
  }

  private AbstractSequentialListRandomGenerator() {
  }
}
