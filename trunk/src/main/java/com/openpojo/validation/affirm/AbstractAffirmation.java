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

package com.openpojo.validation.affirm;

import java.lang.reflect.Array;

/**
 * @author oshoukry
 */
public abstract class AbstractAffirmation implements Affirmation {

    public void affirmArrayEquals(String message, Object expected, Object actual) {
        Integer expectedLength = Array.getLength(expected);
        affirmEquals(message + " : Arrays are not the same length", expectedLength, actual == null ? null : Array.getLength(actual));

        for (int i = 0; i < expectedLength; i++) {
            Object expectedArrayElement = Array.get(expected, i);
            Object actualArrayElement = Array.get(actual, i);
            try {
                affirmEquals(message, actualArrayElement, expectedArrayElement);
            } catch (AssertionError ae) {
                fail("Array element mismatch value at index [" + i + "] :" + ae.getMessage());
            }
        }
    }

    public boolean isArray(Object object) {
        return object != null && object.getClass().isArray();
    }

    public boolean objectPointersAreTheSame(Object expected, Object actual) {
        return (expected == null && actual == null) || (expected == actual);
    }
}
