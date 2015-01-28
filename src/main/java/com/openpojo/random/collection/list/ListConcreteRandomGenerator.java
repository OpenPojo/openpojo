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

package com.openpojo.random.collection.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.openpojo.random.ParameterizableRandomGenerator;
import com.openpojo.random.RandomFactory;
import com.openpojo.random.RandomGenerator;
import com.openpojo.random.collection.util.CollectionHelper;
import com.openpojo.random.util.SerializableComparableObject;
import com.openpojo.reflection.Parameterizable;
import com.openpojo.reflection.construct.InstanceFactory;
import com.openpojo.reflection.impl.PojoClassFactory;

/**
 * This is random generator is responsible for generating concrete List implementations<br>
 * <strong>Namely:</strong><br>
 * 1. ArrayList <br>
 * 2. LinkedList <br>
 * <br>
 *
 * @author oshoukry
 */
public final class ListConcreteRandomGenerator implements ParameterizableRandomGenerator {

    private ListConcreteRandomGenerator() {
    }

    public static RandomGenerator getInstance() {
        return Instance.INSTANCE;
    }

    private static final Class<?>[] TYPES = new Class<?>[] { ArrayList.class, LinkedList.class, List.class };

    public Object doGenerate(final Class<?> type) {

        Class<?> typeToGenerate = type;
        if (typeToGenerate == List.class)
            typeToGenerate = ArrayList.class;

        List randomList = (List) InstanceFactory.getLeastCompleteInstance(PojoClassFactory.getPojoClass(typeToGenerate));
        CollectionHelper.buildCollections(randomList, SerializableComparableObject.class);

        return randomList;
    }

    public Collection<Class<?>> getTypes() {
        return Arrays.asList(TYPES);
    }

    public Object doGenerate(Parameterizable parameterizedType) {
        List returnedList = (List) RandomFactory.getRandomValue(parameterizedType.getType());
        returnedList.clear();
        CollectionHelper.buildCollections(returnedList, parameterizedType.getParameterTypes().get(0));
        return returnedList;
    }

    private static class Instance {
        private static final RandomGenerator INSTANCE = new ListConcreteRandomGenerator();
    }
}
