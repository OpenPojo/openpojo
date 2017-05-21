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

package com.openpojo.issues.issue85;

import java.lang.reflect.Type;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.validation.rule.Rule;
import org.junit.Assert;

/**
 * @author oshoukry
 */
class AllPimitivesAsArraysDeclaredRule implements Rule {
  public void evaluate(PojoClass pojoClass) {
    boolean foundBytes = false,
            foundChars = false,
            foundShorts = false,
            foundInts = false,
            foundLongs = false,
            foundFloats = false,
            foundDoubles = false,
            foundBooleans = false;

    for (PojoField field : pojoClass.getPojoFields()) {
      if (field.isArray()) {
        Type type = field.getParameterTypes().get(0);
        if (type == byte.class)
          foundBytes = true;
        if (type == char.class)
          foundChars = true;
        if (type == short.class)
          foundShorts = true;
        if (type == int.class)
          foundInts = true;
        if (type == long.class)
          foundLongs = true;
        if (type == float.class)
          foundFloats = true;
        if (type == double.class)
          foundDoubles = true;
        if (type == boolean.class)
          foundBooleans = true;
      }
    }
    Assert.assertTrue("byte array not found", foundBytes);
    Assert.assertTrue("char array not found", foundChars);
    Assert.assertTrue("short array not found", foundShorts);
    Assert.assertTrue("int array not found", foundInts);
    Assert.assertTrue("long array not found", foundLongs);
    Assert.assertTrue("float array not found", foundFloats);
    Assert.assertTrue("double array not found", foundDoubles);
    Assert.assertTrue("boolean array not found", foundBooleans);
    Assert.assertEquals("No other fields allowed, only primitive arrays", 8, pojoClass.getPojoFields().size());
  }
}
