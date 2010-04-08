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

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import com.openpojo.random.RandomFactory;
import com.openpojo.random.RandomGenerator;

/**
 * This is the most basic random generator, it handles all basic java types (17 in total).<br>
 * <strong>Namely:</strong><br>
 * 1. boolean & Boolean<br>
 * 2. int & Integer <br>
 * 3. float & Float <br>
 * 4. double & Double <br>
 * 5. long & Long <br>
 * 6. short & Short <br>
 * 7. byte & Byte <br>
 * 8. char & Character <br>
 * 9. And String<br>
 * <br>
 * You can overwrite any of those types with your own generator and register it with the RandomFactory. <br>
 * <strong>Note:</strong><br>
 * 1. char & Character random generation is <i>currently</i> limited to the following set of
 * characters A-Z, a-z and 0-9. <br>
 * 2. String random generation relies on the Character random generation, all strings are generated as random sequence
 * of characters with length between 1 and 32 inclusive. So If you over-write the Character generator,
 * you will implicitly be modifying the types of character the random characters a random String is made of.
 *
 * @author oshoukry
 */
public final class BasicRandomGenerator implements RandomGenerator {
    private static final Random RANDOM = new Random(new Date().getTime());
    private static final int MAX_RANDOM_STRING_LENGTH = 32;

    private BasicRandomGenerator() {

    }

    public static RandomGenerator getInstance() {
        return Instance.INSTANCE;
    }

    private static final Class<?>[] TYPES = new Class<?>[]{ boolean.class, Boolean.class, int.class, Integer.class,
            float.class, Float.class, double.class, Double.class, long.class, Long.class, short.class, Short.class,
            byte.class, Byte.class, char.class, Character.class, String.class };

    private static final char[] CHARACTERS = new char[]{ 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
            'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1',
            '2', '3', '4', '5', '6', '7', '8', '9' };

    public Object doGenerate(final Class<?> type) {
        if (type == boolean.class || type == Boolean.class) {
            return RANDOM.nextBoolean();
        }

        if (type == int.class || type == Integer.class) {
            return RANDOM.nextInt();
        }

        if (type == float.class || type == Float.class) {
            return RANDOM.nextFloat();
        }

        if (type == double.class || type == Double.class) {
            return RANDOM.nextDouble();
        }

        if (type == long.class || type == Long.class) {
            return RANDOM.nextLong();
        }

        if (type == short.class || type == Short.class) {

            return (short) (RANDOM.nextInt(Short.MAX_VALUE + 1) * (RANDOM.nextBoolean() ? 1 : -1));
        }

        if (type == byte.class || type == Byte.class) {
            byte[] randombyte = new byte[1];
            RANDOM.nextBytes(randombyte);
            return randombyte[0];
        }

        if (type == char.class || type == Character.class) {
            return CHARACTERS[RANDOM.nextInt(CHARACTERS.length)];
        }

        if (type == String.class) {
            StringBuffer randomString = new StringBuffer(MAX_RANDOM_STRING_LENGTH);

            /* prevent zero length string lengths */
            for (int count = 0; count < RANDOM.nextInt(MAX_RANDOM_STRING_LENGTH + 1) + 1; count++) {
                randomString.append(RandomFactory.getRandomValue(Character.class));
            }
            return randomString.toString();
        }

        return null;
    }

    public Collection<Class<?>> getTypes() {
        return Arrays.asList(TYPES);
    }

    private static class Instance {
        private static final RandomGenerator INSTANCE = new BasicRandomGenerator();
    }
}
