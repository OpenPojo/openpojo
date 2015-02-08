/*
 * Copyright (c) 2010-2015 Osman Shoukry
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

package com.openpojo.reflection;

/**
 * This Interface will serve as a way to exclude pojoClasses from the factory.
 * If the filter returns true, the class will be included, otherwise it will be excluded.
 *
 * @author oshoukry
 */
public interface PojoClassFilter {

    /**
     * This Method determines whether to include a PojoClass or not.
     *
     * @param pojoClass
     *            The pojoClass in question.
     * @return
     *         True if it should be included, false otherwise.
     */
    public boolean include(final PojoClass pojoClass);
}
