/**
 * Copyright (C) 2011 Osman Shoukry
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
package com.openpojo.random.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.openpojo.random.RandomGenerator;

/**
 * @author oshoukry
 */
public class DefaultRandomGeneratorService implements com.openpojo.random.service.RandomGeneratorService {
    private final Map<Class<?>, RandomGenerator> concreteRandomGenerator = new HashMap<Class<?>, RandomGenerator>();
    private RandomGenerator defaultRandomGenerator;
    private static final Random RANDOM = new Random(new Date().getTime());

    /*
     * (non-Javadoc)
     *
     * @see com.openpojo.registry.Service#getName()
     */
    public String getName() {
        return this.getClass().getName();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.openpojo.random.service.RandomGeneratorService#registerRandomGenerator(com.openpojo.random.RandomGenerator)
     */
    public void registerRandomGenerator(RandomGenerator randomGenerator) {
        for (Class<?> type : randomGenerator.getTypes()) {
            concreteRandomGenerator.put(type, randomGenerator);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.openpojo.random.service.RandomGeneratorService#registerDefaultRandomGenerator(com.openpojo.random.RandomGenerator
     * )
     */
    public void setDefaultRandomGenerator(RandomGenerator randomGenerator) {
        defaultRandomGenerator = randomGenerator;
    }

    public RandomGenerator getDefaultRandomGenerator() {
        return defaultRandomGenerator;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.openpojo.random.service.RandomGeneratorService#getRandomGeneratorByType(java.lang.Class)
     */
    public RandomGenerator getRandomGeneratorByType(Class<?> type) {
        RandomGenerator appropriateRandomGenerator = concreteRandomGenerator.get(type);
        if (appropriateRandomGenerator == null) {
            List<Class<?>> assignableTypes = getAssignableTypesForType(type, concreteRandomGenerator.keySet());
            if (assignableTypes.size() == 0) {
                appropriateRandomGenerator = getDefaultRandomGenerator();
            } else {
                appropriateRandomGenerator = concreteRandomGenerator.get(assignableTypes.get(RANDOM.nextInt(assignableTypes.size())));
            }
        }

        return appropriateRandomGenerator;
    }

    private List<Class<?>> getAssignableTypesForType(Class<?> type, Set<Class<?>> registeredTypes) {
        List<Class<?>> assignableTypes = new LinkedList<Class<?>>();
        for (Class<?> registeredType : registeredTypes) {
            if (type.isAssignableFrom(registeredType)) {
                assignableTypes.add(registeredType);
            }
        }
        return assignableTypes;
    }
}
