/*
 * Copyright (c) 2010-2014 Osman Shoukry
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License or any
 *    later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.openpojo.random.service.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.openpojo.random.RandomGenerator;
import com.openpojo.random.service.RandomGeneratorService;

/**
 * @author oshoukry
 */
public class DefaultRandomGeneratorService implements RandomGeneratorService {
    private final Map<Class<?>, RandomGenerator> concreteRandomGenerator = new HashMap<Class<?>, RandomGenerator>();
    private RandomGenerator defaultRandomGenerator;
    private static final Random RANDOM = new Random(new Date().getTime());

    public String getName() {
        return this.getClass().getName();
    }

    public void registerRandomGenerator(final RandomGenerator randomGenerator) {
        for (final Class<?> type : randomGenerator.getTypes()) {
            concreteRandomGenerator.put(type, randomGenerator);
        }
    }

    public void setDefaultRandomGenerator(final RandomGenerator randomGenerator) {
        defaultRandomGenerator = randomGenerator;
    }

    public RandomGenerator getDefaultRandomGenerator() {
        return defaultRandomGenerator;
    }

    public RandomGenerator getRandomGeneratorByType(final Class<?> type) {
        RandomGenerator appropriateRandomGenerator = concreteRandomGenerator.get(type);
        if (appropriateRandomGenerator == null) {
            final List<Class<?>> assignableTypes = getAssignableTypesForType(type, concreteRandomGenerator.keySet());
            if (assignableTypes.size() == 0) {
                appropriateRandomGenerator = getDefaultRandomGenerator();
            } else {
                final Class<?> adaptToType = assignableTypes.get(RANDOM.nextInt(assignableTypes.size()));
                appropriateRandomGenerator = new RandomGeneratorAdapter(type, adaptToType,
                                                                        concreteRandomGenerator.get(adaptToType));
            }
        }
        return appropriateRandomGenerator;
    }

    public Collection<Class<?>> getRegisteredTypes() {
        return Collections.unmodifiableSet(concreteRandomGenerator.keySet());
    }

    private List<Class<?>> getAssignableTypesForType(final Class<?> type, final Set<Class<?>> registeredTypes) {
        final List<Class<?>> assignableTypes = new LinkedList<Class<?>>();
        for (final Class<?> registeredType : registeredTypes) {
            if (type.isAssignableFrom(registeredType)) {
                assignableTypes.add(registeredType);
            }
        }
        return assignableTypes;
    }

}
