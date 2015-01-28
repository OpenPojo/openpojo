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

package com.openpojo.random.collection.util;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.SynchronousQueue;

import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.impl.ParameterizableFactory;

/**
 * This Helper class populates the randomly generated collection with some random elements.<br>
 * It is configured to generate anywhere between 0 - 10 elements in the collection.
 *
 * @author oshoukry
 */
public class CollectionHelper {

    private static final Random RANDOM = new Random(new Date().getTime());
    private static final int MAX_RANDOM_ELEMENTS = 5;

    @SuppressWarnings("unchecked")
    public static void buildCollections(Collection collection, Type type) {
        if (type == null || collection == null) return;

        if (collection.getClass() == SynchronousQueue.class) return;

        if (DelayQueue.class.isAssignableFrom(collection.getClass()) && !(Delayed.class.isAssignableFrom((Class) type)))
            type = Delayed.class;

        int counter = RANDOM.nextInt(MAX_RANDOM_ELEMENTS) + 1;

        collection.clear();
        while (counter-- > 0) {
            Object nextEntry = RandomFactory.getRandomValue(ParameterizableFactory.getInstance(type));
            collection.add(nextEntry);
        }
    }

}