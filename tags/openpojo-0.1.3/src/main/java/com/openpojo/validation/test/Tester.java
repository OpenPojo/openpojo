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
package com.openpojo.validation.test;

import com.openpojo.reflection.PojoClass;

/**
 * This interface is to be implemented by all testers that will check for behavioural concerns on a Pojo.
 *
 * @author oshoukry
 */
public interface Tester {
    /**
     * This method starts the test, and has no return value.
     * Every test should perform its own "Affirm"ions and fail accordingly.
     *
     * @param pojoClass
     *            The PojoClass being tested.
     */
    public void run(PojoClass pojoClass);
}
