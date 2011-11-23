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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import com.openpojo.log.LoggerFactory;
import com.openpojo.random.RandomFactory;
import com.openpojo.random.RandomGenerator;

/**
 * This is random generator is responsible for generating NavigableSet interface<br>
 * Currently if a random NavigableSet.class is requested, you will get an object of type TreeSet<br>
 *
 * @author oshoukry
 */
public final class NavigableSetRandomGenerator implements RandomGenerator {

    private NavigableSetRandomGenerator() {
    }

    public static RandomGenerator getInstance() {
        return Instance.INSTANCE;
    }

    private static final String[] TYPES = new String[] { "java.util.NavigableSet" };

    public Object doGenerate(final Class<?> type) {
        return RandomFactory.getRandomValue(TreeSet.class);
    }

    public Collection<Class<?>> getTypes() {
        Set<Class<?>> types = new HashSet<Class<?>>();
        for (String type : TYPES) {
            try {
                types.add(Class.forName(type));
            } catch (ClassNotFoundException e) {
                LoggerFactory.getLogger(this.getClass()).info("Failed to load [{0}], got Exception[{1}]", type, e);
            }
        }
        return types;
    }

    private static class Instance {
        private static final RandomGenerator INSTANCE = new NavigableSetRandomGenerator();
    }
}
