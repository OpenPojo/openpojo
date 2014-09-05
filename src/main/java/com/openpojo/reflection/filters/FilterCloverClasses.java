/*
 * Copyright (c) 2010-2014 Osman Shoukry
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
 * This class will filter out Clover classes.
 * The logic for filtering is simple, since all clover generated classes have "$__CLR" in their name.
 *
 * @author oshoukry
 *
 */
public class FilterCloverClasses implements PojoClassFilter {
    private static final String DEFAULT_CLOVER_TAG = "$__CLR";

    public boolean include(final PojoClass pojoClass) {
        return !pojoClass.getName().contains(DEFAULT_CLOVER_TAG);
    }

}
