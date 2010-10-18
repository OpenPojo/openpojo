/**
 * Copyright (C) 2010 Osman Shoukry
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.openpojo.random;

import java.util.Arrays;
import java.util.Collection;

import junit.framework.Assert;

import org.junit.Test;

import com.openpojo.random.loop.Employee;
import com.openpojo.random.loop.RandomEmployee;

/**
 * @author oshoukry
 */
public class RandomFactoryTest {
    private final String randomString = (String) RandomFactory.getRandomValue(String.class);

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

        Assert.assertEquals("RandomGenerator registration failed", randomString, ((RegisteredDummy) RandomFactory
                .getRandomValue(RegisteredDummy.class)).getValue());
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
