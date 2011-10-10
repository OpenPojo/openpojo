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
package com.openpojo.random.map;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.openpojo.random.RandomFactory;
import com.openpojo.random.RandomGenerator;

/**
 * This is random generator is responsible for generating Map interface <br>
 * Currently if Map.class or AbstractMap.class is requested, you will get <br>
 * a random object of any of the types defined in {@link MapConcreteRandomGenerator} <br>
 *
 * @author oshoukry
 */
public final class MapRandomGenerator implements RandomGenerator {

    private final Random random = new Random(new Date().getTime());
    private final List<Class<?>> mapImplementations = new LinkedList<Class<?>>();

    private MapRandomGenerator() {
        mapImplementations.addAll(MapConcreteRandomGenerator.getInstance().getTypes());

    }

    public static RandomGenerator getInstance() {
        return Instance.INSTANCE;
    }

    private static final Class<?>[] TYPES = new Class<?>[] { Map.class };

    public Object doGenerate(final Class<?> type) {
        return RandomFactory.getRandomValue(mapImplementations.get(random.nextInt(mapImplementations.size())));
    }

    public Collection<Class<?>> getTypes() {
        return Arrays.asList(TYPES);
    }

    private static class Instance {
        private static final RandomGenerator INSTANCE = new MapRandomGenerator();
    }
}
