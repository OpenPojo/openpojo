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
 * This class has a misspelling in the name and has been renamed, please use FilterBasedOnInheritance instead.
 *
 * @author oshoukry
 * @deprecated This class has been renamed due to the typo in the name, please use FilterBasedOnInheritance instead.
 */
@Deprecated
public class FilterBasedOnInheritence implements PojoClassFilter {
    private FilterBasedOnInheritance filterBasedOnInheritance;

    public FilterBasedOnInheritence(final Class<?> type) {
        filterBasedOnInheritance = new FilterBasedOnInheritance(type);
        System.out.println("Deprecated Usage: Please use com.openpojo.reflection.filters.FilterBasedOnInheritance " +
                "instead of this class.");
    }

    public boolean include(final PojoClass pojoClass) {
        return filterBasedOnInheritance.include(pojoClass);
    }
}
