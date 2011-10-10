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
package com.openpojo.random;

import java.util.HashMap;
import java.util.Map;

import com.openpojo.log.Logger;
import com.openpojo.log.LoggerFactory;
import com.openpojo.random.collection.CollectionRandomGenerator;
import com.openpojo.random.collection.list.AbstractListRandomGenerator;
import com.openpojo.random.collection.list.AbstractSequentialListRandomGenerator;
import com.openpojo.random.collection.list.ListConcreteRandomGenerator;
import com.openpojo.random.collection.list.ListRandomGenerator;
import com.openpojo.random.collection.queue.QueueConcreteRandomGenerator;
import com.openpojo.random.collection.queue.QueueRandomGenerator;
import com.openpojo.random.collection.set.NavigableSetRandomGenerator;
import com.openpojo.random.collection.set.SetConcreteRandomGenerator;
import com.openpojo.random.collection.set.SetRandomGenerator;
import com.openpojo.random.collection.set.SortedSetRandomGenerator;
import com.openpojo.random.dynamic.EnumRandomGenerator;
import com.openpojo.random.dynamic.RandomInstanceFromInterfaceRandomGenerator;
import com.openpojo.random.exception.RandomGeneratorException;
import com.openpojo.random.impl.BasicRandomGenerator;
import com.openpojo.random.impl.ClassRandomGenerator;
import com.openpojo.random.impl.EnumSetRandomGenerator;
import com.openpojo.random.impl.ObjectRandomGenerator;
import com.openpojo.random.impl.TimestampRandomGenerator;
import com.openpojo.random.impl.VoidRandomGenerator;
import com.openpojo.random.map.AbstractMapRandomGenerator;
import com.openpojo.random.map.MapConcreteRandomGenerator;
import com.openpojo.random.map.MapRandomGenerator;
import com.openpojo.random.map.SortedMapRandomGenerator;
import com.openpojo.random.thread.GeneratedRandomValues;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.construct.InstanceFactory;
import com.openpojo.reflection.impl.PojoClassFactory;

/**
 * This factory is responsible for generating the random values using the registered RandomGenerator(s). <br>
 * This Factory will automatically detect cyclic dependency and return null for the second time around.<br>
 * <br>
 * <i>Simple Example</i><br>
 * If you have an Employee class that has the following constructor:
 *
 * <pre>
 * {@code
 * public Employee(final String fullName, final Employee manager) {
 *   ...
 * }
 * }
 * </pre>
 *
 * And you created the random generator as follows:
 *
 * <pre>
 * {@code
 * public Object doGenerate(Class<?> type) {
 *     return new Employee(RandomFactory.getRandomValue(String.class),
 *                          (Employee) RandomFactory.getRandomValue(Employee.class));
 * }
 * }
 * </pre>
 *
 * This would potentially cause a stack over-flow since there is a cyclic dependency of Employee on itself. So to
 * prevent stack over-flow (which would occur by trying to create a manager for every manager), this Factory has built
 * in protection (using {@link GeneratedRandomValues}) to prevent such a thing by recording for a current recursive call
 * if it's seen this type before, if so, it will return null the second time around.
 *
 * @author oshoukry
 */
public class RandomFactory {
    private static final Logger logger = LoggerFactory.getLogger(RandomFactory.class);

    private static final Map<Class<?>, RandomGenerator> generators = new HashMap<Class<?>, RandomGenerator>();

    static {
        // register defaults with Factory.
        RandomFactory.addRandomGenerator(VoidRandomGenerator.getInstance());
        RandomFactory.addRandomGenerator(ObjectRandomGenerator.getInstance());
        RandomFactory.addRandomGenerator(BasicRandomGenerator.getInstance());
        RandomFactory.addRandomGenerator(TimestampRandomGenerator.getInstance());
        RandomFactory.addRandomGenerator(ClassRandomGenerator.getInstance());
        RandomFactory.addRandomGenerator(EnumSetRandomGenerator.getInstance());

        // Collection
        RandomFactory.addRandomGenerator(CollectionRandomGenerator.getInstance());

        // Lists
        RandomFactory.addRandomGenerator(ListRandomGenerator.getInstance());
        RandomFactory.addRandomGenerator(ListConcreteRandomGenerator.getInstance());
        RandomFactory.addRandomGenerator(AbstractSequentialListRandomGenerator.getInstance());
        RandomFactory.addRandomGenerator(AbstractListRandomGenerator.getInstance());

        // Sets
        RandomFactory.addRandomGenerator(SetRandomGenerator.getInstance());
        RandomFactory.addRandomGenerator(SetConcreteRandomGenerator.getInstance());
        RandomFactory.addRandomGenerator(SortedSetRandomGenerator.getInstance());
        RandomFactory.addRandomGenerator(NavigableSetRandomGenerator.getInstance());

        // Queue
        RandomFactory.addRandomGenerator(QueueRandomGenerator.getInstance());
        RandomFactory.addRandomGenerator(QueueConcreteRandomGenerator.getInstance());

        // Map
        RandomFactory.addRandomGenerator(MapRandomGenerator.getInstance());
        RandomFactory.addRandomGenerator(SortedMapRandomGenerator.getInstance());
        RandomFactory.addRandomGenerator(MapConcreteRandomGenerator.getInstance());
        RandomFactory.addRandomGenerator(AbstractMapRandomGenerator.getInstance());
    }

    /**
     * Add a random generator to the list of available generators. The latest random generator registered wins.
     *
     * @param generator
     *            The generator to add.
     */
    public static synchronized void addRandomGenerator(final RandomGenerator generator) {
        for (Class<?> type : generator.getTypes()) {
            RandomFactory.generators.put(type, generator);
        }
    }

    /**
     * This method generates a random value of the requested type.<br>
     * If the requested type isn't registerd in the factory, an RandomGeneratorException will be thrown.
     *
     * @param type
     *            The type to get a random value of.
     * @return Randomly created value.
     */
    public static final Object getRandomValue(final Class<?> type) {
        if (GeneratedRandomValues.contains(type)) {
            logger.warn("Cyclic dependency on random generator for type=[{0}] detected, returning null", type);
            return null; // seen before, break loop.
        }

        RandomGenerator randomGenerator = RandomFactory.generators.get(type);
        if (randomGenerator == null) {
            PojoClass typeClass = PojoClassFactory.getPojoClass(type);

            if (typeClass.isInterface()) {
                return RandomInstanceFromInterfaceRandomGenerator.getInstance().doGenerate(type);
            }

            if (typeClass.isEnum()) {
                return EnumRandomGenerator.getInstance().doGenerate(type);
            }

            if (typeClass.isAbstract()) {
                throw RandomGeneratorException
                        .getInstance(String
                                .format("Unable to generate random instance for Abstract class [%s], please register a RandomGenerator and try again",
                                        typeClass));
            }

            logger.info("Creating random instance for type=[{0}] since no random generator registered", type);
            return InstanceFactory.getLeastCompleteInstance(PojoClassFactory.getPojoClass(type));
        }
        GeneratedRandomValues.add(type);
        Object randomValue;
        randomValue = randomGenerator.doGenerate(type);
        GeneratedRandomValues.remove(type);

        return randomValue;
    }
}
