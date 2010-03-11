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

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;

/**
 * @author oshoukry
 */
public class FilterBasedOnInheritence implements PojoClassFilter {
    private final Class<?> type;
    private final PojoClassFilter filter;
    
    public FilterBasedOnInheritence(final Class<?> type, final PojoClassFilter filter) {
        this.type = type;
        this.filter = filter;
    }

    @Override
    public boolean include(final PojoClass pojoClass) {
        if (!pojoClass.getName().equals(type.getName()) && (filter == null || filter.include(pojoClass)))
            return pojoClass.extendz(type);
        return false;
    }
}
