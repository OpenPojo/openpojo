/**
 * Copyright (C) 2010 Osman Shoukry
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.openpojo.reflection.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.exception.ReflectionException;

/**
 * @author oshoukry
 *
 */
public class PojoMethodImpl implements PojoMethod {
    private final Method method;

    PojoMethodImpl(final Method method) {
        this.method = method;
        this.method.setAccessible(true);
    }

    public <T extends Annotation> T getAnnotation(final Class<T> annotationClass) {
        return method.getAnnotation(annotationClass);
    }

    public String getName() {
        return method.getName();
    }

    public Object invoke(final Object instance, final Object... parameters) {
        try {
            return method.invoke(instance, parameters);
        } catch (Exception e) {
            throw ReflectionException.getInstance(e);
        }
    }

    public boolean isFinal() {
        return Modifier.isFinal(method.getModifiers());
    }

    public boolean isPrivate() {
        return Modifier.isPrivate(method.getModifiers());
    }

    public boolean isProtected() {
        return Modifier.isProtected(method.getModifiers());
    }

    public boolean isPublic() {
        return Modifier.isPublic(method.getModifiers());
    }

    public boolean isStatic() {
        return Modifier.isStatic(method.getModifiers());
    }

    public Class<?>[] getParameterTypes() {
        return method.getParameterTypes();
    }

}
