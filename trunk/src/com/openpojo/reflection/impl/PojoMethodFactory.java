/**
 * Copyright (C) 2010 Osman Shoukry
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.openpojo.reflection.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.openpojo.reflection.PojoMethod;

/**
 * @author oshoukry
 */
public class PojoMethodFactory {
    public static List<PojoMethod> getPojoMethods(final Class<?> clazz) {
        final List<PojoMethod> pojoMethods = new LinkedList<PojoMethod>();

        for (final Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            pojoMethods.add(new PojoMethodImpl(constructor));
        }

        for (final Method method : clazz.getDeclaredMethods()) {
            pojoMethods.add(new PojoMethodImpl(method));
        }
        return Collections.unmodifiableList(pojoMethods);
    }

    public static PojoMethod getMethod(final Class<?> clazz, final String name, final Class<?>... parameterTypes) {
        for (PojoMethod pojoMethod : getPojoMethods(clazz)) {
            if (pojoMethod.getName().equals(name) && Arrays.equals(pojoMethod.getParameterTypes(), parameterTypes)) {
                return pojoMethod;
            }
        }
        return null;
    }

    /**
     * Returns the Getter Method for a field.
     *
     * @param field
     *            The field to lookup the getter on.
     * @return
     *         The getter method or null if none exist.
     */
    public static PojoMethod getFieldGetter(final Field field) {
        PojoMethod pojoMethod = null;
        for (String candidateName : generateGetMethodNames(field)) {
            Class<?> clazz = field.getDeclaringClass();
            pojoMethod = PojoMethodFactory.getMethod(clazz, candidateName);
            if (pojoMethod != null) {
                break;
            }
        }
        return pojoMethod;
    }

    /**
     * Returns a list for candidate getter names.
     *
     * @param field
     *            Field to generate the candidate getter names for.
     * @return
     *         List of candidate method names.
     */
    private static List<String> generateGetMethodNames(final Field field) {
        List<String> prefix = new LinkedList<String>();
        prefix.add("get" + formattedFieldName(field));
        if (field.getType() == boolean.class || field.getType() == Boolean.class) {
            prefix.add("is" + formattedFieldName(field));
        }
        return prefix;
    }

    /**
     * Properly CamelCased the field name as expected "first Letter is uppercase, rest is unchanged".
     *
     * @param field
     *            The field to proper case.
     * @return
     *         Formatted field name.
     */
    private static String formattedFieldName(final Field field) {
        return field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1, field.getName().length());
    }

    /**
     * Returns the Setter Method for a field.
     *
     * @param field
     *            The field to lookup the setter on.
     * @return
     *         The setter method or null if none exist.
     */
    public static PojoMethod getFieldSetter(final Field field) {
        PojoMethod pojoMethod = null;

        for (String candidateName : generateSetMethodNames(field)) {
            Class<?> clazz = field.getDeclaringClass();
            pojoMethod = PojoMethodFactory.getMethod(clazz, candidateName, field.getType());
            if (pojoMethod != null) {
                break;
            }
        }
        return pojoMethod;
    }


    /**
     * Returns a list for candidate setter names.
     *
     * @param field
     *            The field to generate for.
     * @return
     */
    private static List<String> generateSetMethodNames(final Field field) {
        List<String> prefix = new LinkedList<String>();
        prefix.add("set" + formattedFieldName(field));
        return prefix;
    }

}
