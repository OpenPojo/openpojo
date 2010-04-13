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

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.openpojo.random.RandomGenerator;
import com.openpojo.validation.affirm.Affirm;

public class BasicRandomGeneratorTest {
    private RandomGenerator basicRandomGenerator;
    private static final int EXPECTED_BASIC_TYPES = 18;

    @Before
    public void setUp() throws Exception {
        basicRandomGenerator = BasicRandomGenerator.getInstance();
    }

    /**
     * Test random String.
     */
    @Test
    public void testString() {
        testDoGenerateForClass(String.class);
    }

    /**
     * Test random Date.
     */
    @Test
    public void testDate() {
        testDoGenerateForClass(Date.class);
    }

    /**
     * Test random Character.
     */
    @Test
    public void testCharacter() {
        // Character.

        @SuppressWarnings("unused")
        char primitiveChar = (Character) basicRandomGenerator.doGenerate(char.class);
        testDoGenerateForClass(Character.class);
    }

    /**
     * Test random Byte.
     */
    @Test
    public void testByte() {
        // Byte
        @SuppressWarnings("unused")
        byte primitiveByte = (Byte) basicRandomGenerator.doGenerate(byte.class);
        testDoGenerateForClass(Byte.class);
    }

    /**
     * Test random Short.
     */
    @Test
    public void testShort() {
        // Short
        @SuppressWarnings("unused")
        short primitiveShort = (Short) basicRandomGenerator.doGenerate(short.class);
        testDoGenerateForClass(Short.class);
    }

    /**
     * Test random Long.
     */
    @Test
    public void testLong() {
        // Long.
        @SuppressWarnings("unused")
        long primitiveLong = (Long) basicRandomGenerator.doGenerate(long.class);
        testDoGenerateForClass(Long.class);
    }

    /**
     * Test random Double.
     */
    @Test
    public void testDouble() {
        // Double.
        @SuppressWarnings("unused")
        double primitiveDouble = (Double) basicRandomGenerator.doGenerate(double.class);
        testDoGenerateForClass(Double.class);
    }

    /**
     * Test random Float.
     */
    @Test
    public void testFloat() {
        // Float.
        @SuppressWarnings("unused")
        float primitiveFloat = (Float) basicRandomGenerator.doGenerate(float.class);
        testDoGenerateForClass(Float.class);
    }

    /**
     * Test random Integer.
     */
    @Test
    public void testInteger() {
        // Integer.
        @SuppressWarnings("unused")
        int primitiveInt = (Integer) basicRandomGenerator.doGenerate(int.class);
        testDoGenerateForClass(Integer.class);
    }

    /**
     * Test random Boolean.
     */
    @Test
    public void testBoolean() {
        // Boolean.
        @SuppressWarnings("unused")
        boolean primitiveBoolean = (Boolean) basicRandomGenerator.doGenerate(boolean.class);
        testDoGenerateForClass(Boolean.class);
    }


    /**
     * Test unregisterd type.
     */
    @Test
    public void testUnregisteredType() {
        Affirm.affirmNull(String.format("Request to non-registered type [%s] must return null!!", this.getClass()),
                basicRandomGenerator.doGenerate(this.getClass()));

    }

    /**
     * Utility method to assist with testing all non-Primitive classes.
     *
     * @param type
     *            The class type to test.
     */
    private void testDoGenerateForClass(final Class<? extends Object> type) {
        Affirm.affirmTrue(String.format("Failed to get the requested type=[%s] from BasicRandomGenerator", type),
                basicRandomGenerator.doGenerate(type).getClass() == type);
        Object object = type.cast(basicRandomGenerator.doGenerate(type));
        Affirm.affirmNotNull(String.format("Request to registered type [%s] must return non-null value!!", type),
                object);

        Object anotherObject = basicRandomGenerator.doGenerate(type);
        if (object.equals(anotherObject)) { // Just incase they are the same
            anotherObject = basicRandomGenerator.doGenerate(type);
            // if Boolean, try for 20 times, since there is a 50% chance we land on the same value.
            if (object.equals(anotherObject) && type == Boolean.class) {
                for (int counter = 0; counter < 20; counter++) {
                    anotherObject = basicRandomGenerator.doGenerate(type);
                    if (!object.equals(anotherObject)) {
                        break;
                    }
                }
            }
        }
        Affirm.affirmFalse(String.format("BasicRandomGenerator generating the same values for type=[%s]", type), object
                .equals(anotherObject));
    }

    /**
     * Test method for {@link com.openpojo.random.impl.BasicRandomGenerator#getTypes()}.
     */
    @Test
    public final void testGetTypes() {
        Affirm.affirmEquals("New Types added/removed to BasicRandomGenerator?", EXPECTED_BASIC_TYPES,
                basicRandomGenerator.getTypes().size());
    }

}
