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

package com.openpojo.reflection.java.bytecode.sample;

import java.util.List;
import java.util.Map;

import com.openpojo.issues.issue28.sample.AnotherChildClass;
import com.openpojo.random.dynamic.sampleclasses.AConcreteClass;

/**
 * @author oshoukry
 */
public abstract class AnAbstractClassWithGenericConstructor {
  public AnAbstractClassWithGenericConstructor(
      List<String> aListOfStrings, Integer number, Map<AConcreteClass, AnotherChildClass> map) {
    if (aListOfStrings == null
        || aListOfStrings.isEmpty()
        || !listOfStrings(aListOfStrings)
        || number == null
        || map.isEmpty()
        || !mapOfConcreteClassAndAnotherChildClass(map)) {
      throw new IllegalArgumentException("Invalid construction parameters [" + aListOfStrings + "], [" + number + "], ["
          + map + "]");
    }
  }

  private boolean mapOfConcreteClassAndAnotherChildClass(Map<AConcreteClass, AnotherChildClass> map) {
    if (map.isEmpty())
      return false;
    for (Map.Entry entry : map.entrySet()) {
      if (entry.getKey() == null
          || !(entry.getKey() instanceof AConcreteClass)
          || entry.getValue() == null
          || !(entry.getValue
          () instanceof AnotherChildClass))
        return false;
    }
    return true;
  }

  private boolean listOfStrings(List list) {
    if (list.isEmpty())
      return false;

    for (Object o : list) {
      if (!(o instanceof String)) {
        return false;
      }
    }
    return true;
  }
}
