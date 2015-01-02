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

package com.openpojo.random.collection.type.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

import com.openpojo.random.collection.type.TypeResolver;
import com.openpojo.reflection.exception.ReflectionException;

/**
 * @author oshoukry
 */
public class ParameterizedTypeResolver implements TypeResolver<ParameterizedType> {

    public Type getEnclosingType(ParameterizedType type) {
        return type.getRawType();
    }

    public Type getEnclosedType(ParameterizedType type) {
        if (type.getActualTypeArguments().length == 1)
            return type.getActualTypeArguments()[0];

        throw ReflectionException.getInstance("Unexpected array encountered when calling ParameterizedType.getActualTypeArguments() on [" +
                type + "]" + " expected array with one element, found " + Arrays.toString(type.getActualTypeArguments()));
    }

    public Type resolveType(ParameterizedType type) {
        return type.getRawType();
    }

    public Type[] getParameterTypes(ParameterizedType type) {
        return type.getActualTypeArguments();
    }
}