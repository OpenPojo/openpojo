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

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.utils.MethodHelper;

/**
 * @author oshoukry
 */
public class PojoMethodFactory {
    public static List<PojoMethod> getPojoMethods(final Class<?> clazz) {
        final List<PojoMethod> pojoMethods = new LinkedList<PojoMethod>();
        for (final Method method : MethodHelper.getDeclaredMethods(clazz)) {
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
}
