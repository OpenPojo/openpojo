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

package com.openpojo.reflection.java.type.impl;

import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Arrays;

import com.openpojo.reflection.java.type.TypeResolver;
import com.openpojo.reflection.exception.ReflectionException;

/**
 * @author oshoukry
 */
public class WildcardTypeResolver implements TypeResolver<WildcardType> {

    public Type getEnclosingType(WildcardType type) {
        return resolveType(type);
    }

    public Type resolveType(WildcardType wildcardType) {

        Type[] lowerBounds = wildcardType.getLowerBounds();
        Type[] upperBounds = wildcardType.getUpperBounds();

        ensureAValidBoundaryExists(lowerBounds, upperBounds);

        return getParameterTypes(wildcardType)[0];
    }

    public Type[] getParameterTypes(WildcardType wildcardType) {
        Type[] bounds = wildcardType.getLowerBounds();
        if (bounds == null || bounds.length == 0)
            return wildcardType.getUpperBounds();
        return bounds;
    }

    private void ensureAValidBoundaryExists(Type[] lowerBounds, Type[] upperBounds) {
        if (lowerBounds.length > 1 || upperBounds.length > 1 || (upperBounds.length == 0 && lowerBounds.length == 0))
            throw ReflectionException.getInstance("Unable to identify proper resolution for type, " +
                    "multiple UpperBounds[" + Arrays.toString(upperBounds) + "] Or LowerBounds[" + Arrays.toString(lowerBounds) + "]");
    }


}
