/**
 * 2010 Copyright Osman Shoukry.
 */
package com.openpojo.random.impl;

import java.sql.Timestamp;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * @author oshoukry
 *
 */
public class TimestampRandomGeneratorTest {
    TimestampRandomGenerator timestampRandomGenerator;
    private static final int EXPECTED_TYPES = 1;


    @Before
    public void setUp() {
        timestampRandomGenerator = new TimestampRandomGenerator();
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
        Assert.assertEquals("New Types added/removed to TimestampRandomGenerator?", EXPECTED_TYPES, timestampRandomGenerator.getTypes().size());
    }

}
