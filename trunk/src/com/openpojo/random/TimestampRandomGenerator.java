/**
 * 2010 Copyright Osman Shoukry.
 */
package com.openpojo.random;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author oshoukry
 * 
 */
public final class TimestampRandomGenerator implements RandomGenerator {
    private static final Class<?>[] TYPES = new Class<?>[] { Timestamp.class };

    public Object doGenerate(Class<?> type) {
        return new Timestamp((Long)RandomFactory.getRandomValue(Long.class));
    }

    public Collection<Class<?>> getTypes() {
        return Arrays.asList(TYPES);
    }
}
