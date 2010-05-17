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

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class AffirmTest {

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * Test method for {@link com.openpojo.validation.affirm.Affirm#fail(java.lang.String)}.
     */
    @Test
    public void testFail() {
        try {
            Affirm.fail("Expected failure!!");
        } catch (AssertionError e) {
            return;
        }
        Assert.fail("Affirm.fail() failed to fail :)!!");
    }

    /**
     * Test method for {@link com.openpojo.validation.affirm.Affirm#affirmTrue(java.lang.String, boolean)}.
     */
    @Test
    public void testAffirmTrue() {
        Affirm.affirmTrue("Affirm.affirmTrue on true failed!!", true);
        try {
            Affirm.affirmTrue("Affirm.affirmTrue on false passed!!", false);
        } catch (AssertionError e) {
            return;
        }
        Assert.fail("Affirm.affirmTrue call on false passed!!");
    }

    /**
     * Test method for {@link com.openpojo.validation.affirm.Affirm#affirmFalse(java.lang.String, boolean)}.
     */
    @Test
    public void testAffirmFalse() {
        Affirm.affirmFalse("Affirm.affirmFalse on false failed!!", false);
        try {
            Affirm.affirmFalse("Affirm.affirmTrue on true passed!!", true);
        } catch (AssertionError e) {
            return;
        }
        Assert.fail("Affirm.affirmFalse call on true passed!!");
    }

    /**
     * Test method for {@link com.openpojo.validation.affirm.Affirm#affirmNotNull(java.lang.String, java.lang.Object)}.
     */
    @Test
    public void testAffirmNotNull() {
        Affirm.affirmNotNull("Affirm.affirmNotNull on non-null failed!!", new Object());
        try {
            Affirm.affirmNotNull("Affirm.affirmNotNull on null passed!!", null);
        } catch (AssertionError e) {
            return;
        }
        Assert.fail("Affirm.affirmNotNull call on null passed!!");
    }

    /**
     * Test method for {@link com.openpojo.validation.affirm.Affirm#affirmNull(java.lang.String, java.lang.Object)}.
     */
    @Test
    public void testAffirmNull() {
        Affirm.affirmNull("Affirm.affirmNull on null failed!!", null);
        try {
            Affirm.affirmNull("Affirm.affirmNull on non-null passed!!", new Object());
        } catch (AssertionError e) {
            return;
        }
        Assert.fail("Affirm.affirmNull call on non-null passed!!");
    }

    /**
     * Test method for
     * {@link com.openpojo.validation.affirm.Affirm#affirmEquals(java.lang.String, java.lang.Object, java.lang.Object)}.
     */
    @Test
    public void testAffirmEquals() {
        Affirm.affirmEquals("Affirm.affirmEquals on equal objects failed", Integer.valueOf(5), Integer.valueOf(5));
        try {
            Affirm.affirmEquals("Affirm.affirmEquals on non-equal objects failed passed!!", Integer.valueOf(5), Integer.valueOf(6));
        } catch (AssertionError e) {
            return;
        }
        Assert.fail("Affirm.affirmEquals call on non-equal objects passed!!");
    }

}
