package com.openpojo.reflection.impl;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.openpojo.reflection.PojoField;
import com.openpojo.reflection.utils.FieldHelper;
import com.openpojo.reflection.utils.MethodHelper;

/**
 * This Field Factory returns a list of PojoFields for a given Class.
 * @author oshoukry
 */
class PojoFieldFactoryImpl {
    /**
     * Get all PojoFields in a given Class.
     * @param clazz
     *          The class to Introspect.
     * @return
     *          List of All Fields as PojoFields in that class.
     */
    public static List<PojoField> getPojoFields(Class<?> clazz) {
        List<PojoField> pojoFields = new LinkedList<PojoField>();
        for (Field field : FieldHelper.getDeclaredFields(clazz)) {
            pojoFields.add(new PojoFieldImpl(field, MethodHelper.getGetter(field), MethodHelper.getSetter(field)));
        }
        return Collections.unmodifiableList(pojoFields);
    }

}
