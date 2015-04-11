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

package com.openpojo.random.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.openpojo.random.collection.util.AbstractCollectionRandomGenerator;
import com.openpojo.random.collection.util.CollectionHelper;
import com.openpojo.random.util.SerializableComparableObject;

/**
 * @author oshoukry
 */
public class CollectionRandomGenerator extends AbstractCollectionRandomGenerator {
    private final Class<?>[] TYPES = new Class<?>[] { Collection.class };

    public static CollectionRandomGenerator getInstance() {
        return Instance.INSTANCE;
    }

    public Object doGenerate(Class<?> type) {
        super.doGenerate(type);

        return CollectionHelper.buildCollections(new ArrayList(), SerializableComparableObject.class);
    }

    public Collection<Class<?>> getTypes() {
        return Arrays.asList(TYPES);
    }

    private CollectionRandomGenerator() {
    }

    private static class Instance {
        public static final CollectionRandomGenerator INSTANCE = new CollectionRandomGenerator();
    }
}
