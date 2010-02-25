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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BasicRandomGeneratorTest {
    private BasicRandomGenerator basicRandomGenerator;
    private static final int EXPECTED_BASIC_TYPES = 17;

    @Before
    public void setUp() throws Exception {
        basicRandomGenerator = new BasicRandomGenerator();
    }

    /**
     * Test random String.
     */
    @Test
    public void testString() {
        testDoGenerateForClass(String.class);
    }

    /**
     * Test random Character.
     */
    @Test
    public void testCharacter() {
        // Character.
        char primitiveChar = (Character) basicRandomGenerator.doGenerate(char.class);
        testDoGenerateForClass(Character.class);
    }

    /**
     * Test random Byte.
     */
    @Test
    public void testByte() {
        // Byte
        byte primitiveByte = (Byte) basicRandomGenerator.doGenerate(byte.class);
        testDoGenerateForClass(Byte.class);
    }

    /**
     * Test random Short.
     */
    public void testShort() {
        // Short
        short primitiveShort = (Short) basicRandomGenerator.doGenerate(short.class);
        testDoGenerateForClass(Short.class);
    }

    /**
     * Test random Long.
     */
    public void testLong() {
        // Long.
        long primitiveLong = (Long) basicRandomGenerator.doGenerate(long.class);
        testDoGenerateForClass(Long.class);
    }

    /**
     * Test random Double.
     */
    public void testDouble() {
        // Double.
        double primitiveDouble = (Double) basicRandomGenerator.doGenerate(double.class);
        testDoGenerateForClass(Double.class);
    }

    /**
     * Test random Float.
     */
    @Test
    public void testFloat() {
        // Float.
        float primitiveFloat = (Float) basicRandomGenerator.doGenerate(float.class);
        testDoGenerateForClass(Float.class);
    }

    /**
     * Test random Integer.
     */
    @Test
    public void testInteger() {
        // Integer.
        int primitiveInt = (Integer) basicRandomGenerator.doGenerate(int.class);
        testDoGenerateForClass(Integer.class);
    }

    /**
     * Test random Boolean.
     */
    @Test
    public void testBoolean() {
        // Boolean.
        boolean primitiveBoolean = (Boolean) basicRandomGenerator.doGenerate(boolean.class);
        testDoGenerateForClass(Boolean.class);
    }

    /**
     * Test unregisterd type.
     */
    @Test
    public void testUnregisteredType() {
        Assert.assertNull(basicRandomGenerator.doGenerate(this.getClass()));

    }

    /**
     * Utility method to assist with testing all non-Primitive classes.
     * 
     * @param type
     *            The class type to test.
     */
    private void testDoGenerateForClass(Class<? extends Object> type) {
        Assert.assertTrue(basicRandomGenerator.doGenerate(type).getClass() == type);
        Object object = type.cast(basicRandomGenerator.doGenerate(type));
        Assert.assertNotNull(object);

        Object anotherObject = basicRandomGenerator.doGenerate(type);
        if (object.equals(anotherObject)) { // Just incase they are the same
            anotherObject = basicRandomGenerator.doGenerate(type);
            // if Boolean, run for 10 times.
            if (object.equals(anotherObject) && type == Boolean.class) {
                for (int counter = 0; counter < 10; counter++) {
                    if (!object.equals(basicRandomGenerator.doGenerate(type))) {
                        return;
                    }
                }
            }
        }
        Assert.assertFalse(object.equals(anotherObject));
    }

    /**
     * Test method for {@link com.openpojo.random.impl.BasicRandomGenerator#getTypes()}.
     */
    @Test
    public final void testGetTypes() {
        Assert.assertEquals("New Types added/removed to BasicRandomGenerator?", EXPECTED_BASIC_TYPES,
                basicRandomGenerator.getTypes().size());
    }

}
