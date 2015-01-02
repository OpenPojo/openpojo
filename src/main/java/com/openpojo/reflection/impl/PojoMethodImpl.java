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
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.PojoParameter;
import com.openpojo.reflection.exception.ReflectionException;

/**
 * @author oshoukry
 */
public class PojoMethodImpl implements PojoMethod {
    private final AccessibleObject accessibleObject;

    PojoMethodImpl(final Method method) {
        this((AccessibleObject) method);
    }

    PojoMethodImpl(final Constructor<?> constructor) {
        this((AccessibleObject) constructor);
    }

    private PojoMethodImpl(final AccessibleObject accessibleObject) {
        this.accessibleObject = accessibleObject;
    }

    private void allowAccessibility() {
        accessibleObject.setAccessible(true);
    }

    public String getName() {
        if (isConstructor()) {
            return getAsConstructor().getName();
        }
        return getAsMethod().getName();
    }

    public <T extends Annotation> T getAnnotation(final Class<T> annotationClass) {
        return accessibleObject.getAnnotation(annotationClass);
    }

    public List<? extends Annotation> getAnnotations() {
        return Arrays.asList(accessibleObject.getAnnotations());
    }

    public Object invoke(final Object instance, final Object... parameters) {
        allowAccessibility();
        if (isConstructor()) {
            try {
                return getAsConstructor().newInstance(parameters);
            } catch (final IllegalArgumentException e) {
                throw ReflectionException.getInstance(e.getMessage(), e);
            } catch (final InstantiationException e) {
                throw ReflectionException.getInstance(e.getMessage(), e);
            } catch (final IllegalAccessException e) {
                throw ReflectionException.getInstance(e.getMessage(), e);
            } catch (final InvocationTargetException e) {
                throw ReflectionException.getInstance(e.getMessage(), e);
            }
        }

        try {
            return getAsMethod().invoke(instance, parameters);
        } catch (final IllegalArgumentException e) {
            throw ReflectionException.getInstance(e.getMessage(), e);
        } catch (final IllegalAccessException e) {
            throw ReflectionException.getInstance(e.getMessage(), e);
        } catch (final InvocationTargetException e) {
            throw ReflectionException.getInstance(e.getMessage(), e);
        }
    }

    public List<PojoParameter> getPojoParameters() {
        List<PojoParameter> parameters = new ArrayList<PojoParameter>();

        Annotation[][] parameterAnnotations;
        Type [] parameterTypes;
        Class<?>[] parameterClasses;

        if (isConstructor()) {
            parameterAnnotations = getAsConstructor().getParameterAnnotations();
            parameterTypes = getAsConstructor().getGenericParameterTypes();
            parameterClasses = getAsConstructor().getParameterTypes();
        } else {
            parameterAnnotations = getAsMethod().getParameterAnnotations();
            parameterTypes = getAsMethod().getGenericParameterTypes();
            parameterClasses = getAsMethod().getParameterTypes();
        }

        // If parameters aren't of Generic type, then default their types to the class params.
        if (parameterTypes.length == 0) parameterTypes = parameterClasses;

        for (int idx = 0; idx < parameterClasses.length; idx ++) {
            parameters.add(PojoParameterFactory.getPojoParameter(parameterTypes[idx], parameterAnnotations[idx]));
        }

        return parameters;
    }

    public boolean isFinal() {
        return Modifier.isFinal(getModifiers());
    }

    public boolean isPrivate() {
        return Modifier.isPrivate(getModifiers());
    }

    public boolean isProtected() {
        return Modifier.isProtected(getModifiers());
    }

    public boolean isPublic() {
        return Modifier.isPublic(getModifiers());
    }

    public boolean isStatic() {
        return Modifier.isStatic(getModifiers());
    }

    public boolean isSynthetic() {
        if (isConstructor()) {
            return getAsConstructor().isSynthetic();
        }
        return getAsMethod().isSynthetic();
    }

    public boolean isConstructor() {
        return accessibleObject instanceof Constructor<?>;
    }

    public Type[] getGenericParameterTypes() {
        if (isConstructor()) {
            return getAsConstructor().getGenericParameterTypes();
        }
        return getAsMethod().getGenericParameterTypes();
    }

    public Class<?>[] getParameterTypes() {
        if (isConstructor()) {
            return getAsConstructor().getParameterTypes();
        }
        return getAsMethod().getParameterTypes();
    }

    public Class<?> getReturnType() {
        if (isConstructor()) {
            return getAsConstructor().getDeclaringClass();
        }
        return getAsMethod().getReturnType();
    }

    private Method getAsMethod() {
        return (Method) accessibleObject;
    }

    private int getModifiers() {
        if (isConstructor()) {
            return getAsConstructor().getModifiers();
        }
        return getAsMethod().getModifiers();
    }

    private Constructor<?> getAsConstructor() {
        return (Constructor<?>) accessibleObject;
    }

    @Override
    public String toString() {
        final String tag = isConstructor() ? "constructor" : "method";
        return String.format("PojoMethodImpl [%s=%s args=%s return=%s]", tag, getName(), Arrays.toString(getParameterTypes()), getReturnType());
    }
}
