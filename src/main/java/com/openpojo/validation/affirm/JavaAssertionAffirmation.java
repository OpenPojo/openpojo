/*
 * Copyright (c) 2010-2013 Osman Shoukry
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

import com.openpojo.business.BusinessIdentity;
import com.openpojo.log.utils.MessageFormatter;

/**
 * @author oshoukry
 */
public class JavaAssertionAffirmation implements Affirmation {

    private JavaAssertionAffirmation() {
    }

    public void fail(final String message) {
        throw new AssertionError(message == null ? "" : message);
    }

    public void affirmTrue(final String message, final boolean condition) {
        if (!condition) {
            fail(message);
        }
    }

    public void affirmFalse(final String message, final boolean condition) {
        if (condition) {
            fail(message);
        }
    }

    public void affirmNotNull(final String message, final Object object) {
        if (object == null) {
            fail(message);
        }
    }

    public void affirmNull(final String message, final Object object) {
        if (object != null) {
            fail(message);
        }
    }

    public void affirmEquals(final String message, final Object expected, final Object actual) {
        if (expected == null && actual == null) {
            return;
        }
        if (expected != null && expected.equals(actual)) {
            return;
        }

        if (isArray(expected)) {
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
            return;
        }

        fail(MessageFormatter.format("{0} expected <{1}> but was <{2}>", message, expected, actual));
    }

    private boolean isArray(Object object) {
        return object != null && object.getClass().isArray();
    }

    @Override
    public String toString() {
        return BusinessIdentity.toString(this);
    }

}
