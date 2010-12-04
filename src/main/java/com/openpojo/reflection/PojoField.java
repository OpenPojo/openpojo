/**
 * Copyright (C) 2010 Osman Shoukry
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.openpojo.reflection;

import java.lang.reflect.Type;
import java.util.List;

/**
 * This class encapsulates the meta data definition of a field on a class.
 *
 * @author oshoukry
 */
public interface PojoField extends PojoElement {

    /**
     * This method gets the value of the field.
     *
     * @param instance
     *            The instance to extract the value out of.
     * @return
     *         The value of the field.
     */
    public Object get(final Object instance);

    /**
     * This method sets the value of the field.
     *
     * @param instance
     *            The instance to set the value on.
     * @param value
     *            The value to set it to.
     */
    public void set(final Object instance, final Object value);

    /**
     * Returns true if this field has a getter method.
     *
     * @return
     *         Returns true if the getter is set.
     */
    public boolean hasGetter();

    /**
     * This method will invoke the getter method.
     *
     * @param instance
     *            The instance of the class to invoke the getter on.
     * @return
     *         The value of the field.
     */
    public Object invokeGetter(final Object instance);

    /**
     * Returns true if this field has a setter method.
     *
     * @return
     *         Returns true if the setter is set.
     */
    public boolean hasSetter();

    /**
     * This method will invoke the setter method.
     *
     * @param instance
     *            The instance of the class to invoke the setter on.
     * @param value
     *            The value to set the field to.
     */
    public void invokeSetter(final Object instance, final Object value);

    /**
     * Return the type of field encapsulated.
     *
     * @return
     *         The type of the Field the PojoField is set to.
     */
    public Class<?> getType();

    /**
     * @return
     *         True if the field is of primitive type.
     */
    public boolean isPrimitive();

    /**
     * @return
     *         True if this PojoField is defined as final on the enclosing class.
     */
    public boolean isFinal();

    /**
     * @return
     *         True if this PojoField is defined as static on the enclosing class.
     */
    public boolean isStatic();

    /**
     * @return
     *         True if this PojoField is defined as private on the enclosing class.
     */
    public boolean isPrivate();

    /**
     * @return
     *         True if this PojoField is defined as protected on the enclosing class.
     */
    public boolean isProtected();

    /**
     * @return
     *         True if this PojoField is defined as public on the enclosing class.
     */
    public boolean isPublic();

    /**
     * @return
     *         True if this PojoField is defined as transient on the enclosing class.
     */
    public boolean isTransient();

    /**
     * @return
     *         True if this PojoField is defined as volatile on the enclosing class.
     */
    public boolean isVolatile();

    /**
     * @return
     *         True if this PojoField is defined with parameters (i.e. List<SomeClass>).
     */
    public boolean isParameterized();

    /**
     * Get the generics defined on the field, returns empty list if the field isn't Parameterized.
     *
     * @return
     *         Return a list of Types that are defined parameterized on enclosed field (i.e. will return a list
     *         containing SomeClass for a field that is defined as List<SomeClass>, or [String, Integer] if field is defined
     *         as Map<String, Integer>, ...etc).
     */
    public List<Type> getParameterTypes();

    /**
     * Returns properly formated field=value string from instance.
     *
     * @param instance
     *            The instance to pull the field value off of.
     * @return
     *         A string representation of the field and value.
     */
    public String toString(Object instance);

}
