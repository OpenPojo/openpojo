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
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import com.openpojo.reflection.PojoMethod;
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
        allowAccessiblity();
    }

    private void allowAccessiblity() {
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

    public Object invoke(final Object instance, final Object... parameters) {
        if (isConstructor()) {
            try {
                return getAsConstructor().newInstance(parameters);
            } catch (Exception e) {
                throw ReflectionException.getInstance(e);
            }
        }

        try {
            return getAsMethod().invoke(instance, parameters);
        } catch (Exception e) {
            throw ReflectionException.getInstance(e);
        }
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

    public boolean isConstructor() {
        return accessibleObject instanceof Constructor<?>;
    }

    public Class<?>[] getParameterTypes() {
        if (isConstructor()) {
            return getAsConstructor().getParameterTypes();
        }
        return getAsMethod().getParameterTypes();
    }

    private Method getAsMethod() {
        if (!isConstructor()) {
            return (Method) accessibleObject;
        }
        throw ReflectionException.getInstance("Attempt to get Constructor as Method!!");
    }

    private Constructor<?> getAsConstructor() {
        if (isConstructor()) {
            return (Constructor<?>) accessibleObject;
        }
        throw ReflectionException.getInstance("Attempt to get Method as Constructor!!");
    }

    private int getModifiers() {
        if (isConstructor()) {
            return getAsConstructor().getModifiers();
        }
        return getAsMethod().getModifiers();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        String tag = isConstructor() ? "constructor" : "method";
        return String.format("PojoMethodImpl [%s=%s args=%s]", tag, getName(), Arrays.toString(getParameterTypes()));
    }
}
