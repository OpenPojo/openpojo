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

/**
 * This class acts as a façade to JUnit, to enforce that all calls to simplify & enforce how Assert gets called.
 * 
 * @author oshoukry
 */
public class Affirm {
    public static void fail(String message) {
        org.junit.Assert.fail(message);
    }

    public static void affirmNull(String message, Object object) {
        org.junit.Assert.assertNull(message, object);
    }

    public static void affirmEquals(String message, Object first, Object second) {
        org.junit.Assert.assertEquals(message, first, second);
    }

    public static void affirmTrue(boolean condition) {
        org.junit.Assert.assertTrue(condition);
    }

    public static void affirmFalse(boolean condition) {
        org.junit.Assert.assertFalse(condition);
    }

    public static void affirmNotNull(String message, Object object) {
        org.junit.Assert.assertNotNull(message, object);
    }

}
