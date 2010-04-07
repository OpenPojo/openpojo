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
package com.openpojo.reflection.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

/**
 * This Class is responsible for helping get the getters/setters for a given field.
 *
 * @author oshoukry
 */
public final class MethodHelper {
    /**
     * Returns the Setter Method for a field.
     *
     * @param field
     *            The field to lookup the setter on.
     * @return
     *         The setter method or null if none exist.
     */
    public static Method getSetter(final Field field) {
        Method method;
        for (String candidateName : generateSetMethodNames(field)) {
            try {
                Class<?> clazz = field.getDeclaringClass();
                method = clazz.getDeclaredMethod(candidateName, field.getType());
                return method;
            } catch (NoSuchMethodException nsme) {
            }
        }
        return null;
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

    /**
     * Returns the Getter Method for a field.
     *
     * @param field
     *            The field to lookup the getter on.
     * @return
     *         The getter method or null if none exist.
     */
    public static Method getGetter(final Field field) {
        Method method;
        for (String candidateName : generateGetMethodNames(field)) {
            try {
                Class<?> clazz = field.getDeclaringClass();
                method = clazz.getDeclaredMethod(candidateName);
                return method;
            } catch (NoSuchMethodException nsme) {
            }

        }
        return null;
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
}
