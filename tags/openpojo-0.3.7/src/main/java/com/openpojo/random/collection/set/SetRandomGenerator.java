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
package com.openpojo.random.collection.set;

import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.openpojo.random.RandomFactory;
import com.openpojo.random.RandomGenerator;

/**
 * This is random generator is responsible for generating Set interface<br>
 * Currently if a random Set.class or AbstractSet.class is requested, <br>
 * you will get a random object of any of the types defined in {@link SetConcreteRandomGenerator} <br>
 *
 * @author oshoukry
 */
public final class SetRandomGenerator implements RandomGenerator {

    private final Random random = new Random(new Date().getTime());
    private final List<Class<?>> setImplementations = new LinkedList<Class<?>>();

    private SetRandomGenerator() {
        setImplementations.addAll(SetConcreteRandomGenerator.getInstance().getTypes());
    }

    public static RandomGenerator getInstance() {
        return Instance.INSTANCE;
    }

    private static final Class<?>[] TYPES = new Class<?>[] { Set.class, AbstractSet.class };

    public Object doGenerate(final Class<?> type) {
        return RandomFactory.getRandomValue(setImplementations.get(random.nextInt(setImplementations.size())));
    }

    public Collection<Class<?>> getTypes() {
        return Arrays.asList(TYPES);
    }

    private static class Instance {
        private static final RandomGenerator INSTANCE = new SetRandomGenerator();
    }
}
