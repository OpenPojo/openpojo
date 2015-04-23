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

import java.util.Arrays;
import java.util.Collection;
import javax.management.Attribute;
import javax.management.AttributeList;

import com.openpojo.random.RandomGenerator;
import com.openpojo.random.collection.util.CollectionHelper;
import com.openpojo.random.util.Helper;

/**
 * @author oshoukry
 */
public class AttributeListRandomGenerator implements RandomGenerator {
    private static final Class<?>[] TYPES = new Class<?>[] { AttributeList.class };
    private static final AttributeListRandomGenerator INSTANCE = new AttributeListRandomGenerator();

    public static AttributeListRandomGenerator getInstance() {
        return INSTANCE;
    }

    public Collection<Class<?>> getTypes() {
        return Arrays.asList(TYPES);
    }

    public Collection doGenerate(Class<?> type) {
        Helper.assertIsAssignableTo(type, getTypes());
        return CollectionHelper.buildCollections(new AttributeList(), Attribute.class);
    }

    private AttributeListRandomGenerator() {
    }
}
