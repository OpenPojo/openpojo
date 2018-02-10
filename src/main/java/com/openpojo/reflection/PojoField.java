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

package com.openpojo.reflection;

/**
 * This class encapsulates the meta data definition of a field on a class.
 *
 * @author oshoukry
 */
public interface PojoField extends PojoElement, Parameterizable, Accessible {

  /**
   * This method gets the value of the field.
   *
   * @param instance
   *     The instance to extract the value out of.
   * @return The value of the field.
   */
  Object get(final Object instance);

  /**
   * This method sets the value of the field.
   *
   * @param instance
   *     The instance to set the value on.
   * @param value
   *     The value to set it to.
   */
  void set(final Object instance, final Object value);

  /**
   * Returns true if this field has a getter method.
   *
   * @return Returns true if the getter is set.
   */
  boolean hasGetter();

  /**
   * Returns getter PojoMethod or null if none are set.
   *
   * @return Returns getter if one is set, null otherwise.
   */
  PojoMethod getGetter();

  /**
   * This method will invoke the getter method.
   *
   * @param instance
   *     The instance of the class to invoke the getter on.
   * @return The value of the field.
   */
  Object invokeGetter(final Object instance);

  /**
   * Returns true if this field has a setter method.
   *
   * @return Returns true if the setter is set.
   */
  boolean hasSetter();

  /**
   * Returns setter PojoMethod or null if none are set.
   *
   * @return Returns setter if one is set, null otherwise.
   */
  PojoMethod getSetter();

  /**
   * @return Returns the enclosing PojoClass.
   */
  PojoClass getDeclaringPojoClass();

  /**
   * This method will invoke the setter method.
   *
   * @param instance
   *     The instance of the class to invoke the setter on.
   * @param value
   *     The value to set the field to.
   */
  void invokeSetter(final Object instance, final Object value);

  /**
   * @return True if the field is of primitive type.
   */
  boolean isPrimitive();

  /**
   * @return True if this PojoField is defined as final on the enclosing class.
   */
  boolean isFinal();

  /**
   * @return True if this PojoField is defined as static on the enclosing class.
   */
  boolean isStatic();

  /**
   * @return True if this PojoField is defined as transient on the enclosing class.
   */
  boolean isTransient();

  /**
   * @return True if this PojoField is defined as volatile on the enclosing class.
   */
  boolean isVolatile();

  /**
   * @return True if this PojoField is defined as array on the enclosing class.
   */
  boolean isArray();

  /**
   * @return true if this PojoField is synthetic (i.e. jdk compiler generated).
   */
  boolean isSynthetic();

  /**
   * Returns properly formatted field=value string from instance.
   *
   * @param instance
   *     The instance to pull the field value off of.
   * @return A string representation of the field and value.
   */
  String toString(Object instance);

}