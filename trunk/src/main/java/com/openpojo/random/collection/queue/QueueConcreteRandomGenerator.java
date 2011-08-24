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
package com.openpojo.random.collection.queue;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;

import com.openpojo.random.RandomGenerator;
import com.openpojo.random.collection.util.CollectionHelper;
import com.openpojo.reflection.construct.InstanceFactory;
import com.openpojo.reflection.impl.PojoClassFactory;

/**
 * This is random generator is responsible for generating concrete Queue implementations <br>
 * <strong>Namely:</strong><br>
 * 1. BlockingQueue -- Interface <br>
 * 2. LinkedBlockingQueue <br>
 * 3. PriorityBlockingQueue <br>
 * 4. DelayQueue <br>
 * 5. SynchronousQueue <br>
 * 6. TBD ArrayBlockingQueue <br>
 *
 * @author oshoukry
 */
public final class QueueConcreteRandomGenerator implements RandomGenerator {

    private QueueConcreteRandomGenerator() {
    }

    public static RandomGenerator getInstance() {
        return Instance.INSTANCE;
    }

    private final Class<?>[] TYPES = new Class<?>[] { ArrayDeque.class, LinkedBlockingQueue.class,
             PriorityBlockingQueue.class, DelayQueue.class, SynchronousQueue.class /**, ArrayBlockingQueue.class*/};

    @SuppressWarnings("rawtypes")
    public Object doGenerate(final Class<?> type) {
        Queue randomQueue = null;

        if (this.getTypes().contains(type)) {
            randomQueue = (Queue) InstanceFactory.getLeastCompleteInstance(PojoClassFactory.getPojoClass(type));
            CollectionHelper.populateWithRandomData(randomQueue);
        }

        return randomQueue;
    }

    public Collection<Class<?>> getTypes() {
        return Arrays.asList(TYPES);
    }

    private static class Instance {
        private static final RandomGenerator INSTANCE = new QueueConcreteRandomGenerator();
    }
}
