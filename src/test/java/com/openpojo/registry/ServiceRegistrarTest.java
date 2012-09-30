/*
 * Copyright (c) 2010-2012 Osman Shoukry
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation, either version 3 of the License or any
 *   later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.openpojo.registry;

import com.openpojo.random.RandomGenerator;
import com.openpojo.random.service.RandomGeneratorService;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.adapt.PojoClassAdaptor;
import com.openpojo.reflection.adapt.service.PojoClassAdaptationService;
import com.openpojo.validation.affirm.Affirm;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class ServiceRegistrarTest {

    private final String[] expectedDefaultTypeNames = new String[]{"java.lang.Boolean", "java.lang.Byte",
            "java.lang.Character", "java.lang.Class", "java.lang.Double", "java.lang.Float", "java.lang.Integer",
            "java.lang.Long", "java.lang.Object", "java.lang.Short", "java.lang.String", "java.math.BigDecimal",
            "java.math.BigInteger", "java.sql.Timestamp", "java.util.AbstractCollection", "java.util.AbstractList",
            "java.util.AbstractMap", "java.util.AbstractSequentialList", "java.util.AbstractSet",
            "java.util.ArrayDeque", // jdk6 only
            "java.util.ArrayList", "java.util.Calendar", "java.util.Collection", "java.util.Date",
            "java.util.EnumSet", "java.util.HashMap", "java.util.HashSet", "java.util.Hashtable",
            "java.util.IdentityHashMap", "java.util.LinkedHashMap", "java.util.LinkedHashSet",
            "java.util.LinkedList", "java.util.List", "java.util.Map", "java.util.NavigableSet", // jdk6 only
            "java.util.Queue", "java.util.Set", "java.util.SortedMap", "java.util.SortedSet", "java.util.TreeMap",
            "java.util.TreeSet", "java.util.WeakHashMap", "java.util.concurrent.ConcurrentHashMap",
            "java.util.concurrent.DelayQueue", "java.util.concurrent.LinkedBlockingQueue",
            "java.util.concurrent.PriorityBlockingQueue", "java.util.concurrent.SynchronousQueue"};

    private final int expectedTypes = 43;

    private Set<Class<?>> expectedDefaultTypes;
    private RandomGeneratorService randomGeneratorService;

    @Before
    public void setup() {
        expectedDefaultTypes = new HashSet<Class<?>>();

        // Add the primitives
        expectedDefaultTypes.add(boolean.class);
        expectedDefaultTypes.add(byte.class);
        expectedDefaultTypes.add(char.class);
        expectedDefaultTypes.add(double.class);
        expectedDefaultTypes.add(float.class);
        expectedDefaultTypes.add(int.class);
        expectedDefaultTypes.add(long.class);
        expectedDefaultTypes.add(short.class);
        expectedDefaultTypes.add(void.class);

        for (final String type : expectedDefaultTypeNames) {
            try {
                expectedDefaultTypes.add(Class.forName(type));
            } catch (final ClassNotFoundException e) {
                System.out.println("Failed for: " + e.getMessage());
            }
        }

        ServiceRegistrar.getInstance().initializeRandomGeneratorService();
        randomGeneratorService = ServiceRegistrar.getInstance().getRandomGeneratorService();
    }

    @Test
    public void defaultRandomGeneratorServicePrePopulated() {
        // JDK 5 only supports 42 of the 43 possible types. (java.util.ArrayDeque does not exist in JDK5).
        if (System.getProperty("java.version").startsWith("1.6")) {
            Affirm.affirmEquals("Types added / removed?", expectedTypes, randomGeneratorService.getRegisteredTypes()
                    .size());
        } else {
            if (System.getProperty("java.version").startsWith("1.5")) {
                Affirm.affirmEquals("Types added / removed?", expectedTypes - 1, // (java.util.ArrayDeque does not exist
                        // in JDK5),
                        randomGeneratorService.getRegisteredTypes().size());
            } else {
                Affirm.fail("Unkown java version found " + System.getProperty("java.version") + " please check the " +
                        "correct number of expected registered classes and register type here");
            }
        }
    }

    @Test
    public void RandomGeneratedValue() {
        final RandomGenerator defaultRandomGenerator = randomGeneratorService.getDefaultRandomGenerator();
        for (final Class<?> type : expectedDefaultTypes) {
            Affirm.affirmFalse(String.format("Error default random generatar returned when expected a registered type" +
                    " [%s]", type), defaultRandomGenerator.equals(randomGeneratorService.getRandomGeneratorByType(type)));
        }
    }

    @Test
    public void shouldHaveDefaultPojoClassAdaptationService() {
        Affirm.affirmNotNull("No default registered PojoClassAdaptationService?", ServiceRegistrar.getInstance().getPojoClassAdaptationService());
        Affirm.affirmEquals("Non DefaultPojoClassAdaptationService registered",
                "com.openpojo.reflection.adapt.service.impl.DefaultPojoClassAdaptationService",
                ServiceRegistrar.getInstance().getPojoClassAdaptationService().getClass().getName());
    }

    @Test
    public void shouldSetPojoClassAdaptationService() {
        PojoClassAdaptationServiceMock pojoClassAdaptationServiceMock = new PojoClassAdaptationServiceMock();
        ServiceRegistrar.getInstance().setPojoClassAdaptationService(pojoClassAdaptationServiceMock);
        Affirm.affirmEquals("Failed to setPojoClassAdaptationService", pojoClassAdaptationServiceMock,
                ServiceRegistrar.getInstance().getPojoClassAdaptationService());
    }

    private class PojoClassAdaptationServiceMock implements PojoClassAdaptationService {

        @Override
        public void registerPojoClassAdaptor(PojoClassAdaptor pojoClassAdaptor) {
            throw new RuntimeException("UnImplemented");
        }

        @Override
        public void unRegisterPojoClassAdaptor(PojoClassAdaptor pojoClassAdaptor) {
            throw new RuntimeException("UnImplemented");
        }

        @Override
        public PojoClass adapt(PojoClass pojoClass) {
            throw new RuntimeException("UnImplemented");
        }

    }
}
