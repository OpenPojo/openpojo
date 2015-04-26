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

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author oshoukry
 */
public interface Parameterizable {
    /**
     * Return the type encapsulated.
     *
     * @return
     *         The type of the Parameterizable Object.
     */
    Class<?> getType();

    /**
     * @return
     *         True if Parameterized (i.e. List&lt;SomeClass&gt;).
     */
    boolean isParameterized();

    /**
     * Get the generics defined, returns empty list if not Parameterized.
     *
     * @return
     *         Return a list of Type that are defined parameterized (i.e. will return a list
     *         containing SomeClass for a List&lt;SomeClass&gt;, or [String, Integer] for Map&lt;String, Integer&lt;, ...etc).
     */
    List<Type> getParameterTypes();
}