/*
 * Copyright (c) 2010-2013 Osman Shoukry
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License or any
 *    later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.openpojo.reflection.filters;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;

/**
 * This filter includes classes that extend/implement a certain type.
 * Note: This class filters out the extended/implemented class/interface.
 *
 * @author oshoukry
 */
public class FilterBasedOnInheritence implements PojoClassFilter {
    private final Class<?> type;

    /**
     * Construtor.
     *
     * @param type
     *            The type/class to use for inclusion (i.e. the "Class" extends/implements type).
     */
    public FilterBasedOnInheritence(final Class<?> type) {
        this.type = type;
    }

    public boolean include(final PojoClass pojoClass) {
        if (!pojoClass.getName().equals(type.getName())) {
            return pojoClass.extendz(type);
        }
        return false;
    }
}
