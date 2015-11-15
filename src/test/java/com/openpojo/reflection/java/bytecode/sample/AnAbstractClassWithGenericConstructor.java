/*
 * Copyright (c) 2010-2015 Osman Shoukry
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License or any
 *    later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
