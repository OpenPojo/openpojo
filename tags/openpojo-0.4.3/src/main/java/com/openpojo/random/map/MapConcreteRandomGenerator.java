/*
 * Copyright (c) 2010-2012 Osman Shoukry
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation, either version 3 of the License or any
 *   later version.
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
import java.util.HashMap;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

import com.openpojo.random.RandomGenerator;
import com.openpojo.random.map.util.MapHelper;
import com.openpojo.reflection.construct.InstanceFactory;
import com.openpojo.reflection.impl.PojoClassFactory;

/**
 * This is random generator is responsible for generating concrete Map implementations <br>
 * <strong>Namely:</strong> <br>
 * 1. TreeMap <br>
 * 2. HashMap <br>
 * 3. LinkedHashMap <br>
 * 4. IdentityHashMap <br>
 * 5. WeakHashMap <br>
 * 6. ConcurrentHashMap <br>
 * 7. Hashtable <br>
 *
 * @author oshoukry
 */
public final class MapConcreteRandomGenerator implements RandomGenerator {

    private MapConcreteRandomGenerator() {
    }

    public static RandomGenerator getInstance() {
        return Instance.INSTANCE;
    }

    private final Class<?>[] TYPES = new Class<?>[] { TreeMap.class, HashMap.class, LinkedHashMap.class,
            IdentityHashMap.class, Hashtable.class, // EnumMap.class,
            WeakHashMap.class, ConcurrentHashMap.class };

    @SuppressWarnings("rawtypes")
    public Object doGenerate(final Class<?> type) {
        Map randomMap = null;

        if (this.getTypes().contains(type)) {
            randomMap = (Map) InstanceFactory.getLeastCompleteInstance(PojoClassFactory.getPojoClass(type));
            MapHelper.populateWithRandomData(randomMap);
        }

        return randomMap;
    }

    public Collection<Class<?>> getTypes() {
        return Arrays.asList(TYPES);
    }

    private static class Instance {
        private static final RandomGenerator INSTANCE = new MapConcreteRandomGenerator();
    }
}
