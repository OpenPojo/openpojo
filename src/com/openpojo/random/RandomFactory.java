/**
 * 2010 Copyright Osman Shoukry.
 */
package com.openpojo.random;

import java.util.HashMap;
import java.util.Map;

import com.openpojo.random.impl.BasicRandomGenerator;
import com.openpojo.random.impl.TimestampRandomGenerator;

/**
 * @author oshoukry
 */
public class RandomFactory {

    private static final Map<Class<?>, RandomGenerator> generators = new HashMap<Class<?>, RandomGenerator>();

    static {
        // register with Factory.
        RandomFactory.addRandomGenerator(new BasicRandomGenerator());
        RandomFactory.addRandomGenerator(new TimestampRandomGenerator());
    }

    /**
     * Add a random generator to the list of available generators.
     * @param generator
     *          The generator to add.
     */
    public static synchronized void addRandomGenerator(final RandomGenerator generator) {
        for (Class<?> type : generator.getTypes()) {
            if (RandomFactory.generators.get(type) != null) {
                throw new IllegalStateException(String.format(
                        "[%s AND %s] attempting to register random generators for type=[%s]", RandomFactory.generators
                                .get(type).getClass(), generator.getClass(), type.getName()));
            }
            RandomFactory.generators.put(type, generator);
        }
    }

    /**
     * Get a random value for a given type.
     * @param type
     *          The type to get a random value of.
     * @return
     *          Randomly created value.
     */
    public static final Object getRandomValue(Class<?> type) {
        RandomGenerator randomGenerator = RandomFactory.generators.get(type);
        if (randomGenerator == null) {
            throw new RuntimeException("No Random Generators registered for type " + type.getName());
        }
        return randomGenerator.doGenerate(type);
    }
}
