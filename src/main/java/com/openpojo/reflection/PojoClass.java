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

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * This Interface defines the contract published by PojoClass implementations.
 * The idea is to give an easy and flexible way to work with application POJO classes.
 *
 * @author oshoukry
 */
public interface PojoClass extends PojoElement, Accessible {

  /**
   * Check if PojoClass wraps an interface.
   *
   * @return true if this PojoClass wraps an interface.
   */
  boolean isInterface();

  /**
   * Check if PojoClass wraps an abstract.
   *
   * @return true if this PojoClass wraps an abstract class.
   */
  boolean isAbstract();

  /**
   * Check if PojoClass wraps a concrete (i.e. can be instantiated).
   *
   * @return true if this PojoClass wraps a concrete class.
   */
  boolean isConcrete();

  /**
   * Check if PojoClass wraps an enum.
   *
   * @return true if this PojoClass wraps an enum type.
   */
  boolean isEnum();

  /**
   * Check if PojoClass wraps an array.
   *
   * @return true if this PojoClass wraps an array class.
   */
  boolean isArray();

  /**
   * Check if PojoClass wraps a final class.
   *
   * @return true if this PojoClass wraps a final class.
   */
  boolean isFinal();

  /**
   * @return true if this PojoField is synthetic (i.e. jdk compiler generated).
   */
  boolean isSynthetic();

  /**
   * Get all PojoFields defined in the class.
   *
   * @return the pojoFields
   */
  List<PojoField> getPojoFields();

  /**
   * Get all PojoFields annotated with given annotation.
   *
   * @param annotation
   *     the annotation to use for lookup.
   * @return the list of fields that are annotated with given annotation.
   */
  List<PojoField> getPojoFieldsAnnotatedWith(Class<? extends Annotation> annotation);

  /**
   * Get all PojoMethods defined in the class;
   *
   * @return the list of all methods defined in class, this includes constructors and synthetically added methods by the
   * compiler.
   */
  List<PojoMethod> getPojoMethods();

  /**
   * Get all PojoMethods annotated with given annotation
   *
   * @param annotation
   *     the annotation to use for lookup.
   * @return a list of methods that are annotated with given annotation.
   */
  List<PojoMethod> getPojoMethodsAnnotatedWith(Class<? extends Annotation> annotation);

  /**
   * Get all Constructors defined in the class.
   *
   * @return the list of constructors.
   */
  List<PojoMethod> getPojoConstructors();

  /**
   * If this is a nested class, get enclosing class.
   *
   * @return the enclosing class' PojoClass, or null.
   */
  PojoClass getEnclosingClass();

  /**
   * Checks to see if class extends/implements a certain type.
   *
   * @param type
   *     The type in question.
   * @return True if class is subclass or implements an interface, otherwise false.
   */
  boolean extendz(final Class<?> type);

  /**
   * Return the super class of the class represented by this PojoClass class.
   *
   * @return PojoClass representing the super class of this class or null if none exist.
   */
  PojoClass getSuperClass();

  /**
   * Returns a list of all interfaces implemented by the class represented by this PojoClass.
   *
   * @return The list of interfaces implemented by the class wrapped by this PojoClass.
   */
  List<PojoClass> getInterfaces();

  /**
   * Get Enclosing Package
   *
   * @return the package that has this class is part of.
   */
  PojoPackage getPackage();

  /**
   * This method returns the underlying class represented by this instance.
   *
   * @return The class type wrapped by this PojoClass.
   */
  Class<?> getClazz();

  /**
   * Checks to see if this class is a nested subclass.
   *
   * @return True if it is a subclass, false otherwise.
   */
  boolean isNestedClass();

  /**
   * Check if PojoClass wraps a static class.
   * This would be true for example when a class is defined as nested and is declared static.
   *
   * @return true if this PojoClass wraps a static class.
   */
  boolean isStatic();

  /**
   * Copy all contents from one Instance represented by this PojoClass to another.
   *
   * @param from
   *     The Instance to copy from.
   * @param to
   *     The Instance to copy to.
   */
  void copy(final Object from, final Object to);

  /**
   * This method converts a pojoClass instance's contents to a string.
   * This method can serve as a good delegate for all toString().
   *
   * @param instance
   *     The instance to print the contents out of.
   * @return String representation of the instance.
   */
  String toString(Object instance);

  /**
   * This method returns the location from which this class was loaded.
   * Note: Do not use, still in experimental mode.
   *
   * @return String of the PATH of where this class was loaded from.
   */
  String getSourcePath();

}
