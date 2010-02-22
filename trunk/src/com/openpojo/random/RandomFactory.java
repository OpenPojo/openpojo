/**
 * 2010 Copyright Osman Shoukry.
 */
package com.openpojo.random;

import java.util.HashMap;
import java.util.Map;

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

    public static final Object getRandomValue(Class<?> type) {
        RandomGenerator randomGenerator = RandomFactory.generators.get(type);
        if (randomGenerator == null) {
            throw new RuntimeException("No Random Generators registered for type " + type.getName());
        }
        return randomGenerator.doGenerate(type);
    }
}
