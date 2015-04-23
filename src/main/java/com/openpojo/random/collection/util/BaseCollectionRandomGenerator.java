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

package com.openpojo.random.collection.util;

import java.util.Collection;

import com.openpojo.random.ParameterizableRandomGenerator;
import com.openpojo.random.util.SerializableComparableObject;
import com.openpojo.reflection.Parameterizable;

/**
 * @author oshoukry
 */
public abstract class BaseCollectionRandomGenerator implements ParameterizableRandomGenerator {

    public Collection doGenerate(Class<?> type) {
        return CollectionHelper.buildCollections(getBasicInstance(type), SerializableComparableObject.class);
    }

    public Collection doGenerate(Parameterizable parameterizedType) {
        return CollectionHelper.buildCollections(doGenerate(parameterizedType.getType()), parameterizedType.getParameterTypes().get(0));
    }

    public abstract Collection<Class<?>> getTypes();

    protected abstract Collection getBasicInstance(Class<?> type);
}
