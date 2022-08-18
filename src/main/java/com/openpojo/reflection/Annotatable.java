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
 * @author oshoukry
 */
public interface Annotatable {
  /**
   * Get all annotations defined on element.
   *
   * @return Get Annotations
   */
  List<? extends Annotation> getAnnotations();

  /**
   * Get specified instance of an annotation defined on element.
   *
   * @param <T>
   *     Class Type of annotation.
   * @param annotationClass
   *     The annotation class.
   * @return The definition of this annotation on the PojoField.
   */
  <T extends Annotation> T getAnnotation(Class<T> annotationClass);
}
