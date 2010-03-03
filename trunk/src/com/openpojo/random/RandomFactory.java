/**
 * Copyright (C) 2010 Osman Shoukry
 * 
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.openpojo.random;

import java.util.HashMap;
import java.util.Map;

import com.openpojo.random.exception.RandomGeneratorException;
import com.openpojo.random.impl.BasicRandomGenerator;
import com.openpojo.random.impl.TimestampRandomGenerator;
import com.openpojo.random.thread.GeneratedRandomValues;

/**
 * This factory is responsible for generating the random values using the registered RandomGenerator implementations.
 * 
 * @author oshoukry
 */
public class RandomFactory {

    private static final Map<Class<?>, RandomGenerator> generators = new HashMap<Class<?>, RandomGenerator>();

    static {
        // register defaults with Factory.
        RandomFactory.addRandomGenerator(new BasicRandomGenerator());
        RandomFactory.addRandomGenerator(new TimestampRandomGenerator());
    }

    /**
     * Add a random generator to the list of available generators.
     * The latest random generator registered wins.
     * 
     * @param generator
     *            The generator to add.
     */
    public static synchronized void addRandomGenerator(final RandomGenerator generator) {
        for (Class<?> type : generator.getTypes()) {
            RandomFactory.generators.put(type, generator);
        }
    }

    /**
     * Get a random value for a given type.
     * 
     * @param type
     *            The type to get a random value of.
     * @return
     *         Randomly created value.
     */
    public static final Object getRandomValue(Class<?> type) {
        if (GeneratedRandomValues.contains(type)) {
            return null; // seen before, break loop.
        }
        RandomGenerator randomGenerator = RandomFactory.generators.get(type);
        if (randomGenerator == null) {
            throw new RandomGeneratorException("No Random Generators registered for type " + type.getName());
        }
        GeneratedRandomValues.add(type);
        Object randomValue;
        randomValue = randomGenerator.doGenerate(type);
        GeneratedRandomValues.remove(type);

        return randomValue;
    }
}
