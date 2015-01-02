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

package com.openpojo.reflection.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.openpojo.random.collection.type.Resolver;
import com.openpojo.reflection.PojoParameter;

/**
 * @author oshoukry
 */
public class PojoParameterImpl implements PojoParameter {

    private final Type type;
    private final List<? extends Annotation> annotations;

    public PojoParameterImpl(Type type, Annotation[] annotations) {
        this.type = type;
        if (annotations == null) {
            this.annotations = Collections.EMPTY_LIST;
        } else {
            this.annotations = Collections.unmodifiableList(Arrays.asList(annotations));
        }

    }

    public List<? extends Annotation> getAnnotations() {
        return annotations;
    }

    @SuppressWarnings("unchecked")
    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        for (Annotation entry : annotations) {
            if (entry.getClass() == annotationClass)
                return (T) entry;
        }
        return null;
    }

    public Class<?> getType() {
        return (Class<?>) Resolver.getEnclosingType(type);
    }

    public boolean isParameterized() {
        return (type instanceof ParameterizedType);//TODO: Need to handle WildcardType as well... || type instanceof WildcardType);
    }

    public List<Type> getParameterTypes() {
        return Arrays.asList(Resolver.getParameterTypes(type));
    }
}
