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

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * This Class is responsible for helping get the getters/setters for a given field.
 *
 * @author oshoukry
 */
public final class MethodHelper {

    /**
     * Returns a list of declared methods on class.
     *
     * @param clazz
     *            The class to introspect.
     * @return
     *         List of all methods founds on that class.
     */
    public static List<Method> getDeclaredMethods(final Class<?> clazz) {
        List<Method> methods = new LinkedList<Method>();
        for (Method method : clazz.getDeclaredMethods()) {
            methods.add(method);
        }
        return Collections.unmodifiableList(methods);
    }
}
