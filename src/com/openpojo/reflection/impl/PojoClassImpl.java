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

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.utils.ConstructionHelper;
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

    public String getName() {
        return clazz.getName();
    }

    public boolean extendz(final Class<?> type) {
        return type.isAssignableFrom(clazz);
    }

    private void validateBeforeNewInstance() {
        if (isInterface() || isAbstract()) {
            throw ReflectionException.getInstance(String.format(
                    "[%s] is an interface/abstract, can't create new instance", this));
        }
    }

    public Object newInstance(final Object... objects) {
        Object[] parameters = objects;
        if (parameters == null) {
            // newInstance(null) assumes you're calling the constructor that takes
            // no params... to pass null to the first parameter, need a valid array.
            parameters = new Object[]{ null };
        }
        validateBeforeNewInstance();
        List<Constructor<?>> constructors = ConstructionHelper.getConstructorsByParamCount(clazz, parameters.length);
        validateCandidateConstructors(constructors, objects);
        Constructor<?> constructor = constructors.get(0);
        try {
            constructor.setAccessible(true);
            return constructor.newInstance(parameters);
        } catch (Exception e) {
            // IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException
            throw ReflectionException.getInstance(e.getMessage(), e);
        }
    }

    private void validateCandidateConstructors(final List<Constructor<?>> constructors, final Object... objects) {
        if (constructors.size() == 0) {
            throw ReflectionException.getInstance(String.format(
                    "Unable to locate an appropriate constructor for class=[%s], given args=[%s]", clazz, Arrays
                            .toString(objects)));
        }
        if (constructors.size() > 1) {
            // need to filter down the list by arg types
            throw ReflectionException.getInstance(String.format(
                    "Unable to locate an appropriate constructor for class=[%s],"
                            + " [%s] constructor(s) found that take [%s] arguments, "
                            + "filter down by arg type not implemented", clazz, constructors.size(), objects.length));
        }
    }

    public Object newInstance() {
        validateBeforeNewInstance();

        try {
            Constructor<?> c = clazz.getDeclaredConstructor(); // NoSuchMethodException, SecurityException
            c.setAccessible(true); // SecurityException
            return c.newInstance(); // InstantiationException, IllegalAccessException, IllegalArgumentException,
            // InvocationTargetException
        } catch (Exception e) {
            throw ReflectionException.getInstance(e.getMessage(), e);
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
