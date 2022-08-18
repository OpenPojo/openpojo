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

package com.openpojo.business.utils;

import java.lang.reflect.Array;

import com.openpojo.business.cache.BusinessKeyField;
import com.openpojo.business.exception.BusinessException;

/**
 * This class is just a utility class that holds a few utilities needed by various classes in the business package.
 *
 * @author oshoukry
 */
public class BusinessIdentityUtils {

  private BusinessIdentityUtils() {
    throw new UnsupportedOperationException(BusinessIdentityUtils.class.getName() + " should not be constructed!");
  }

  /**
   * @param objects
   *     List of objects to check if any are null.
   * @return True if any of the objects passed is null, false otherwise.
   */
  public static boolean anyNull(final Object... objects) {
    if (objects == null) {
      throw BusinessException.getInstance("objects parameter cannot be null");
    }
    for (Object o : objects) {
      if (o == null) {
        return true;
      }
    }
    return false;
  }

  /**
   * This method checks if two objects are of the same class.
   *
   * @param first
   *     First object to check.
   * @param second
   *     Second object to check.
   * @return True if both have the same class, false otherwise.
   */
  public static boolean sameClass(final Object first, final Object second) {
    return first.getClass() == second.getClass();
  }

  /**
   * This method checks if two object point to the same instance.
   *
   * @param first
   *     First object to check.
   * @param second
   *     Second object to check.
   * @return True if both first and second point to the same instance, false otherwise.
   */
  public static boolean sameInstance(final Object first, final Object second) {
    return first == second;
  }

  /**
   * Perform Equality between two PojoFields' instances.
   * This is different from regular equals implementation that it will return true
   * if both fields are null, as well as it takes into account case based equality for character sequences.
   *
   * @param pojoField
   *     The pojoField to perform the equality on.
   * @param first
   *     The first instance that contains this PojoField.
   * @param second
   *     The second instance that contains this PojoField.
   * @param caseSensitive
   *     Whether or not to compare ignoring case.
   * @return True if they are equal or if they are both null.
   */
  public static boolean areEqual(final BusinessKeyField pojoField, final Object first, final Object second, final boolean
      caseSensitive) {
    Object firstField = pojoField.get(first);
    Object secondField = pojoField.get(second);
    if (firstField == null) {
      return secondField == null;
    }

    return secondField != null && doEquals(firstField, secondField, caseSensitive);

  }

  private static boolean doEquals(Object first, Object second, boolean caseSensitive) {
    if (first == second)
      return true;

    if (first == null) {
      return false;
    }

    if (first.getClass().isArray()) {
      if (Array.getLength(first) != Array.getLength(second)) {
        return false;
      }

      boolean runningEquality = true;
      for (int idx = 0; idx < Array.getLength(first); idx++) {
        runningEquality = runningEquality && doEquals(Array.get(first, idx), Array.get(second, idx), caseSensitive);
      }
      return runningEquality;
    }

    if (!caseSensitive && isCharacterBased(first)) {
      return first.toString().equalsIgnoreCase(second.toString());
    }

    return first.equals(second);
  }

  /**
   * Generate HashCode on field value.
   *
   * @param pojoField
   *     The pojoField to get the hashCode for.
   * @param instance
   *     The instance to pull the field value out of.
   * @param caseSensitive
   *     Whether or not to ignore case while generating hash code.
   * @return The generated HashCode.
   */
  public static int getHashCode(final BusinessKeyField pojoField, final Object instance, final boolean caseSensitive) {
    Object data = pojoField.get(instance);
    if (data == null) {
      return 0;
    }

    return doHashCode(data, caseSensitive);
  }

  private static int doHashCode(Object data, boolean caseSensitive) {
    if (data == null) {
      return 1;
    }

    if (data.getClass().isArray()) {
      final int prime = 31;
      int result = 1;

      for (int idx = 0; idx < Array.getLength(data); idx++) {
        result = prime * result + doHashCode(Array.get(data, idx), caseSensitive);
      }

      return result;
    }

    if (!caseSensitive && isCharacterBased(data)) {
      return data.toString().toLowerCase().hashCode();
    }

    return data.hashCode();
  }

  /**
   * This method holds the logic needed to determine that a type is of character
   * (i.e. its contents can be pulled out using toString)
   *
   * @param data
   *     The data to be checked.
   * @return True if data is of type Character or CharSequence, false otherwise.
   */
  private static boolean isCharacterBased(final Object data) {
    return (data instanceof Character || data instanceof CharSequence);
  }
}
