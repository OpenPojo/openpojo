/*
 * Copyright (c) 2010-2014 Osman Shoukry
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

package com.openpojo.random.impl;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.openpojo.random.RandomGenerator;

public class ObjectRandomGeneratorTest {
    private RandomGenerator objectRandomGenerator;
    private final Class<?> objectClass = Object.class;
    private static final int EXPECTED_COUNT = 1;

    @Before
    public void setUp() throws Exception {
        objectRandomGenerator = ObjectRandomGenerator.getInstance();
    }

    @Test
    public void testGetInstance() {
        Assert.assertNotNull("Null object returned for ObjectRandomGenerator.getInstance()", objectRandomGenerator);
        Assert.assertTrue(String.format("Incorrect type returned=[%s] for requested type=[%s]", objectRandomGenerator
                .getClass(), ObjectRandomGenerator.class), objectRandomGenerator instanceof ObjectRandomGenerator);

    }

    @Test
    public void testDoGenerate() {
        CommonCode.testDoGenerateForClass(objectRandomGenerator, objectClass);
    }

    @Test
    public void testGetTypes() {
        CommonCode.testGetType(objectRandomGenerator, objectClass, EXPECTED_COUNT);
    }

}
