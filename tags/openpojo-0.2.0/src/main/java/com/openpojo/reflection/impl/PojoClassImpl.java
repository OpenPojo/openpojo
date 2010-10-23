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
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.utils.ToStringHelper;

/**
 * This class is the default implementation for the PojoClass Interface, created through the PojoClassFactory.
 *
 * @author oshoukry
 */
class PojoClassImpl implements PojoClass {
    private final Class<?> clazz;
    private final List<PojoField> pojoFields;
    private final List<PojoMethod> pojoMethods;
    private static final String NESTED_CLASS_TOKEN = "$";

    /**
     * Minimum constructor.
     */
    PojoClassImpl(final Class<?> clazz, final List<PojoField> pojoFields, final List<PojoMethod> pojoMethods) {
        this.clazz = clazz;
        this.pojoFields = Collections.unmodifiableList(pojoFields);
        this.pojoMethods = Collections.unmodifiableList(pojoMethods);
    }

    public boolean isInterface() {
        return clazz.isInterface();
    }

    public boolean isAbstract() {
        return Modifier.isAbstract(clazz.getModifiers());
    }

    public boolean isConcrete() {
        return !(isAbstract() || isInterface() || isEnum());
    }

    public boolean isEnum() {
        return clazz.isEnum();
    }

    public boolean isFinal() {
        return Modifier.isFinal(clazz.getModifiers());
    }

    public List<PojoField> getPojoFields() {
        return pojoFields;
    }

    public List<PojoMethod> getPojoMethods() {
        return pojoMethods;
    }

    public List<PojoMethod> getPojoConstructors() {
        List<PojoMethod> constructors = new LinkedList<PojoMethod>();
        for (PojoMethod pojoMethod : pojoMethods) {
            if (pojoMethod.isConstructor()) {
                constructors.add(pojoMethod);
            }
        }
        return constructors;
    }

    public String getName() {
        return clazz.getName();
    }

    public <T extends Annotation> T getAnnotation(final Class<T> annotationClass) {
        return clazz.getAnnotation(annotationClass);
    }

    public List<? extends Annotation> getAnnotations() {
        return Arrays.asList(clazz.getAnnotations());
    }

    public boolean extendz(final Class<?> type) {
        return type.isAssignableFrom(clazz);
    }

    public boolean isNestedClass() {
        return clazz.getName().contains(NESTED_CLASS_TOKEN);
    }

    public void copy(final Object from, final Object to) {
        for (PojoField pojoField : pojoFields) {
            if (pojoField.hasGetter() && pojoField.hasSetter()) {
                Object value = pojoField.invokeGetter(from);
                pojoField.invokeSetter(to, value);
            }
        }
    }

    public Class<?> getClazz() {
        return clazz;
    }

    @Override
    public String toString() {
        return String.format("PojoClassImpl [clazz=%s, pojoFields=%s, pojoMethods=%s]", clazz, pojoFields, pojoMethods);
    }

    public String toString(final Object instance) {
        return ToStringHelper.pojoClassToString(this, instance);
    }

    public PojoClass getSuperClass() {
        if (clazz.getSuperclass() != null) {
            return PojoClassFactory.getPojoClass(clazz.getSuperclass());
        }
        return null;
    }

    public List<PojoClass> getInterfaces() {
        List<PojoClass> interfaces = new LinkedList<PojoClass>();
        for (Class<?> interfaze : clazz.getInterfaces()) {
            interfaces.add(PojoClassFactory.getPojoClass(interfaze));
        }
        return interfaces;
    }
}
