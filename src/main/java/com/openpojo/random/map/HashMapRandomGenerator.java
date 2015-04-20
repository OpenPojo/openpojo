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

package com.openpojo.random.map;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.openpojo.random.exception.RandomGeneratorException;
import com.openpojo.random.map.util.AbstractMapRandomGenerator;
import com.openpojo.random.map.util.MapHelper;
import com.openpojo.random.util.SerializableComparableObject;

/**
 * @author oshoukry
 */
public class HashMapRandomGenerator extends AbstractMapRandomGenerator {
    private static final Class<?>[] TYPES = new Class<?>[] { HashMap.class };
    private static final HashMapRandomGenerator INSTANCE = new HashMapRandomGenerator();

    public static HashMapRandomGenerator getInstance() {
        return INSTANCE;
    }

    public Collection<Class<?>> getTypes() {
        return Arrays.asList(TYPES);
    }

    @Override
    protected Map getBasicInstance(Class<?> type) {
        if (!isAssignableTo(type))
            throw RandomGeneratorException.getInstance("Invalid type requested [" + type + "]");
        return MapHelper.buildMap(new HashMap(), SerializableComparableObject.class, SerializableComparableObject.class);
    }

    private HashMapRandomGenerator() {
    }
}
