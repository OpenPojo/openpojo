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

import java.util.List;

/**
 * This Interface defines the contract published by PojoClass implementations.
 * The idea is to give an easy and flexible way to work with application pojo classes.
 * 
 * @author oshoukry
 */
public interface PojoClass {

    /**
     * Check if Class is a class or interface.
     * 
     * @return true if this PojoClass wrapps an interface.
     */
    boolean isInterface();

    /**
     * Check if Class is complete or abstract.
     * 
     * @return true if this PojoClass wrapps an abstract class.
     */
    boolean isAbstract();

    /**
     * Get all PojoFields defined in the class.
     * 
     * @return the pojoFields
     */
    List<PojoField> getPojoFields();

    /**
     * Returns the fully qualified class name.
     * 
     * @return
     *         The String fully qualified class name.
     */
    String getName();

    /**
     * Checks to see if class extends a certain type.
     * 
     * @param type
     *            The type in question.
     * @return
     *         True if class is subclass or implements an interface, otherwise false.
     */
    boolean extendz(final Class<?> type);

    /**
     * This method returns the underlying class represented by this instance.
     * 
     * @return
     *         The class type wrapped by this PojoClass.
     */
    Class<?> getClazz();

    /**
     * This method creates a new instance.
     * 
     * @return
     *         new instance of clazz.
     */
    Object newInstance();

    /**
     * Checks to see if this class is a nested subclass.
     * 
     * @return
     *         True if it is a subclass, false otherwise.
     */
    boolean isNestedClass();

    /**
     * Copy all contents from one Instance represented by this PojoClass to another.
     * 
     * @param from
     *            The Instance to copy from.
     * @param to
     *            The Instance to copy to.
     */
    void copy(final Object from, final Object to);

}