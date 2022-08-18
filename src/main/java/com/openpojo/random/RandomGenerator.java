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

package com.openpojo.random;

import java.util.Collection;

/**
 * Random Generator interface, this is the contract the {@link RandomFactory} expects.
 *
 * @author oshoukry
 */
public interface RandomGenerator {
  /**
   * This method is used to get the types that this RandomGenerator is responsible for.
   *
   * @return A collection with a list of Types this Random Generator can handle.
   */
  Collection<Class<?>> getTypes();

  /**
   * Perform random generation.
   *
   * @param type
   *     The type to generate for.
   * @return A random Object dynamically created.
   */
  Object doGenerate(Class<?> type);
}
