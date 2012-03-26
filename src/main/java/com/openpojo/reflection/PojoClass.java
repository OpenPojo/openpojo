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

import java.util.List;

/**
 * This Interface defines the contract published by PojoClass implementations.
 * The idea is to give an easy and flexible way to work with application POJO classes.
 *
 * @author oshoukry
 */
public interface PojoClass extends PojoElement {

    /**
     * Check if PojoClass wraps an interface.
     *
     * @return true if this PojoClass wraps an interface.
     */
    public boolean isInterface();

    /**
     * Check if PojoClass wraps an abstract.
     *
     * @return true if this PojoClass wraps an abstract class.
     */
    public boolean isAbstract();

    /**
     * Check if PojoClass wraps a concrete (i.e. can be instantiated).
     *
     * @return true if this PojoClass wraps a concrete class.
     */
    public boolean isConcrete();

    /**
     * Check if PojoClass wraps an enum.
     *
     * @return true if this PojoClass wraps an enum type.
     */
    public boolean isEnum();

    /**
     * Check if PojoClass wraps an array.
     *
     * @return true if this PojoClass wraps an array class.
     */
    public boolean isArray();

    /**
     * Check if PojoClass wrapps a final class.
     *
     * @return true if this PojoClass wrapps a final class.
     */
    public boolean isFinal();

    /**
     * Get all PojoFields defined in the class.
     *
     * @return the pojoFields
     */
    public List<PojoField> getPojoFields();

    /**
     * Get all PojoMethods defined in the class;
     */
    public List<PojoMethod> getPojoMethods();

    /**
     * Get all Constructors defined in the class.
     */
    public List<PojoMethod> getPojoConstructors();

    /**
     * Checks to see if class extends/implements a certain type.
     *
     * @param type
     *            The type in question.
     * @return
     *         True if class is subclass or implements an interface, otherwise false.
     */
    public boolean extendz(final Class<?> type);

    /**
     * Return the super class of the class represented by this PojoClass class.
     *
     * @return
     *         PojoClass representing the super class of this class or null if none exist.
     */
    public PojoClass getSuperClass();

    /**
     * Returns a list of all interfaces implemented by the class represented by this PojoClass.
     *
     * @return
     *         The list of interfaces implemented by the class wrapped by this PojoClass.
     */
    public List<PojoClass> getInterfaces();

    /**
     * This method returns the underlying class represented by this instance.
     *
     * @return
     *         The class type wrapped by this PojoClass.
     */
    public Class<?> getClazz();

    /**
     * Checks to see if this class is a nested subclass.
     *
     * @return
     *         True if it is a subclass, false otherwise.
     */
    public boolean isNestedClass();

    /**
     * Copy all contents from one Instance represented by this PojoClass to another.
     *
     * @param from
     *            The Instance to copy from.
     * @param to
     *            The Instance to copy to.
     */
    public void copy(final Object from, final Object to);

    /**
     * This method converts a pojoClass instance's contents to a string.
     * This method can serve as a good delegate for all toString().
     *
     * @param instance
     *            The instance to print the contents out of.
     * @return
     *         String representation of the instance.
     */
    public String toString(Object instance);

    /**
     * This method returns the location from which this class was loaded.
     * Note: Do not use, still in experimental mode.
     *
     * @return
     *         String of the PATH of where this class was loaded from.
     */
    public String getSourcePath();

}
