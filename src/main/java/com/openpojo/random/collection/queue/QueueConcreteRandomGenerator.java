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
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.SynchronousQueue;

import com.openpojo.log.Logger;
import com.openpojo.log.LoggerFactory;
import com.openpojo.random.ParameterizableRandomGenerator;
import com.openpojo.random.RandomGenerator;
import com.openpojo.random.collection.util.CollectionHelper;
import com.openpojo.random.util.SerializableComparableObject;
import com.openpojo.reflection.Parameterizable;
import com.openpojo.reflection.construct.InstanceFactory;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.reflection.java.load.ClassUtil;

/**
 * This is random generator is responsible for generating concrete Queue implementations <br>
 * <strong>Namely:</strong><br>
 * 1. ArrayDeque
 * 2. Queue -- Interface
 * 3. ArrayBlockingQueue
 * 4. ConcurrentLinkedQueue
 * 5. DelayQueue
 * 6. LinkedBlockingQueue
 * 7. PriorityBlockingQueue
 * 8. SynchronousQueue
 *
 * @author oshoukry
 */
public final class QueueConcreteRandomGenerator implements ParameterizableRandomGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueueConcreteRandomGenerator.class);

    private QueueConcreteRandomGenerator() {
    }

    public static RandomGenerator getInstance() {
        return Instance.INSTANCE;
    }

    private final String[] TYPES = new String[] {
            "java.util.ArrayDeque"
            ,"java.util.Queue"
            ,"java.util.concurrent.ArrayBlockingQueue"
            ,"java.util.concurrent.ConcurrentLinkedQueue"
            ,"java.util.concurrent.DelayQueue"
            ,"java.util.concurrent.LinkedBlockingQueue"
            ,"java.util.concurrent.PriorityBlockingQueue"
            ,"java.util.concurrent.SynchronousQueue"
    };


    @SuppressWarnings("rawtypes")
    public Object doGenerate(final Class<?> type) {
        Queue randomQueue;

        Class<?> typeToGenerate = type;
        if (typeToGenerate.isInterface())
            typeToGenerate = CollectionHelper.getConstructableType(type, getTypes());

        LOGGER.debug("Generating [{0}] for requested type [{1}]", typeToGenerate, type);

        if (typeToGenerate == SynchronousQueue.class)
            return new SynchronousQueue();

        if (typeToGenerate == ArrayBlockingQueue.class)
            randomQueue = new ArrayBlockingQueue(20);
        else
            randomQueue = (Queue) InstanceFactory.getLeastCompleteInstance(PojoClassFactory.getPojoClass(typeToGenerate));

        if (typeToGenerate == DelayQueue.class)
            CollectionHelper.buildCollections(randomQueue, Delayed.class);
        else
            CollectionHelper.buildCollections(randomQueue, SerializableComparableObject.class);

        return randomQueue;
    }

    public Object doGenerate(Parameterizable parameterizedType) {
        return CollectionHelper.buildCollections((Collection) doGenerate(parameterizedType.getType()), parameterizedType.getParameterTypes()
                .get(0));
    }

    public Collection<Class<?>> getTypes() {
        Set<Class<?>> types = new HashSet<Class<?>>();

        for (String type : TYPES) {
            Class<?> clazz = ClassUtil.loadClass(type);
            if (clazz != null) {
                types.add(clazz);
            } else {
                LOGGER.info("Class [" + type + "] can't be loaded, skipping!");
            }
        }
        return types;
    }

    private static class Instance {
        private static final RandomGenerator INSTANCE = new QueueConcreteRandomGenerator();
    }
}
