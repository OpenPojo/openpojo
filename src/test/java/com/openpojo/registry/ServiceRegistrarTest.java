/*
 * Copyright (c) 2010-2016 Osman Shoukry
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

  private String javaVersion = System.getProperty("java.version");
  private final String[] expectedDefaultTypeNames = new String[] {
      // @formatter:off
            "java.lang.Boolean"
            ,"java.lang.Byte"
            ,"java.lang.Character"
            ,"java.lang.Class"
            ,"java.lang.Double"
            ,"java.lang.Enum"
            ,"java.lang.Float"
            ,"java.lang.Integer"
            ,"java.lang.Long"
            ,"java.lang.Object"
            ,"java.lang.Short"
            ,"java.lang.String"

            ,"java.math.BigDecimal"
            ,"java.math.BigInteger"

            ,"java.sql.Timestamp"

            ,"java.time.ZonedDateTime" // jdk8
            ,"java.time.ZoneId" // jdk8

            ,"java.util.AbstractCollection"
            ,"java.util.AbstractList"
            ,"java.util.AbstractMap"
            ,"java.util.AbstractQueue"
            ,"java.util.AbstractSequentialList"
            ,"java.util.AbstractSet"
            ,"java.util.ArrayDeque" // jdk6
            ,"java.util.ArrayList"
            ,"java.util.Calendar"
            ,"java.util.Collection"
            ,"java.util.Date"
            ,"java.util.Deque"
            ,"java.util.EnumMap"
            ,"java.util.EnumSet"
            ,"java.util.HashMap"
            ,"java.util.HashSet"
            ,"java.util.Hashtable"
            ,"java.util.IdentityHashMap"
            ,"java.util.LinkedHashMap"
            ,"java.util.LinkedHashSet"
            ,"java.util.LinkedList"
            ,"java.util.List"
            ,"java.util.Map"
            ,"java.util.NavigableMap" // jdk6
            ,"java.util.NavigableSet" // jdk6
            ,"java.util.PriorityQueue"
            ,"java.util.Queue"
            ,"java.util.Set"
            ,"java.util.SortedMap"
            ,"java.util.Stack"
            ,"java.util.SortedSet"
            ,"java.util.TreeMap"
            ,"java.util.TreeSet"
            ,"java.net.URI"
            ,"java.net.URL"
            ,"java.util.UUID"
            ,"java.util.Vector"
            ,"java.util.WeakHashMap"
            ,"java.util.concurrent.ArrayBlockingQueue"
            ,"java.util.concurrent.BlockingQueue"
            ,"java.util.concurrent.BlockingDeque"
            ,"java.util.concurrent.ConcurrentHashMap"
            ,"java.util.concurrent.ConcurrentLinkedDeque"
            ,"java.util.concurrent.ConcurrentLinkedQueue"
            ,"java.util.concurrent.ConcurrentMap"
            ,"java.util.concurrent.ConcurrentSkipListSet"
            ,"java.util.concurrent.CopyOnWriteArrayList"
            ,"java.util.concurrent.CopyOnWriteArraySet"
            ,"java.util.concurrent.DelayQueue"
            ,"java.util.concurrent.LinkedBlockingDeque"
            ,"java.util.concurrent.LinkedBlockingQueue"
            ,"java.util.concurrent.LinkedTransferQueue"
            ,"java.util.concurrent.PriorityBlockingQueue"
            ,"java.util.concurrent.SynchronousQueue"
            ,"java.util.concurrent.TransferQueue"

            ,"javax.management.AttributeList"

            ,"javax.management.relation.RoleList"
            ,"javax.management.relation.RoleUnresolvedList"

            ,"javax.print.attribute.standard.JobStateReasons"
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

    if (javaVersion.startsWith("1.5")
        || javaVersion.startsWith("1.6")
        || javaVersion.startsWith("1.7")
        || javaVersion.startsWith("1.8"))
      expectedTypes = expectedDefaultTypes.size();
    else
      throw new UnsupportedOperationException("Unknown java version found " + javaVersion + " please check " +
          "the correct number of expected registered classes and register type here - (found " + randomGeneratorService
          .getRegisteredTypes().size() + ")");
  }

  @Test
  public void defaultRandomGeneratorServicePrePopulated() {
    reportDifferences();
    Affirm.affirmEquals("Types added / removed?", expectedTypes, randomGeneratorService.getRegisteredTypes().size());
  }

  private void reportDifferences() {
    System.out.println("Found that many types: " + expectedDefaultTypes.size() + ", expected: " + expectedTypes);
    System.out.println("List of Entries in the expected List but not in the registered list:");
    for (Class<?> expectedEntry : expectedDefaultTypes) {
      if (!randomGeneratorService.getRegisteredTypes().contains(expectedEntry))
        System.out.println("\"" + expectedEntry.getName() +
            "\"");
    }
    System.out.println("List of Registered types but not in the expected list:");
    for (Class<?> foundEntry : randomGeneratorService.getRegisteredTypes()) {
      if (!expectedDefaultTypes.contains(foundEntry))
        System.out.println("\"" + foundEntry.getName() + "\"");
    }
  }

  @Test
  public void RandomGeneratedValue() {
    final RandomGenerator defaultRandomGenerator = randomGeneratorService.getDefaultRandomGenerator();
    for (final Class<?> type : expectedDefaultTypes) {
      Affirm.affirmFalse(String.format("Error default random generator returned when expected a registered " + "type " +
          "[%s]", type), defaultRandomGenerator.equals(randomGeneratorService.getRandomGeneratorByType(type)));
    }
  }
}
