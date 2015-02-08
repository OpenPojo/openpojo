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

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.openpojo.reflection.java.type.Resolver;
import com.openpojo.reflection.PojoParameter;

/**
 * @author oshoukry
 */
public class PojoParameterImpl implements PojoParameter {

    private final Type type;
    private final List<? extends Annotation> annotations;

    public PojoParameterImpl(Type type, Annotation[] annotations) {
        this.type = type;
        List<Annotation> tmpAnnotations = new ArrayList<Annotation>();
        if (annotations != null) {
            for (Annotation entry : annotations) {
                if (entry != null)
                    tmpAnnotations.add(entry);
            }
        }
        this.annotations = Collections.unmodifiableList(tmpAnnotations);
    }

    public List<? extends Annotation> getAnnotations() {
        return annotations;
    }

    @SuppressWarnings("unchecked")
    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {

        for (Annotation entry : annotations) {
            if (entry.annotationType().equals(annotationClass)){
                return (T) entry;
            }
        }
        return null;
    }

    public Class<?> getType() {
        return (Class<?>) Resolver.getEnclosingType(type);
    }

    public boolean isParameterized() {
        return (type instanceof ParameterizedType);
    }

    public List<Type> getParameterTypes() {
        return Arrays.asList(Resolver.getParameterTypes(type));
    }

    @Override
    public String toString() {
        return String.format("%s [@%s: Type=%s, Annotations=%s]", this.getClass().getName(), this.hashCode(), type, annotations );
    }
}
