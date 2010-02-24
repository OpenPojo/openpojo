package com.openpojo.validation.test;

import com.openpojo.reflection.PojoClass;

/**
 * This interface defines how tests should be run.
 * 
 * @author oshoukry
 */
public interface Tester {
    /**
     * This method starts the test, and has no return value.
     * Every test should perform its own "Assert"ions and fail accordingly.
     * 
     * @param pojoClass
     *            The PojoClass being tested.
     */
    public void run(PojoClass pojoClass);
}
