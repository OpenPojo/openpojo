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

package com.openpojo.random;

import java.util.Collection;

/**
 * Random Generator interface, this is the contract the {@link RandomFactory} expects.
 *
 * @author oshoukry
 */
public interface RandomGenerator {
    /**
     * This method is used to get the types that this RandomGenerator is responsible for.
     *
     * @return
     *         A collection with a list of Types this Random Generator can handle.
     */
    public Collection<Class<?>> getTypes();

    /**
     * Perform random generation.
     *
     * @param type
     *            The type to generate for.
     * @return
     *         A random Object dynamically created.
     */
    public Object doGenerate(Class<?> type);
}
