/**
 * Copyright (C) 2010 Osman Shoukry
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
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

import com.openpojo.log.LoggerFactory;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.utils.AttributeHelper;

/**
 * This factory handles various method related operations on given Class or Field.
 *
 * @author oshoukry
 */
public class PojoMethodFactory {

    /**
     * Returns all methods on a given Class. Note: Constructors are treated as methods and will be returned in the list
     * as well.
     *
     * @param clazz
     *            The class to introspect for methods / constructors.
     * @return A list of all methods and constructors in a class.
     */
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

    /**
     * Returns a specific method given method name and parameters.
     *
     * @param clazz
     *            The Class that holds the method.
     * @param name
     *            The name of the method to return.
     * @param parameterTypes
     *            The Parameters to match.
     * @return A PojoMethod if found, or null otherwise.
     */
    public static PojoMethod getMethod(final Class<?> clazz, final String name, final Class<?>... parameterTypes) {
        for (final PojoMethod pojoMethod : getPojoMethods(clazz)) {
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
     * @return The getter method or null if none exist.
     */
    public static PojoMethod getFieldGetter(final Field field) {
        PojoMethod pojoMethod = null;
        for (final String candidateName : generateGetMethodNames(field)) {
            final Class<?> clazz = field.getDeclaringClass();
            pojoMethod = PojoMethodFactory.getMethod(clazz, candidateName);
            if (pojoMethod != null) {
                if (pojoMethod.getReturnType().equals(field.getType())) {
                    break;
                } else {
                    LoggerFactory.getLogger(PojoMethodFactory.class).warn("Getter=[{0}] in class=[{1}] rejected due non-euqal return types [{2} != {3}]", pojoMethod.getName(), field.getDeclaringClass().getName(), pojoMethod.getReturnType(), field.getType());
                    pojoMethod = null;
                }
            }
        }
        return pojoMethod;
    }

    /**
     * Returns a list for candidate getter names.
     *
     * @param field
     *            Field to generate the candidate getter names for.
     * @return List of candidate method names.
     */
    private static List<String> generateGetMethodNames(final Field field) {
        final List<String> prefix = new LinkedList<String>();
        prefix.add("get" + AttributeHelper.getAttributeName(field));
        if (field.getType() == boolean.class || field.getType() == Boolean.class) {
            prefix.add("is" + AttributeHelper.getAttributeName(field));
        }
        return prefix;
    }

    /**
     * Returns the Setter Method for a field.
     *
     * @param field
     *            The field to lookup the setter on.
     * @return The setter method or null if none exist.
     */
    public static PojoMethod getFieldSetter(final Field field) {
        PojoMethod pojoMethod = null;

        for (final String candidateName : generateSetMethodNames(field)) {
            final Class<?> clazz = field.getDeclaringClass();
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
        final List<String> prefix = new LinkedList<String>();
        prefix.add("set" + AttributeHelper.getAttributeName(field));
        return prefix;
    }

}
