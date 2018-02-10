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

import java.util.List;

/**
 * This class encapsulates the meta data definition of a Java Package.
 *
 * @author oshoukry
 */
public interface PojoPackage extends PojoElement {
  /**
   * Get all PojoClasses in current package.
   *
   * @return Return a list of all classes as PojoClasses in a given package.
   */
  List<PojoClass> getPojoClasses();

  /**
   * Get all Classes in this PojoPackageImpl using defined filter.
   *
   * @param filter
   *     Filter to apply while enumerating;
   * @return List of PojoClasses in package.
   */
  List<PojoClass> getPojoClasses(final PojoClassFilter filter);

  /**
   * Get all child Packages for current Package.
   *
   * @return A list containing PojoPackages for all sub packages.
   */
  List<PojoPackage> getPojoSubPackages();
}
