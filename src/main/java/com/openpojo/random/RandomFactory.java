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

import com.openpojo.log.Logger;
import com.openpojo.log.LoggerFactory;
import com.openpojo.random.exception.RandomGeneratorException;
import com.openpojo.random.service.RandomGeneratorService;
import com.openpojo.random.thread.GeneratedRandomValues;
import com.openpojo.reflection.Parameterizable;
import com.openpojo.registry.ServiceRegistrar;

/**
 * This factory is responsible for generating the random values using the registered RandomGenerator(s). <br>
 * This Factory will automatically detect cyclic dependency and return null for the second time around.<br>
 * <br>
 * <i>Simple Example</i><br>
 * If you have an Employee class that has the following constructor:
 * <pre>
 * {@code
 * public Employee(final String fullName, final Employee manager) {
 *   ...
 * }
 * }
 * </pre>
 *
 * And you created the random generator as follows:
 * <pre>
 * {@code
 * public Object doGenerate(Class<?> type) {
 *     return new Employee(RandomFactory.getRandomValue(String.class),
 *                          (Employee) RandomFactory.getRandomValue(Employee.class));
 * }
 * }
 * </pre>
 *
 * This would potentially cause a stack over-flow since there is a cyclic dependency of Employee on itself. So to
 * prevent stack over-flow (which would occur by trying to create a manager for every manager), this Factory has built
 * in protection (using {@link GeneratedRandomValues}) to prevent such a thing by recording for a current recursive call
 * if it's seen this type before, if so, it will return null the second time around.
 *
 * @author oshoukry
 */
public class RandomFactory {
  private static final Logger logger = LoggerFactory.getLogger(RandomFactory.class);

  /**
   * Add a random generator to the list of available generators. The latest random generator registered wins.
   *
   * @param generator
   *     The generator to add.
   */
  public static synchronized void addRandomGenerator(final RandomGenerator generator) {
    getRandomGeneratorService().registerRandomGenerator(generator);
  }

  /**
   * This method generates a random value of the requested type.<br>
   * If the requested type isn't registered in the factory, an RandomGeneratorException will be thrown.
   *
   * @param type
   *     The type to get a random value of.
   * @param <T>
   *     The class type to generate an object for.
   * @return Randomly created value.
   */
  @SuppressWarnings("unchecked")
  public static <T> T getRandomValue(final Class<T> type) {
    if (GeneratedRandomValues.contains(type)) {
      logger.warn("Cyclic dependency on random generator for type=[{0}] detected, returning null", type);
      return null; // seen before, break loop.
    }

    GeneratedRandomValues.add(type);

    try {
      final RandomGenerator randomGenerator = getRandomGeneratorService().getRandomGeneratorByType(type);

      if (randomGenerator == null) {
        throw RandomGeneratorException.getInstance("No randomGenerator Found for type " + type);
      }

      return (T) randomGenerator.doGenerate(type);
    } finally {
      GeneratedRandomValues.remove(type);
    }
  }

  public static Object getRandomValue(final Parameterizable parameterizable) {
    if (!parameterizable.isParameterized())
      return getRandomValue(parameterizable.getType());

    RandomGenerator randomGenerator = getRandomGeneratorService().getRandomGeneratorByParameterizable(parameterizable);
    if (randomGenerator instanceof ParameterizableRandomGenerator) {
      return ((ParameterizableRandomGenerator) randomGenerator).doGenerate(parameterizable);
    }

    logger.warn("No ParametrizableRandomGenerator implementation found for parameterized type [" + parameterizable + "] " +
        "creating non-parameterized instance ");
    return getRandomValue(parameterizable.getType());
  }

  private static RandomGeneratorService getRandomGeneratorService() {
    return ServiceRegistrar.getInstance().getRandomGeneratorService();
  }

  private RandomFactory() {
    throw new UnsupportedOperationException(RandomFactory.class.getName() + " should not be constructed!");
  }
}
