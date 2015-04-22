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

package com.openpojo.random.collection;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.openpojo.log.LoggerFactory;
import org.junit.BeforeClass;
import org.junit.Test;

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

public class CollectionAndMapPackageRandomGeneratorsTest {
    private static final List<PojoClass> collectionRandomGenerators = new LinkedList<PojoClass>();
    private static final String[] packages = new String[] { "com.openpojo.random.collection", "com.openpojo.random.map" };
    private static final int EXPECTED_COUNT = 40;

    @BeforeClass
    public static void setup() {
        for (final String pkg : packages) {
            collectionRandomGenerators.addAll(PojoClassFactory.getPojoClassesRecursively(pkg,
                                                                                         new FilterChain(
                                                                                                         new RandomGeneratorFilter(),
                                                                                                         new FilterNestedClasses(),
                                                                                                         new FilterNonConcrete())));
        }
        Affirm.affirmEquals(MessageFormatter.format("Invalid number of Collection/Map RandomGenerators added/removed? expected: " +
                                "[{0}], found: [{1}] which were [{2}] ", EXPECTED_COUNT, collectionRandomGenerators.size(),
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
                final Object firstInstance = randomGenerator.doGenerate(type);
                Affirm.affirmNotNull(MessageFormatter.format("[{0}] returned null for type [{1}]",
                                                             randomGenerator.getClass(), type), firstInstance);

                Affirm.affirmTrue(MessageFormatter.format("[{0}] returned incompatible type [{1}] when requesting type [{2}]",
                                                          randomGenerator.getClass(), firstInstance.getClass(), type),
                                  type.isAssignableFrom(firstInstance.getClass()));

                int counter = 10;
                Object secondInstance = null;
                while (counter > 0) {
                    secondInstance = randomGenerator.doGenerate(type);
                    if (!firstInstance.equals(secondInstance)) {
                        break;
                    }
                    counter--;
                }
                Affirm.affirmFalse(MessageFormatter.format("[{0}] returned identical instances for type [{1}]",
                                                           randomGenerator.getClass(), type),
                                   firstInstance.equals(secondInstance));

            }
        }
    }

    private static class RandomGeneratorFilter implements PojoClassFilter {
        public boolean include(final PojoClass pojoClass) {
            return pojoClass.extendz(RandomGenerator.class);
        }
    }
}
