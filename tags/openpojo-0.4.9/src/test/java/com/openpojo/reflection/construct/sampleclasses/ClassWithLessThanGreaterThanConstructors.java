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

package com.openpojo.reflection.construct.sampleclasses;

/**
 * @author oshoukry
 *
 */
public class ClassWithLessThanGreaterThanConstructors {
    private final Integer parameterCountUsedForConstruction;

    public ClassWithLessThanGreaterThanConstructors(final String param1) {
        parameterCountUsedForConstruction = 1;
    }

    public ClassWithLessThanGreaterThanConstructors(final String param1, final String param2) {
        parameterCountUsedForConstruction = 2;
    }

    public ClassWithLessThanGreaterThanConstructors(final String param1, final String param2, final String param3) {
        parameterCountUsedForConstruction = 3;
    }

    public Integer getParameterCountUsedForConstruction() {
        return parameterCountUsedForConstruction;
    }
}
