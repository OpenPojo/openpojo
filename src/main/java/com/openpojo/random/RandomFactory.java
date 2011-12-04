/**
 * Copyright (C) 2010 Osman Shoukry
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.openpojo.random;

import com.openpojo.log.Logger;
import com.openpojo.log.LoggerFactory;
import com.openpojo.random.exception.RandomGeneratorException;
import com.openpojo.random.service.RandomGeneratorService;
import com.openpojo.random.thread.GeneratedRandomValues;
import com.openpojo.registry.ServiceRegistrar;

/**
 * This factory is responsible for generating the random values using the registered RandomGenerator(s). <br>
 * This Factory will automatically detect cyclic dependency and return null for the second time around.<br>
 * <br>
 * <i>Simple Example</i><br>
 * If you have an Employee class that has the following constructor:
 *
 * <pre>
 * {@code
 * public Employee(final String fullName, final Employee manager) {
 *   ...
 * }
 * }
 * </pre>
 *
 * And you created the random generator as follows:
 *
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
     *            The generator to add.
     */
    public static synchronized void addRandomGenerator(final RandomGenerator generator) {
        getRandomGeneratorService().registerRandomGenerator(generator);
    }

    /**
     * This method generates a random value of the requested type.<br>
     * If the requested type isn't registered in the factory, an RandomGeneratorException will be thrown.
     *
     * @param type
     *            The type to get a random value of.
     * @return Randomly created value.
     */
    public static final Object getRandomValue(final Class<?> type) {
        if (GeneratedRandomValues.contains(type)) {
            logger.warn("Cyclic dependency on random generator for type=[{0}] detected, returning null", type);
            return null; // seen before, break loop.
        }

        GeneratedRandomValues.add(type);

        try {
            RandomGenerator randomGenerator = getRandomGeneratorService().getRandomGeneratorByType(type);

            if (randomGenerator == null) {
                throw RandomGeneratorException.getInstance("No randomGenerator Found for type " + type);
            }

            return randomGenerator.doGenerate(type);
        } finally {
            GeneratedRandomValues.remove(type);
        }
    }

    private static RandomGeneratorService getRandomGeneratorService() {
        return ServiceRegistrar.getInstance().getRandomGeneratorService();
    }
}
