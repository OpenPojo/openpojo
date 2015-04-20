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

package com.openpojo.random.map.util;

import java.util.Collection;
import java.util.Map;

import com.openpojo.random.ParameterizableRandomGenerator;
import com.openpojo.reflection.Parameterizable;

/**
 * @author oshoukry
 */
public abstract class AbstractMapRandomGenerator implements ParameterizableRandomGenerator {

    @SuppressWarnings("unchecked")
    public Map doGenerate(Class<?> type) {
        return getBasicInstance(type);
    }

    public Map doGenerate(Parameterizable parameterizedType) {
        return MapHelper.buildMap(doGenerate(parameterizedType.getType()), parameterizedType.getParameterTypes().get(0),
                parameterizedType.getParameterTypes().get(1));
    }

    protected boolean isAssignableTo(Class<?> type) {
        for (Class<?> knownType : getTypes()) {
            if (type.isAssignableFrom(knownType))
                return true;
        }
        return false;
    }

    public abstract Collection<Class<?>> getTypes();

    protected abstract Map getBasicInstance(Class<?> type);
}
