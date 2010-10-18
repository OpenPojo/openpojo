package com.openpojo.random.impl;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.openpojo.random.RandomGenerator;

public class VoidRandomGeneratorTest {
    RandomGenerator voidRandomGenerator;
    Class<?> voidClass = void.class;
    private static final int EXPECTED_COUNT = 1;

    @Before
    public void setUp() throws Exception {
        voidRandomGenerator = VoidRandomGenerator.getInstance();
    }

    @Test
    public void testGetInstance() {
        Assert.assertNotNull("Null object returned for VoidRandomGenerator.getInstance()", voidRandomGenerator);
        Assert.assertTrue(String.format("Incorrect type returned=[%s] for requested type=[%s]", voidRandomGenerator
                .getClass(), VoidRandomGenerator.class), voidRandomGenerator instanceof VoidRandomGenerator);
    }

    @Test
    public void testDoGenerate() {
        Assert.assertNull("Non null returned when invoking void random generation", voidRandomGenerator
                .doGenerate(voidClass));
    }

    @Test
    public void testGetTypes() {
        CommonCode.testGetType(voidRandomGenerator, voidClass, EXPECTED_COUNT);

    }

}
