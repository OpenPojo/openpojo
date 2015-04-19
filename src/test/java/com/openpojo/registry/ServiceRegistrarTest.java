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

package com.openpojo.registry;

import java.util.HashSet;
import java.util.Set;

import com.openpojo.random.RandomGenerator;
import com.openpojo.random.service.RandomGeneratorService;
import com.openpojo.validation.affirm.Affirm;
import org.junit.Before;
import org.junit.Test;

public class ServiceRegistrarTest {

    private final String[] expectedDefaultTypeNames = new String[] {
            // @formatter:off
            "java.lang.Boolean"
            ,"java.lang.Byte"
            ,"java.lang.Character"
            ,"java.lang.Class"
            ,"java.lang.Double"
            ,"java.lang.Float"
            ,"java.lang.Integer"
            ,"java.lang.Long"
            ,"java.lang.Object"
            ,"java.lang.Short"

            ,"java.lang.String"
            ,"java.math.BigDecimal"
            ,"java.math.BigInteger"
            ,"java.sql.Timestamp"
            ,"java.util.AbstractCollection"
            ,"java.util.AbstractList"
            ,"java.util.AbstractMap"
            ,"java.util.AbstractSequentialList"
            ,"java.util.AbstractSet"
            ,"java.lang.Enum"

            ,"java.util.ArrayDeque" // jdk6 only
            ,"java.util.ArrayList"
            ,"java.util.Calendar"
            ,"java.util.Collection"
            ,"java.util.Date"
            ,"java.util.EnumSet"
            ,"java.util.HashMap"
            ,"java.util.HashSet"
            ,"java.util.Hashtable"
            ,"java.util.IdentityHashMap"

            ,"java.util.LinkedHashMap"
            ,"java.util.LinkedHashSet"
            ,"java.util.LinkedList"
            ,"java.util.List"
            ,"java.util.Collection"
            ,"java.util.Map"
            ,"java.util.NavigableSet" // jdk6 only
            ,"java.util.Queue"
            ,"java.util.Set"
            ,"java.util.SortedMap"
            ,"java.util.SortedSet"

            ,"java.util.TreeMap"
            ,"java.util.TreeSet"
            ,"java.util.WeakHashMap"
            ,"java.util.concurrent.ArrayBlockingQueue"
            ,"java.util.concurrent.ConcurrentMap"
            ,"java.util.concurrent.ConcurrentHashMap"
            ,"java.util.concurrent.ConcurrentLinkedQueue"
            ,"java.util.concurrent.DelayQueue"
            ,"java.util.concurrent.LinkedBlockingQueue"
            ,"java.util.PriorityQueue"
            ,"java.util.concurrent.PriorityBlockingQueue"
            ,"java.util.concurrent.SynchronousQueue"
    };
    // @formatter:on

    private int expectedTypes;

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

//        "java.util.AbstractMap"
//        "java.util.NavigableSet"
//        "java.util.AbstractList"
//        "java.util.SortedSet"
//        "java.util.AbstractSequentialList"
//        "java.util.AbstractSet"
//        "java.util.AbstractCollection"
//        "java.util.SortedMap"
        expectedTypes = expectedDefaultTypes.size() - 8;

    }

    @Test
    public void defaultRandomGeneratorServicePrePopulated() {
        // JDK 5 only supports 42 of the 44 possible types. (java.util.ArrayDeque does not exist in JDK5).
        String javaVersion = System.getProperty("java.version");
        if (javaVersion.startsWith("1.6") || javaVersion.startsWith("1.7") || javaVersion.startsWith("1.8")) {
            System.out.println("Found that many types: " + expectedDefaultTypes.size());
            for (Class<?> expectedEntry : expectedDefaultTypes) {
                boolean found = false;
                for (Class<?> foundEntry : randomGeneratorService.getRegisteredTypes()) {
                    if (expectedEntry == foundEntry) found = true;
                }
                if (!found) System.out.println("\"" + expectedEntry.getName() + "\"");
            }
            System.out.println("Registered and not in the expected list!!");
            for (Class<?> foundEntry : randomGeneratorService.getRegisteredTypes()) {
                boolean found = false;
                for (Class<?> expectedEntry : expectedDefaultTypes) {
                    if (expectedEntry == foundEntry) found = true;
                }
                if (!found) System.out.println("\"" + foundEntry.getName() + "\"");
            }
            Affirm.affirmEquals("Types added / removed?", expectedTypes, randomGeneratorService.getRegisteredTypes().size());
        } else {
            if (javaVersion.startsWith("1.5")) {
                Affirm.affirmEquals("Types added / removed?", expectedTypes - 1, // (java.util.ArrayDeque does not exist
                        // in JDK5),
                        randomGeneratorService.getRegisteredTypes().size());
            } else {
                Affirm.fail("Unknown java version found " + System.getProperty("java.version") + " please check the " +
                        "correct number of expected registered classes and register type here - (found " + randomGeneratorService
                        .getRegisteredTypes().size() + ")");
            }
        }
    }

    @Test
    public void RandomGeneratedValue() {
        final RandomGenerator defaultRandomGenerator = randomGeneratorService.getDefaultRandomGenerator();
        for (final Class<?> type : expectedDefaultTypes) {
            Affirm.affirmFalse(String.format("Error default random generator returned when expected a registered " + "type [%s]", type),
                    defaultRandomGenerator.equals(randomGeneratorService.getRandomGeneratorByType(type)));
        }
    }
}
