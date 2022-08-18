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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import com.openpojo.reflection.exception.ReflectionException;

/**
 * This Class is responsible for normalizing field names to attribute names. The reason for this is some companies have
 * policies that require all member variables start with "m" or "its" or any of the hungarian notation prefixes.
 *
 * @author oshoukry
 */
public class AttributeHelper {

  private static Set<String> fieldPrefixes = new CopyOnWriteArraySet<String>();

  /**
   * If your fields are prefixed with some pre-defined string register them here. You can register more than one
   * prefix.
   *
   * @param prefix
   *     The prefix to register.
   */
  public static void registerFieldPrefix(final String prefix) {
    // don't allow null, empty or duplicate prefixes.
    if (prefix != null && prefix.length() > 0) {
      fieldPrefixes.add(prefix);
    }
  }

  /**
   * If a prefix is no longer needed unregister it here.
   *
   * @param prefix
   *     The prefix to un-register.
   */
  public static void unregisterFieldPrefix(final String prefix) {
    fieldPrefixes.remove(prefix);
  }

  /**
   * This method removes all registered values.
   */
  public static void clearRegistry() {
    fieldPrefixes.clear();
  }

  public static List<String> getFieldNameVariations(final Field field) {
    List<String> fieldNameVariations = new ArrayList<String>();
    fieldNameVariations.add(formattedFieldName(field.getName()));
    try {
      String withoutPrefix = getAttributeName(field);
      if (!fieldNameVariations.contains(withoutPrefix))
        fieldNameVariations.add(withoutPrefix);
    } catch (ReflectionException ignored) { /* ignored */ }
    return fieldNameVariations;
  }

  /**
   * This method returns the attribute name given a field name. The field name will get stripped of prefixes
   * @deprecated Please rewire to utilize getFieldNameVariations instead.
   *
   * @param field
   *     The field to inspect for attribute name
   * @return Normalized attribute name
   */
  @Deprecated
  public static String getAttributeName(final Field field) {
    String normalizedFieldName = field.getName();
    normalizedFieldName = stripPrefix(normalizedFieldName);
    return formattedFieldName(normalizedFieldName);
  }

  private static String stripPrefix(final String fieldName) {
    for (String prefix : fieldPrefixes) {
      if (fieldName.equals(prefix)) {
        throw ReflectionException.getInstance(
            String.format("Field name =[%s] matches registered prefix=[%s], if stripped, empty string will result",
                fieldName, prefix));
      }
      if (fieldName.startsWith(prefix)) {
        return fieldName.substring(prefix.length(), fieldName.length());
      }
    }
    return fieldName;
  }

  /**
   * Properly formatted field name, this will change the first letter to upper case only if the second letter isn't
   * upper.
   *
   * @param fieldName
   *     The field to proper case.
   * @return Formatted field name.
   */
  private static String formattedFieldName(final String fieldName) {
    if (isSecondLetterUpperCase(fieldName)) {
      return fieldName;
    }
    return camelCase(fieldName);
  }

  private static boolean isSecondLetterUpperCase(String fieldName) {
    return fieldName.length() > 1 && Character.isUpperCase(Character.codePointAt(fieldName, 1));
  }

  private static String camelCase(String fieldName) {
    return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length());
  }

  private AttributeHelper() {
    throw new UnsupportedOperationException(AttributeHelper.class.getName() + " should not be constructed!");
  }
}
