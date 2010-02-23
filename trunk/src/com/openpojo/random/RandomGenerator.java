package com.openpojo.random;

import java.util.Collection;

/**
 * Random Generator interface, this is the contract the {@link RandomFactory} expects.
 * @author oshoukry
 */
public interface RandomGenerator {
    /**
     * This method is used to get the types that this RandomGenerator is responsible for.
     * @return
     *      A collection with a list of Types this Random Generator can handle.
     */
    public Collection<Class<?>> getTypes();

    /**
     * Perform random generation.
     * @param type
     *          The type to generate for.
     * @return
     *          A random Object dynamically created.
     */
    public Object doGenerate(Class<?> type);
}
