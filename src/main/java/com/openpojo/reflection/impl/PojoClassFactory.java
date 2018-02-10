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

package com.openpojo.reflection.impl;

import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;
import com.openpojo.registry.ServiceRegistrar;

/**
 * This is a factory class that builds PojoClassImpl representation given a class.
 *
 * @author oshoukry
 */
public final class PojoClassFactory {

  /**
   * Create a PojoClass for a given application Class.
   *
   * @param clazz
   *     Class to introspect.
   * @return A PojoClass meta representation for the clazz.
   */
  public static PojoClass getPojoClass(final Class<?> clazz) {
    return ServiceRegistrar.getInstance().getPojoClassLookupService().getPojoClass(clazz);
  }

  /**
   * This method returns a list of PojoClasses in a package representation.
   *
   * @param packageName
   *     Package to introspect (eg. com.mypackage.pojo).
   * @return A list of PojoClasses.
   */
  public static List<PojoClass> getPojoClasses(final String packageName) {
    return ServiceRegistrar.getInstance().getPojoClassLookupService().getPojoClasses(packageName);
  }

  /**
   * This method returns a list of PojoClasses in a package representation with filtering capabilities.
   *
   * @param packageName
   *     Package to introspect (eg. com.mypackage.pojo).
   * @param pojoClassFilter
   *     The filter to apply to the list of PojoClasses.
   * @return A list of PojoClasses.
   */
  public static List<PojoClass> getPojoClasses(final String packageName, final PojoClassFilter pojoClassFilter) {
    return ServiceRegistrar.getInstance().getPojoClassLookupService().getPojoClasses(packageName, pojoClassFilter);
  }

  /**
   * This method enumerates all classes in a package path. This method will enumerate using the class loader, so if
   * you're tests live in the same package as your code, make sure you pass in a filter that can weed those out for
   * testing.
   *
   * @param packageName
   *     The package name in question.
   * @param pojoClassFilter
   *     The filter to use.
   * @return List of PojoClasses enumerated.
   */
  public static List<PojoClass> getPojoClassesRecursively(final String packageName, final PojoClassFilter pojoClassFilter) {
    return ServiceRegistrar.getInstance().getPojoClassLookupService().getPojoClassesRecursively(packageName, pojoClassFilter);
  }

  /**
   * Return a list of classes that implement/extend a given type
   *
   * @param packageName
   *     Parent package to recurse through.
   * @param type
   *     Inheritance type (can be interface / abstract class or class).
   * @param pojoClassFilter
   *     A filter to use for PojoClasses.
   * @return List of all Pojo's that extend type.
   */
  public static List<PojoClass> enumerateClassesByExtendingType(final String packageName, final Class<?> type, final
  PojoClassFilter pojoClassFilter) {
    return ServiceRegistrar.getInstance().getPojoClassLookupService().enumerateClassesByExtendingType(packageName, type,
        pojoClassFilter);
  }

  private PojoClassFactory() {
    throw new UnsupportedOperationException(PojoClassFactory.class.getName() + " should not be constructed!");
  }
}
