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

package com.openpojo.random.collection.queue;

import java.util.Arrays;
import java.util.Collection;
import java.util.PriorityQueue;

import com.openpojo.random.collection.util.AbstractCollectionRandomGenerator;
import com.openpojo.random.collection.util.CollectionHelper;
import com.openpojo.random.util.SerializableComparableObject;

/**
 * @author oshoukry
 */
public class PriorityQueueRandomGenerator extends AbstractCollectionRandomGenerator {
    private final Class<?>[] TYPES = new Class<?>[] { PriorityQueue.class };

    public static PriorityQueueRandomGenerator getInstance() {
        return Instance.INSTANCE;
    }

    public Object doGenerate(Class<?> type) {
        super.doGenerate(type);

        return CollectionHelper.buildCollections(new PriorityQueue(), SerializableComparableObject.class);
    }

    public Collection<Class<?>> getTypes() {
        return Arrays.asList(TYPES);
    }

    private PriorityQueueRandomGenerator() {
    }

    private static class Instance {
        public static final PriorityQueueRandomGenerator INSTANCE = new PriorityQueueRandomGenerator();
    }
}
