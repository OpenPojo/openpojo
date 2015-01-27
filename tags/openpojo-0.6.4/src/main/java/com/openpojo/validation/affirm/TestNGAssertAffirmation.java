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

package com.openpojo.validation.affirm;

import com.openpojo.business.BusinessIdentity;
import com.openpojo.reflection.exception.ReflectionException;

/**
 * @author oshoukry
 */
public class TestNGAssertAffirmation extends AbstractAffirmation implements Affirmation {
    static {
        try {
            Class.forName("org.testng.Assert");
        } catch (ClassNotFoundException e) {
            throw ReflectionException.getInstance("org.testng.Assert class not found");
        }
    }

    private TestNGAssertAffirmation() {
    }

    public void fail(final String message) {
        org.testng.Assert.fail(message);
    }

    public void affirmTrue(final String message, final boolean condition) {
        org.testng.Assert.assertTrue(condition, message);
    }

    public void affirmFalse(final String message, final boolean condition) {
        org.testng.Assert.assertFalse(condition, message);
    }

    public void affirmNotNull(final String message, final Object object) {
        org.testng.Assert.assertNotNull(object, message);
    }

    public void affirmNull(final String message, final Object object) {
        org.testng.Assert.assertNull(object, message);
    }

    public void affirmEquals(final String message, final Object expected, final Object actual) {
        if (objectPointersAreTheSame(expected, actual)) return;

        if (isArray(expected))
            affirmArrayEquals(message, expected, actual);
        else
            org.testng.Assert.assertEquals(actual, expected, message);
    }

    @Override
    public String toString() {
        return BusinessIdentity.toString(this);
    }
}
