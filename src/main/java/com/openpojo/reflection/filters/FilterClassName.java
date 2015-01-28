/*
 * Copyright (c) 2010-2015 Osman Shoukry
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
    //Since Pattern doesn't implement equals, relying on String for equals & hashCode instead.
    private final String regex;
    private final Pattern pattern;

    public FilterClassName(String regex) {
        this.regex = regex;
        this.pattern = Pattern.compile(regex);
    }

    public boolean include(PojoClass pojoClass) {
        return pattern.matcher(pojoClass.getName()).find();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilterClassName that = (FilterClassName) o;

        return regex.equals(that.regex);
    }

    @Override
    public int hashCode() {
        return regex.hashCode();
    }

}
