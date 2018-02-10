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

import java.util.Collection;

import com.openpojo.log.LoggerFactory;
import com.openpojo.random.RandomGenerator;
import com.openpojo.random.dynamic.ArrayRandomGenerator;
import com.openpojo.random.dynamic.EnumRandomGenerator;
import com.openpojo.random.dynamic.RandomInstanceFromInterfaceRandomGenerator;
import com.openpojo.random.exception.RandomGeneratorException;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.construct.InstanceFactory;
import com.openpojo.reflection.impl.PojoClassFactory;

/**
 * @author oshoukry
 */
public class DefaultRandomGenerator implements RandomGenerator {
  private final RandomInstanceFromInterfaceRandomGenerator interfaceRandomGenerator =
      RandomInstanceFromInterfaceRandomGenerator.getInstance();
  private final EnumRandomGenerator enumRandomGenerator = EnumRandomGenerator.getInstance();
  private final ArrayRandomGenerator arrayRandomGenerator = ArrayRandomGenerator.getInstance();

  public Collection<Class<?>> getTypes() {
    throw RandomGeneratorException.getInstance("UnImplemented, this default RandomGenerator should be registered as " +
        "Default, and has " + "no explicit Types declared");
  }

  public Object doGenerate(final Class<?> type) {
    final PojoClass typePojoClass = PojoClassFactory.getPojoClass(type);
    if (typePojoClass.isInterface()) {
      return interfaceRandomGenerator.doGenerate(type);
    }

    if (typePojoClass.isEnum()) {
      return enumRandomGenerator.doGenerate(type);
    }

    if (typePojoClass.isArray()) {
      return arrayRandomGenerator.doGenerate(type);
    }

    LoggerFactory.getLogger(DefaultRandomGenerator.class).debug("Creating basic instance for type=[{0}] using " +
        "InstanceFactory", type);
    return InstanceFactory.getLeastCompleteInstance(PojoClassFactory.getPojoClass(type));

  }
}
