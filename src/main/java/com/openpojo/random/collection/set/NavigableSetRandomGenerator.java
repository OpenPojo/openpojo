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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

import com.openpojo.random.collection.util.BaseCollectionRandomGenerator;
import com.openpojo.random.exception.RandomGeneratorException;
import com.openpojo.reflection.java.load.ClassUtil;

/**
 * @author oshoukry
 */
public class NavigableSetRandomGenerator extends BaseCollectionRandomGenerator {
    private static final String TYPE = "java.util.NavigableSet";
    private static final NavigableSetRandomGenerator INSTANCE = new NavigableSetRandomGenerator();

    public static NavigableSetRandomGenerator getInstance() {
        return INSTANCE;
    }

    public Collection<Class<?>> getTypes() {
        List<Class<?>> types = new ArrayList<Class<?>>();
        if (ClassUtil.isClassLoaded(TYPE))
            types.add(ClassUtil.loadClass(TYPE));
        return types;
    }

    @Override
    protected Collection getBasicInstance(Class<?> type) {
        if (!isAssignableTo(type))
            throw RandomGeneratorException.getInstance("Invalid type requested [" + type + "]");
        return new TreeSet();
    }

    private NavigableSetRandomGenerator() {
    }
}
