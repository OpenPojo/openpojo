/**
 * 2010 Copyright Osman Shoukry.
 */
package com.openpojo.impl;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.openpojo.PojoClass;
import com.openpojo.PojoClassFilter;
import com.openpojo.PojoField;
import com.openpojo.cache.PojoCache;
import com.openpojo.utils.MethodLookup;
import com.openpojo.utils.PojoPackageHelper;

/**
 * @author oshoukry
 *         This is a factory class that builds PojoClassImpl representation given a class.
 */
public final class PojoClassFactory {

    public static List<PojoClass> getPojoClasses(final String packagename) throws ClassNotFoundException {
        return getPojoClasses(packagename, null);
    }

    public static List<PojoClass> getPojoClasses(final String packagename, PojoClassFilter filter)
            throws ClassNotFoundException {
        PojoPackageHelper pkg = new PojoPackageHelper(packagename);
        List<PojoClass> rawList = pkg.getPojoClasses();

        if (filter != null) {
            List<PojoClass> filteredList = new LinkedList<PojoClass>();
            for (PojoClass entry : rawList) {
                if (filter.include(entry)) {
                    filteredList.add(entry);
                }
            }
            return filteredList;
        }
        return rawList;
    }

    /**
     * @param clazz
     * @return
     */
    public static PojoClass buildPojoClass(final Class<?> clazz) {

        PojoClass returnPojo = PojoCache.getPojoClass(clazz.getName());

        if (returnPojo == null) {
            List<PojoField> pojoFields = new LinkedList<PojoField>();
            for (Field field : getDeclaredFields(clazz)) {
                if (!field.isSynthetic()) { // inner instance classes are synthetic skip those
                    pojoFields.add(new PojoFieldImpl(field, MethodLookup.getGetter(clazz, field), MethodLookup
                            .getSetter(clazz, field)));
                }
            }
            returnPojo = new PojoClassImpl(clazz, pojoFields);
            PojoCache.addPojoClass(returnPojo.getName(), returnPojo);
        }
        return returnPojo;
    }

    /**
     * Returns all declared fields on a given class.
     * 
     * @param clazz
     *            The class to generate fields for.
     * @return
     *         List containing the fields of the class.
     */
    private static List<Field> getDeclaredFields(final Class<?> clazz) {
        LinkedList<Field> fields = new LinkedList<Field>();
        for (Field field : clazz.getDeclaredFields()) {
            fields.add(field);
        }
        return Collections.unmodifiableList(fields);
    }
}
