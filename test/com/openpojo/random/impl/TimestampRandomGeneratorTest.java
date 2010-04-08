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
package com.openpojo.random.impl;

import java.sql.Timestamp;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.openpojo.random.RandomGenerator;

/**
 * @author oshoukry
 */
public class TimestampRandomGeneratorTest {
    private RandomGenerator timestampRandomGenerator;
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
        Assert.assertEquals(timestampRandomGenerator.doGenerate(Timestamp.class).getClass(), Timestamp.class);

        Timestamp timestamp = (Timestamp) timestampRandomGenerator.doGenerate(Timestamp.class);
        Assert.assertNotNull(timestamp);
        Timestamp anotherTimestamp = (Timestamp) timestampRandomGenerator.doGenerate(Timestamp.class);
        if (timestamp.equals(anotherTimestamp)) { // Just incase they are equal
            anotherTimestamp = (Timestamp) timestampRandomGenerator.doGenerate(Timestamp.class);
        }
        Assert.assertFalse(timestamp.equals(anotherTimestamp));
    }

    /**
     * Test method for {@link com.openpojo.random.impl.TimestampRandomGenerator#getTypes()}.
     */
    @Test
    public final void testGetTypes() {
        Assert.assertEquals("New Types added/removed to TimestampRandomGenerator?", EXPECTED_TYPES,
                timestampRandomGenerator.getTypes().size());
    }

}
