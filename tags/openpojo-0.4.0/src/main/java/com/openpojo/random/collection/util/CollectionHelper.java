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
package com.openpojo.random.collection.util;

import java.util.Collection;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.SynchronousQueue;

import com.openpojo.random.RandomFactory;

/**
 * This Helper class populates the randomly generated collection with some random elements.<br>
 * It is configured to generate anywhere between 0 - 10 elements in the collection.
 *
 * @author oshoukry
 */
public class CollectionHelper {

    private static final Random RANDOM = new Random(new Date().getTime());
    private static final int MAX_RANDOM_ELEMENTS = 10;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void populateWithRandomData(Collection collection) {
        for (int count = 0; count < RANDOM.nextInt(MAX_RANDOM_ELEMENTS + 1); count++) {
            if (collection.getClass() == SynchronousQueue.class) {
                // You can't add to a SynchronousQueue
                return;
            }
            if (collection.getClass() == DelayQueue.class) {
                collection.add(RandomFactory.getRandomValue(Delayed.class));
            } else {
                collection.add(RANDOM.nextInt());
            }
        }

    }
}
