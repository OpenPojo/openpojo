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

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.openpojo.reflection.PojoField;
import com.openpojo.reflection.utils.FieldHelper;
import com.openpojo.reflection.utils.MethodHelper;

/**
 * This Field Factory returns a list of PojoFields for a given Class.
 * 
 * @author oshoukry
 */
class PojoFieldFactoryImpl {
    /**
     * Get all PojoFields in a given Class.
     * 
     * @param clazz
     *            The class to Introspect.
     * @return
     *         List of All Fields as PojoFields in that class.
     */
    public static List<PojoField> getPojoFields(Class<?> clazz) {
        List<PojoField> pojoFields = new LinkedList<PojoField>();
        for (Field field : FieldHelper.getDeclaredFields(clazz)) {
            pojoFields.add(new PojoFieldImpl(field, MethodHelper.getGetter(field), MethodHelper.getSetter(field)));
        }
        return Collections.unmodifiableList(pojoFields);
    }

}
