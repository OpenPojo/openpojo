package com.openpojo.registry;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.openpojo.random.RandomGenerator;
import com.openpojo.random.service.RandomGeneratorService;
import com.openpojo.validation.affirm.Affirm;

public class ServiceRegistrarTest {

    private final String[] expectedDefaultTypeNames = new String[] { "java.lang.Boolean", "java.lang.Byte",
            "java.lang.Character", "java.lang.Class", "java.lang.Double", "java.lang.Float", "java.lang.Integer",
            "java.lang.Long", "java.lang.Object", "java.lang.Short", "java.lang.String",
            "java.math.BigDecimal",
            "java.math.BigInteger",
            "java.sql.Timestamp",
            "java.util.AbstractCollection",
            "java.util.AbstractList",
            "java.util.AbstractMap",
            "java.util.AbstractSequentialList",
            "java.util.AbstractSet",
            "java.util.ArrayDeque", // jdk6 only
            "java.util.ArrayList", "java.util.Calendar", "java.util.Collection", "java.util.Date", "java.util.EnumSet",
            "java.util.HashMap", "java.util.HashSet", "java.util.Hashtable", "java.util.IdentityHashMap",
            "java.util.LinkedHashMap", "java.util.LinkedHashSet",
            "java.util.LinkedList",
            "java.util.List",
            "java.util.Map",
            "java.util.NavigableSet", // jdk6 only
            "java.util.Queue", "java.util.Set", "java.util.SortedMap", "java.util.SortedSet", "java.util.TreeMap",
            "java.util.TreeSet", "java.util.WeakHashMap", "java.util.concurrent.ConcurrentHashMap",
            "java.util.concurrent.DelayQueue", "java.util.concurrent.LinkedBlockingQueue",
            "java.util.concurrent.PriorityBlockingQueue", "java.util.concurrent.SynchronousQueue" };

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
                Affirm.fail("Unkown java version found " + System.getProperty("java.version")
                        + " please check the correct number of expected registered classes and register type here");
            }
        }
    }

    @Test
    public void RandomGeneratedValue() {
        final RandomGenerator defaultRandomGenerator = randomGeneratorService.getDefaultRandomGenerator();
        for (final Class<?> type : expectedDefaultTypes) {
            Affirm.affirmFalse(String.format("Error default random generatar returned when expected a registered type [%s]",
                                             type),
                               defaultRandomGenerator.equals(randomGeneratorService.getRandomGeneratorByType(type)));
        }
    }
}
