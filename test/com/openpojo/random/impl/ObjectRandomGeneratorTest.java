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
