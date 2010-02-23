/**
 * 2010 Copyright Osman Shoukry.
 */
package com.openpojo.random;

import java.util.Arrays;
import java.util.Collection;

import junit.framework.Assert;

import org.junit.Test;

/**
 * @author oshoukry
 * 
 */
public class RandomFactoryTest {
    private final String randomString = (String) RandomFactory.getRandomValue(String.class);

    private static final Class<?>[] BASIC_TYPES = new Class<?>[] { boolean.class, Boolean.class, int.class,
            Integer.class, float.class, Float.class, double.class, Double.class, long.class, Long.class, short.class,
            Short.class, byte.class, Byte.class, char.class, Character.class, String.class };

    /**
     * Test method for {@link com.openpojo.random.RandomFactory#addRandomGenerator(com.openpojo.random.RandomGenerator)}
     * .
     */
    @Test
    public final void testAddRandomGenerator() {
        RandomFactory.addRandomGenerator(new RandomGenerator() {

            @Override
            public Object doGenerate(Class<?> type) {
                return new RegisteredDummy(randomString);
            }

            @Override
            public Collection<Class<?>> getTypes() {
                return Arrays.asList(new Class<?>[] { RegisteredDummy.class });
            }

        });

        Assert.assertEquals("RandomGenerator registration failed", randomString, ((RegisteredDummy) RandomFactory
                .getRandomValue(RegisteredDummy.class)).getValue());
    }

    /**
     * Test method for {@link com.openpojo.random.RandomFactory#getRandomValue(java.lang.Class)}.
     */
    @Test
    public final void testGetRandomValue() {
        for (Class<?> clazz : BASIC_TYPES) {
            Assert.assertNotNull(RandomFactory.getRandomValue(clazz));
            if (clazz.isPrimitive()) {
                Object returned = RandomFactory.getRandomValue(clazz);
                Assert.assertTrue((clazz == java.lang.Boolean.TYPE && returned instanceof java.lang.Boolean)
                        || (clazz == java.lang.Character.TYPE && returned instanceof java.lang.Character)
                        || (clazz == java.lang.Byte.TYPE && returned instanceof java.lang.Byte)
                        || (clazz == java.lang.Short.TYPE && returned instanceof java.lang.Short)
                        || (clazz == java.lang.Integer.TYPE && returned instanceof java.lang.Integer)
                        || (clazz == java.lang.Long.TYPE && returned instanceof java.lang.Long)
                        || (clazz == java.lang.Float.TYPE && returned instanceof java.lang.Float)
                        || (clazz == java.lang.Double.TYPE && returned instanceof java.lang.Double));
            } else { // make sure we got the right type returned.
                Assert.assertEquals(clazz, (RandomFactory.getRandomValue(clazz)).getClass());
            }
        }
        try {
            RandomFactory.getRandomValue(UnRegisteredDummy.class);
            Assert.fail("Generating for unknown class should throw Exception");
        } catch (Exception re) {
            // Expected Exception path.
        }
    }

    private class RegisteredDummy {
        private String value;

        public RegisteredDummy(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    private class UnRegisteredDummy {
    }
}
