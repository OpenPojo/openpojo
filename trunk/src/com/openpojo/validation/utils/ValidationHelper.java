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
package com.openpojo.validation.utils;

import com.openpojo.reflection.PojoField;

/**
 * This Validation helper utility class will carry the collection of common
 * tasks performed by various validation tasks.
 * 
 * @author oshoukry
 */
public final class ValidationHelper {
    /**
     * Return true if the PojoField is marked as static and is final.
     * 
     * @param fieldEntry
     *            The field to test.
     * @return
     *         True if the field was declared static final, false otherwise.
     */
    public static boolean isStaticFinal(PojoField fieldEntry) {
        return fieldEntry.isFinal() && fieldEntry.isStatic();
    }

}
