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

package com.openpojo.validation;

import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;

/**
 * This Validator interface defines how PojoValidation is handled.
 *
 * @author oshoukry
 */
public interface Validator {

  /**
   * Validate for a given pojoClass.
   *
   * @param pojoClass
   *     the PojoClass to validate.
   */
  void validate(PojoClass pojoClass);

  /**
   * Validate for a given list of pojo classes.
   *
   * @param pojoClasses
   *     the set of PojoClasses to validate.
   */
  void validate(List<PojoClass> pojoClasses);

  /**
   * Validate for a given package and a filter.
   *
   * @param packageName
   *     The package name to run the validation against.
   * @param filters
   *     The filter(s) to be used for filtering which classes are to be included in the validation.
   *
   * @return the list of PojoClasses that were validated.
   */
  List<PojoClass> validate(String packageName, PojoClassFilter... filters);

  /**
   * Validate for a given package and all its sub-packages with a given filter.
   *
   * @param packageName
   *     The package name to run the validation against.
   * @param filters
   *     The filter(s) to be used for filtering which classes are to be included in the validation.
   *
   * @return the list of PojoClasses that were validated.
   */
  List<PojoClass> validateRecursively(String packageName, PojoClassFilter... filters);

}
