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

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author oshoukry
 */
public interface Parameterizable {
  /**
   * Return the type encapsulated.
   *
   * @return The type of the Parameterizable Object.
   */
  Class<?> getType();

  /**
   * @return True if Parameterized (i.e. List&lt;SomeClass&gt;).
   */
  boolean isParameterized();

  /**
   * Get the generics defined, returns empty list if not Parameterized.
   *
   * @return Return a list of Type that are defined parameterized (i.e. will return a list
   * containing SomeClass for a List&lt;SomeClass&gt;, or [String, Integer] for Map&lt;String, Integer&lt;, ...etc).
   */
  List<Type> getParameterTypes();
}