/*
 * Copyright (c) 2010-2015 Osman Shoukry
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

package com.openpojo.random.collection.set;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

import com.openpojo.random.ParameterizableRandomGenerator;
import com.openpojo.random.RandomGenerator;
import com.openpojo.random.collection.util.CollectionHelper;
import com.openpojo.random.util.SerializableComparableObject;
import com.openpojo.reflection.Parameterizable;
import com.openpojo.reflection.construct.InstanceFactory;
import com.openpojo.reflection.impl.PojoClassFactory;

/**
 * This is random generator is responsible for generating concrete Set implementations <br>
 * <strong>Namely:</strong><br>
 * 1. HashSet <br>
 * 2. TreeSet <br>
 * 3. LinkedHashSet <br>
 * <br>
 *
 * @author oshoukry
 */
public final class SetConcreteRandomGenerator implements ParameterizableRandomGenerator {

    private SetConcreteRandomGenerator() {
    }

    public static RandomGenerator getInstance() {
        return Instance.INSTANCE;
    }

    private final Class<?>[] TYPES = new Class<?>[] { HashSet.class, TreeSet.class, LinkedHashSet.class, Set.class };

    @SuppressWarnings("rawtypes")
    public Object doGenerate(final Class<?> type) {

        Class<?> typeToGenerate = type;
        if (type == Set.class)
            typeToGenerate = HashSet.class;

        Set randomSet = (Set) InstanceFactory.getLeastCompleteInstance(PojoClassFactory.getPojoClass(typeToGenerate));
        CollectionHelper.buildCollections(randomSet, SerializableComparableObject.class);

        return randomSet;
    }

    public Collection<Class<?>> getTypes() {
        return Arrays.asList(TYPES);
    }

    public Object doGenerate(Parameterizable parameterizedType) {
        Set initialSet = new HashSet();
        CollectionHelper.buildCollections(initialSet, parameterizedType.getParameterTypes().get(0));
        return initialSet;
    }

    private static class Instance {
        private static final RandomGenerator INSTANCE = new SetConcreteRandomGenerator();
    }
}
