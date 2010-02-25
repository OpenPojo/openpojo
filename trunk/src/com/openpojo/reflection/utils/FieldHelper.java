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
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * This class is a utility class to aid with getting all declared fields for a given class.
 * 
 * @author oshoukry
 */
public class FieldHelper {
    public static List<Field> getDeclaredFields(Class<?> clazz) {
        List<Field> fields = new LinkedList<Field>();
        for (Field field : clazz.getDeclaredFields()) {
            fields.add(field);
        }
        return Collections.unmodifiableList(fields);
    }
}
