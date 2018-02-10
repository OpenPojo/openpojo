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

package com.openpojo.random.service;

import java.util.Collection;

import com.openpojo.random.RandomGenerator;
import com.openpojo.reflection.Parameterizable;
import com.openpojo.registry.Service;

/**
 * This service holds a registry for RandomGenerators.
 *
 * @author oshoukry
 */
public interface RandomGeneratorService extends Service {

  /**
   * Register a RandomGenerator. The RandomGenerator's getTypes will be invoked and all types returned will be
   * registered against this RandomGenerator over-writing any prior registrations for given types.
   *
   * @param randomGenerator
   *     The random generator to register.
   */
  void registerRandomGenerator(RandomGenerator randomGenerator);

  /**
   * Get all registered types.
   *
   * @return all the registered types in service.
   */
  Collection<Class<?>> getRegisteredTypes();

  /**
   * This method set the RandomGenerator to use if non are registered for given type. This randomGenerator's
   * getTypes() return will be ignored and not consulted for routing.
   *
   * There can only be one (1) default random generator.
   *
   * @param randomGenerator
   *     The random generator to register.
   */
  void setDefaultRandomGenerator(RandomGenerator randomGenerator);

  /**
   * Returns the default registered RandomGenerator;
   *
   * @return the default registered random generator.
   */
  RandomGenerator getDefaultRandomGenerator();

  /**
   * This retrieves the most appropriate RandomGenerator for a given Type. If there is more than one possible random
   * generator valid for a given type, one will randomly be selected.
   *
   * The only reason multiple may exist is due to inheritance, for example if you register two random generators one
   * to handle LinkedList, and one to handle Set, and request a randomGenerator for type "Collection", either of those
   * randomGenerators could be used. However, if there is a random generator registered for Collection, that will be
   * the one used.
   *
   * @param type
   *     The type used to lookup the most appropriate RandomGenerator.
   * @return The random generator to handle generating random values for requested type.
   */
  RandomGenerator getRandomGeneratorByType(Class<?> type);

  /**
   * This retrieves the most appropriate ParameterizableRandomGenerator for a given Type.
   * If there is more than one possible random generator valid for a given type, one will randomly be selected.
   *
   * The only reason multiple may exist is due to inheritance, for example if you register two random generators one
   * to handle LinkedList, and one to handle Set, and request a randomGenerator for type "Collection", either of those
   * randomGenerators could be used. However, if there is a random generator registered for Collection, that will be
   * the one used.
   *
   * @param type
   *     The parameterized type used to lookup the most appropriate RandomGenerator.
   * @return The parameterized random generator to handle generating random values for requested type.
   */
  RandomGenerator getRandomGeneratorByParameterizable(Parameterizable type);
}
