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

import java.sql.Timestamp;

import org.junit.Before;
import org.junit.Test;

import com.openpojo.random.RandomGenerator;

/**
 * @author oshoukry
 */
public class TimestampRandomGeneratorTest {
    private RandomGenerator timestampRandomGenerator;
    Class<?> timestampClass = Timestamp.class;
    private static final int EXPECTED_TYPES = 1;

    @Before
    public void setUp() {
        timestampRandomGenerator = TimestampRandomGenerator.getInstance();
    }

    /**
     * Test method for {@link com.openpojo.random.impl.TimestampRandomGenerator#doGenerate(java.lang.Class)}.
     */
    @Test
    public final void testDoGenerate() {
        CommonCode.testDoGenerateForClass(timestampRandomGenerator, timestampClass);
    }

    /**
     * Test method for {@link com.openpojo.random.impl.TimestampRandomGenerator#getTypes()}.
     */
    @Test
    public final void testGetTypes() {
        CommonCode.testGetType(timestampRandomGenerator, timestampClass, EXPECTED_TYPES);
    }

}
