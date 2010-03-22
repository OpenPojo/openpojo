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
package com.openpojo.reflection;

import java.lang.annotation.Annotation;

/**
 * This class encapsulates the meta data definition of a field on a class.
 *
 * @author oshoukry
 */
public interface PojoField {

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
     * Get the String representation of the field name.
     *
     * @return
     *         Return the name of the field.
     */
    public String getName();

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
    public void inovkeSetter(final Object instance, final Object value);

    /**
     * Return the type of field encapsulated.
     *
     * @return
     *         The type of the Field the PojoField is set to.
     */
    public Class<?> getType();

    /**
     * Get instance of the annotation on the PojoField.
     *
     * @param <T>
     *            Class Type of annotation.
     * @param annotationClass
     *            The annotation class.
     * @return
     *         The definition of this annotation on the PojoField.
     */
    public <T extends Annotation> T getAnnotation(final Class<T> annotationClass);

    /**
     * @return
     *         True if the field is of primitive type.
     */
    public boolean isPrimitive();

    /**
     * @return
     *         True if this PojoField is final-ly defined on the enclosing class.
     */
    public boolean isFinal();

    /**
     * @return
     *         True if this PojoField is static-ly defined on the enclosing class.
     */
    public boolean isStatic();

    /**
     * @return
     *         True if this PojoField is private-ly defined on the enclosing class.
     */
    public boolean isPrivate();

    /**
     * @return
     *         True if this PojoField is protected-ly defined on the enclosing class.
     */
    public boolean isProtected();

    /**
     * @return
     *         True if this PojoField is public-ly defined on the enclosing class.
     */
    public boolean isPublic();
    
    /**
     * Returns properly formated field=value string from instance.
     * @param instance
     *          The instance to pull the field value off of.
     * @return
     *          A string representation of the field and value.
     */
    public String toString(Object instance);
}