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

import java.util.Collection;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

import com.openpojo.random.ParameterizableRandomGenerator;
import com.openpojo.random.RandomFactory;
import com.openpojo.random.RandomGenerator;
import com.openpojo.random.collection.util.CollectionHelper;
import com.openpojo.random.util.SerializableComparableObject;
import com.openpojo.reflection.Parameterizable;
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
 * 6. ArrayBlockingQueue <br>
 *
 * @author oshoukry
 */
public final class QueueConcreteRandomGenerator implements ParameterizableRandomGenerator {

    private QueueConcreteRandomGenerator() {
    }

    public static RandomGenerator getInstance() {
        return Instance.INSTANCE;
    }

    private final String[] TYPES = new String[] {
            "java.util.ArrayDeque"
            ,"java.util.Queue"
            ,"java.util.concurrent.ArrayBlockingQueue"
            ,"java.util.concurrent.LinkedBlockingQueue"
            ,"java.util.concurrent.PriorityBlockingQueue"
            ,"java.util.concurrent.DelayQueue"
            ,"java.util.concurrent.SynchronousQueue"
            };


    @SuppressWarnings("rawtypes")
    public Object doGenerate(final Class<?> type) {
        Queue randomQueue;
        if (type == ArrayBlockingQueue.class || type == Queue.class)
            randomQueue = new ArrayBlockingQueue(20);
        else
            randomQueue = (Queue) InstanceFactory.getLeastCompleteInstance(PojoClassFactory.getPojoClass(type));
        CollectionHelper.buildCollections(randomQueue, SerializableComparableObject.class);

        return randomQueue;
    }

    public Object doGenerate(Parameterizable parameterizedType) {
        Queue returnedQueue = (Queue) RandomFactory.getRandomValue(parameterizedType.getType());
        returnedQueue.clear();
        CollectionHelper.buildCollections(returnedQueue, parameterizedType.getParameterTypes().get(0));
        return returnedQueue;
    }

    public Collection<Class<?>> getTypes() {
        Set<Class<?>> types = new HashSet<Class<?>>();

        for (String type : TYPES) {
            try {
                types.add(Class.forName(type));
            } catch (ClassNotFoundException e) {
                System.out.println("Warning: Failed to load [" + type + "] got Exception[" + e + "]");
            }
        }
        return types;
    }

    private static class Instance {
        private static final RandomGenerator INSTANCE = new QueueConcreteRandomGenerator();
    }
}
