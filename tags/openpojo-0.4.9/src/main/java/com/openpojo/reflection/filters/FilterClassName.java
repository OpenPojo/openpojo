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

import java.util.regex.Pattern;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;

/**
 * Filter classes based on regular regular expression
 *
 * Example: ".+Domain$" to only include classes that end in Domain "^((?!Test$).)*$" to exclude classes that end with
 * Test
 *
 * This implementation was submitted on issue 20.
 *
 * @author oshoukry
 */
public class FilterClassName implements PojoClassFilter {
    private final Pattern pattern;

    public FilterClassName(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    public boolean include(PojoClass pojoClass) {
        return pattern.matcher(pojoClass.getName()).find();
    }

}
