/**
 * 2010 Copyright Osman Shoukry.
 */
package com.openpojo.impl;

import java.util.Collections;
import java.util.List;

import com.openpojo.PojoClass;
import com.openpojo.PojoField;
import com.openpojo.exception.ReflectionException;

/**
 * This class is the default implementation for the PojoClass Interface, created through the PojoClassFactory.
 * @author oshoukry
 */
public class PojoClassImpl implements PojoClass {
    private final Class<?> clazz;
    private final List<PojoField> pojoFields;
    private static final String NESTED_CLASS_TOKEN = "$";

    /**
     * Prevent direct construction.
     * Use the factory method.
     * 
     * @param clazz
     *            The class to wrapp in a PojoClassImpl.
     * @param pojoFieldImpls
     *            The PojoFields that make up that class.
     */
    public PojoClassImpl(final Class<?> clazz, final List<PojoField> pojoFields) {
        this.clazz = clazz;
        this.pojoFields = Collections.unmodifiableList(pojoFields);
    }

    public List<PojoField> getPojoFields() {
        return pojoFields;
    }

    public String getName() {
        return clazz.getName();
    }

    public boolean extendz(final Class<?> type) {
        return type.isAssignableFrom(clazz);
    }

    public Object newInstance() {
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            throw new ReflectionException(e);
        } catch (IllegalAccessException e) {
            throw new ReflectionException(e);
        }
    }

    public boolean isNestedClass() {
        return clazz.getName().contains(NESTED_CLASS_TOKEN);
    }

    public void copy(final Object from, final Object to) {
        for (PojoField pojoFieldImpl : pojoFields) {
            if (pojoFieldImpl.hasGetter()) {
                Object value = pojoFieldImpl.invokeGetter(from);
                pojoFieldImpl.inovkeSetter(to, value);
            }
        }
    }

    public Class<?> getClazz() {
        return clazz;
    }

    @Override
    public String toString() {
        return String.format("PojoClassImpl [clazz=%s, pojoFieldImpls=%s]", clazz, pojoFields);
    }


}
