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

import java.lang.reflect.Constructor;
import java.util.LinkedList;
import java.util.List;

/**
 * @author oshoukry
 */
public final class ConstructionHelper {

    public static List<Constructor<?>> getConstructorsByParamCount(final Class<?> clazz, final int count) {
        LinkedList<Constructor<?>> elgibleConstructors = new LinkedList<Constructor<?>>();
        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            if (constructor.getParameterTypes().length == count) {
                elgibleConstructors.add(constructor);
            }
        }
        return elgibleConstructors;
    }
}
