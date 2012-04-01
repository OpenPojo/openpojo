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

import java.util.Arrays;
import java.util.Collection;

import junit.framework.Assert;

import org.junit.Test;

import com.openpojo.random.exception.RandomGeneratorException;
import com.openpojo.random.loop.Employee;
import com.openpojo.random.loop.RandomEmployee;
import com.openpojo.random.sampleclasses.AClassWithNoRegisteredRandomGenerator;
import com.openpojo.random.sampleclasses.NoRandomGeneratorPerson;
import com.openpojo.random.sampleclasses.hierarchy.ClassExtendingClassImplementingSomeInterface;
import com.openpojo.random.sampleclasses.hierarchy.ClassImplementingSomeInterface;
import com.openpojo.random.sampleclasses.hierarchy.SomeInterface;
import com.openpojo.random.sampleclasses.hierarchy.SomeInterfaceRandomGenerator;
import com.openpojo.validation.affirm.Affirm;

/**
 * @author oshoukry
 */
public class RandomFactoryTest {
    private final String randomString = RandomFactory.getRandomValue(String.class);

    /**
     * Test method for {@link com.openpojo.random.RandomFactory#addRandomGenerator(com.openpojo.random.RandomGenerator)}
     * .
     */
    @Test
    public void testAddRandomGenerator() {
        RandomFactory.addRandomGenerator(new RandomGenerator() {

            public Object doGenerate(final Class<?> type) {
                return new RegisteredDummy(randomString);
            }

            public Collection<Class<?>> getTypes() {
                return Arrays.asList(new Class<?>[]{ RegisteredDummy.class });
            }

        });

        Assert.assertEquals("RandomGenerator registration failed", randomString, RandomFactory
            .getRandomValue(RegisteredDummy.class).getValue());
    }

    /**
     * Test that the RandomFactory detects and breaks cyclic dependencies.
     */
    @Test
    public void testRandomLoop() {
        RandomFactory.addRandomGenerator(new RandomEmployee());
        Assert.assertNotNull(RandomFactory.getRandomValue(Employee.class));
        Assert.assertNotNull(RandomFactory.getRandomValue(Employee.class));

    }

    @Test
    public void shouldDetectCyclicLoopForNonRegisteredRandomGenerator() {
        RandomFactory.getRandomValue(NoRandomGeneratorPerson.class);
    }

    @Test(expected = RandomGeneratorException.class)
    public void shouldFailAbstract() {
        RandomFactory.getRandomValue(com.openpojo.random.sampleclasses.AnAbstractClass.class);
    }

    @Test
    public void generateRandomWithNoRegisteredRandomGenerator() {
        final Class<?> clazz = AClassWithNoRegisteredRandomGenerator.class;

        final Object someInstance = RandomFactory.getRandomValue(clazz);

        Affirm.affirmNotNull(String.format("Null value returned for random instance of [%s]", clazz.getName()),
            someInstance);

        Affirm.affirmFalse(String.format("Non randomized instance returned (i.e. same object) for [%s]", clazz
            .getName()), someInstance.equals(RandomFactory.getRandomValue(clazz)));
    }

    @Test
    public void shouldRegisterHierarchyOfTypes() {
        RandomFactory.addRandomGenerator(SomeInterfaceRandomGenerator.getInstance());
        final Class<?> someInterface = SomeInterface.class;
        final Class<?> classImplementingSomeInterface = ClassImplementingSomeInterface.class;
        final Class<?> classExtendingClassImplmentingSomeInterface = ClassExtendingClassImplementingSomeInterface.class;
        Object instance = RandomFactory.getRandomValue(someInterface);

        Affirm.affirmNotNull(String.format("RandomFactory failed to retrieve random instance for interface [%s]", someInterface), instance);
        Affirm.affirmEquals("RandomFactory failed to lookup proper random generator from heirarchy",classExtendingClassImplmentingSomeInterface, instance.getClass());

        instance = RandomFactory.getRandomValue(classImplementingSomeInterface);
        Affirm.affirmEquals("RandomFactory failed to lookup proper random generator from heirarchy",classExtendingClassImplmentingSomeInterface, instance.getClass());
    }


    private class RegisteredDummy {
        private final String value;

        public RegisteredDummy(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
