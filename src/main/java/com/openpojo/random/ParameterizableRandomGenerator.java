/*
 * Copyright (c) 2010-2014 Osman Shoukry
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

import com.openpojo.reflection.Parameterizable;

/**
 * This interface handles parameterizable types.
 *
 * @author oshoukry
 */
public interface ParameterizableRandomGenerator extends RandomGenerator {

    /**
     * Perform random generation.
     *
     * @param parameterizedType
     *            The parameterized type to generate for.
     * @return
     *         A random Object dynamically created.
     */
    public Object doGenerate(Parameterizable parameterizedType);

}
