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
package com.openpojo.validation.rule;

import com.openpojo.reflection.PojoClass;

/**
 * This Class handles applying rules to PojoClass-es for testing.
 * A rule will return True or False weather
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
