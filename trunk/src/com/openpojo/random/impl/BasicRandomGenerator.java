package com.openpojo.random.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import com.openpojo.random.RandomFactory;
import com.openpojo.random.RandomGenerator;

/**
 * This is the most basic random generator, it handles all basic java types.
 * You can overwrite any of those types with your own generator and register it with
 * the RandomFactory.
 * 
 * @author oshoukry
 */
public final class BasicRandomGenerator implements RandomGenerator {
    private static final Random RANDOM = new Random(new Date().getTime());
    private static final int MAX_RANDOM_STRING_LENGTH = 32;

    private static final Class<?>[] TYPES = new Class<?>[] { boolean.class, Boolean.class, int.class, Integer.class,
            float.class, Float.class, double.class, Double.class, long.class, Long.class, short.class, Short.class,
            byte.class, Byte.class, char.class, Character.class, String.class };

    private static final char[] CHARACTERS = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
            'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1',
            '2', '3', '4', '5', '6', '7', '8', '9' };

    public Object doGenerate(Class<?> type) {
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
            for (int count = 0;
                    /* prevent zero length string lengths */
                    count < RANDOM.nextInt(MAX_RANDOM_STRING_LENGTH + 1) + 1;
                    count++) {
                randomString.append((Character) RandomFactory.getRandomValue(Character.class));
            }
            return randomString.toString();
        }

        return null;
    }

    public Collection<Class<?>> getTypes() {
        return Arrays.asList(TYPES);
    }
}
