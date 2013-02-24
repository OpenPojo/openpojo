/*
 * Copyright (c) 2010-2012 Osman Shoukry
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation, either version 3 of the License or any
 *   later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.openpojo.validation.rule;

import com.openpojo.reflection.PojoClass;

/**
 * This interface is to be implemented by all Rule.
 * Rules should check for structural concerns on a Pojo (i.e. public fields, nested classes, etc).
 *
 * @author oshoukry
 */
public interface Rule {
    /**
     * @param pojoClass
     *            The PojoClass to evaluate that rule on.
     */
    public void evaluate(PojoClass pojoClass);
}
