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

package com.openpojo.reflection.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import com.openpojo.reflection.java.type.Resolver;
import com.openpojo.reflection.Parameterizable;

/**
* @author oshoukry
*/
public class ParameterizableFactory {

    public static Parameterizable getInstance(Type type) {
        return new ParameterizableImpl(type);
    }

    private static class ParameterizableImpl implements Parameterizable {
        private final Type type;

        public ParameterizableImpl(Type type) {
            this.type = type;
        }

        public Class<?> getType() {
            return (Class<?>) Resolver.resolve(type);
        }

        public boolean isParameterized() {
            return type instanceof ParameterizedType;
        }

        public List<Type> getParameterTypes() {
            return Arrays.asList(Resolver.getParameterTypes(type));
        }
    }
}
