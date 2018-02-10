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

package com.openpojo.reflection.utils;

import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;

/**
 * This class is a collection of commonly used functions used for various toString(Object) calls.
 *
 * @author oshoukry
 */
public final class ToStringHelper {
  private static final String NAME_VALUE_TOKEN_FORMAT = "%s=%s";
  private static final String LIST_TOKEN_SEPARATOR = ", ";
  private static final String POJO_CLASS_TO_STRING_FORMAT = "%s [@%s: %s]";

  /**
   * This method formats name value pairs into the proper string format.
   *
   * @param name
   *     The name to format.
   * @param value
   *     The value to format.
   * @return String formatted, human readable name/value pair.
   */
  public static String nameValuePair(final Object name, final Object value) {
    String valueString = ObjectToString.toString(value);
    return String.format(NAME_VALUE_TOKEN_FORMAT, name, valueString);
  }

  /**
   * This method takes an object instance for a pojoClass and flattens it into a properly formatted string.
   *
   * @param pojoClass
   *     The meta representation of the instance class.
   * @param instance
   *     The instance to format.
   * @return String formatted, human readable class.
   */

  public static String pojoClassToString(final PojoClass pojoClass, final Object instance) {
    return String.format(POJO_CLASS_TO_STRING_FORMAT,
        pojoClass.getName(),
        Integer.toHexString(System.identityHashCode(instance)),
        PojoFieldsToString(pojoClass.getPojoFields(), instance));
  }

  /**
   * This method takes a list of PojoFields and turns them into token separated name-value pairs.
   *
   * @param pojoFields
   *     The list of pojoFields to render.
   * @param instance
   *     The object instance to get the values out of.
   * @return String formatted, human readable list of pojoFields.
   */
  private static String PojoFieldsToString(final List<PojoField> pojoFields, final Object instance) {
    String pojoString = "";
    for (int index = 0; index < pojoFields.size(); index++) {
      pojoString += pojoFields.get(index).toString(instance);
      if (index < pojoFields.size() - 1) {
        pojoString += LIST_TOKEN_SEPARATOR;
      }
    }
    return pojoString;
  }

  private ToStringHelper() {
    throw new UnsupportedOperationException(ToStringHelper.class.getName() +  " should not be constructed!");
  }
}
