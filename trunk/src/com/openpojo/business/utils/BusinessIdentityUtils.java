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
package com.openpojo.business.utils;

import com.openpojo.business.exception.BusinessException;
import com.openpojo.reflection.PojoField;

/**
 * @author oshoukry
 */
public class BusinessIdentityUtils {
    /**
     * @param objects
     * @return
     */
    public static boolean anyNull(Object... objects) {
        if (objects == null) {
            throw new BusinessException("objects parameter cannot be null");
        }
        for (Object o : objects) {
            if (o == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param first
     * @param second
     * @return
     */
    public static boolean sameClass(Object first, Object second) {
        return first.getClass() == second.getClass();
    }

    /**
     * @param first
     * @param second
     * @return
     */
    public static boolean sameInstance(Object first, Object second) {
        return first == second;
    }

    /**
     * Perform Equality between two PojoFields' instances.
     * This is different from regular equals implementation that it will return true
     * if both fields are null, as well as it takes into account case based equality for character sequences.
     *
     * @param first
     *            The first instance that contains this PojoField.
     * @param second
     *            The second instance that contains this PojoField.
     * @param ignoreCase
     *            Whether or not to compare ignoring case.
     * @return
     *         True if they are equal or if they are both null.
     */
     public static boolean areEqual(final PojoField pojoField, final Object first, final Object second, final boolean ignoreCase) {
        Object firstField = pojoField.get(first);
        Object secondField = pojoField.get(second);
        if (firstField == null) {
            return secondField == null;
        }
        
        if (secondField == null) {
            return false;
        }

        if (ignoreCase && (firstField instanceof Character || firstField instanceof CharSequence)) {
            return firstField.toString().equalsIgnoreCase(secondField.toString());
        }

        return firstField.equals(secondField);
    }
    
    /**
     * Generate HashCode on field value.
     * @param instance
     *          The instance to pull the field value out of.
     * @param ignoreCase
     *          Whether or not to ignore case while generating hash code.
     * @return
     *          The generated HashCode.
     */
    public static int getHashCode(final PojoField pojoField, final Object instance, final boolean ignoreCase) {
        Object data = pojoField.get(instance);
        if (data == null) {
            return 0;
        }
        if (ignoreCase && (data instanceof Character || data instanceof CharSequence)) {
            return data.toString().toLowerCase().hashCode();
        }
        
        return data.hashCode();
    }
}
