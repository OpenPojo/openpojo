/**
 * 2010 Copyright Osman Shoukry.
 */
package com.openpojo.reflection;

/**
 * @author oshoukry
 * This Interface will serve as a way to exclude pojoClasses from the factory.
 * If the filter returns true, the class will be included, otherwise it will be excluded.
 */
public interface PojoClassFilter {

    /**
     * This Method determines whether to include a PojoClass or not.
     * @param pojoClass
     *          The pojoclass in question.
     * @return
     *          True if it should be included, false otherwise.
     */
    public boolean include(final PojoClass pojoClass);
}
