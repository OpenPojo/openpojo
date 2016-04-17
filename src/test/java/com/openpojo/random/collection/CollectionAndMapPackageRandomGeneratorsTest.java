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

package com.openpojo.random.collection;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.openpojo.log.LoggerFactory;
import com.openpojo.log.utils.MessageFormatter;
import com.openpojo.random.RandomGenerator;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;
import com.openpojo.reflection.construct.InstanceFactory;
import com.openpojo.reflection.filters.FilterChain;
import com.openpojo.reflection.filters.FilterNestedClasses;
import com.openpojo.reflection.filters.FilterNonConcrete;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.affirm.Affirm;
import org.junit.BeforeClass;
import org.junit.Test;

public class CollectionAndMapPackageRandomGeneratorsTest {
  private static final List<PojoClass> collectionRandomGenerators = new LinkedList<PojoClass>();
  private static final String[] packages = new String[] { "com.openpojo.random.collection", "com.openpojo.random.map" };
  private static final int EXPECTED_COUNT = 54;

  @BeforeClass
  public static void setup() {
    for (final String pkg : packages) {
      collectionRandomGenerators.addAll(PojoClassFactory.getPojoClassesRecursively(pkg,
          new FilterChain(
              new RandomGeneratorFilter(),
              new FilterNestedClasses(),
              new FilterNonConcrete())));
    }
    Affirm.affirmEquals(MessageFormatter.format("Invalid number of Collection/Map RandomGenerators added/removed? " +
            "expected: " + "[{0}], found: [{1}] which were [{2}] ", EXPECTED_COUNT, collectionRandomGenerators.size(),
        collectionRandomGenerators), EXPECTED_COUNT, collectionRandomGenerators.size());
  }

  /**
   * This test will test every random generator against its declared types, for every type returned from getTypes.
   */
  @Test
  public void shouldReturnRandomInstanceForDeclaredType() {
    for (final PojoClass randomGeneratorPojoClass : collectionRandomGenerators) {
      final RandomGenerator randomGenerator = (RandomGenerator) InstanceFactory.getInstance(randomGeneratorPojoClass);
      final Collection<Class<?>> generatorTypes = randomGenerator.getTypes();
      for (final Class<?> type : generatorTypes) {
        LoggerFactory.getLogger(this.getClass()).debug("Generating Type [" + type + "]");
        Object firstInstance = randomGenerator.doGenerate(type);
        Affirm.affirmNotNull(MessageFormatter.format("[{0}] returned null for type [{1}]",
            randomGenerator.getClass(), type), firstInstance);

        Affirm.affirmTrue(MessageFormatter.format("[{0}] returned incompatible type [{1}] when requesting type [{2}]",
            randomGenerator.getClass(), firstInstance.getClass(), type), type.isAssignableFrom(firstInstance.getClass()));

        int counter = 10;
        Object secondInstance = null;
        while (counter > 0) {
          firstInstance = randomGenerator.doGenerate(type);
          secondInstance = randomGenerator.doGenerate(type);
          if (!firstInstance.equals(secondInstance)) {
            break;
          }
          counter--;
        }

        Affirm.affirmFalse(MessageFormatter.format("[{0}] returned identical instances for type [{1}]",
            randomGenerator.getClass(), type), firstInstance.equals(secondInstance));

      }
    }
  }

  private static class RandomGeneratorFilter implements PojoClassFilter {
    public boolean include(final PojoClass pojoClass) {
      return pojoClass.extendz(RandomGenerator.class);
    }
  }
}
