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

import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.reflection.exception.ReflectionException;

/**
 * This class is the default implementation for the PojoClass Interface, created through the PojoClassFactory.
 * 
 * @author oshoukry
 */
class PojoClassImpl implements PojoClass {
    private final Class<?> clazz;
    private final List<PojoField> pojoFields;
    private static final String NESTED_CLASS_TOKEN = "$";

    /**
     * Minimum constructor.
     */
    PojoClassImpl(final Class<?> clazz, final List<PojoField> pojoFields) {
        this.clazz = clazz;
        this.pojoFields = Collections.unmodifiableList(pojoFields);
    }

    public boolean isInterface() {
        return clazz.isInterface();
    }
    
    public boolean isAbstract() {
        return Modifier.isAbstract(clazz.getModifiers());
    }

    public List<PojoField> getPojoFields() {
        return pojoFields;
    }

    public String getName() {
        return clazz.getName();
    }

    public boolean extendz(final Class<?> type) {
        return type.isAssignableFrom(clazz);
    }

    public Object newInstance() {
        if (isInterface() || isAbstract()) {
            throw new ReflectionException("This PojoClass is an interface/abstract, can't create new instance");
        }
        try {
            return clazz.newInstance();
        } catch (Exception e) { // InstantiationException Or IllegalAccessException
            throw new ReflectionException(e);
        }
    }

    public boolean isNestedClass() {
        return clazz.getName().contains(NESTED_CLASS_TOKEN);
    }

    public void copy(final Object from, final Object to) {
        for (PojoField pojoField : pojoFields) {
            if (pojoField.hasGetter() && pojoField.hasSetter()) {
                Object value = pojoField.invokeGetter(from);
                pojoField.inovkeSetter(to, value);
            }
        }
    }

    public Class<?> getClazz() {
        return clazz;
    }

    @Override
    public String toString() {
        return String.format("PojoClassImpl [clazz=%s, pojoFields=%s]", clazz, pojoFields);
    }

}
