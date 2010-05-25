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
package com.openpojo.validation.affirm;

import com.openpojo.business.BusinessIdentity;

/**
 *
 * @author oshoukry
 *
 */
public class TestNGAssertAffirmation implements Affirmation {

    static {
        // check that TestNG is loaded
        org.testng.Assert.assertTrue(true);
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

    public void affirmEquals(final String message, final Object first, final Object second) {
        org.testng.Assert.assertEquals(first, second, message);
    }

    @Override
    public String toString() {
        return BusinessIdentity.toString(this);
    }
}
