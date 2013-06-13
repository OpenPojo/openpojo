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


/**
 * This class acts as a facade to JUnit Assert-ions.<br>
 * Written to mainly facilitate:<br>
 * 1. To enforce how the Assert is to be used.<br>
 * 2. To simplify available Assert calls.<br>
 * <br>
 * In a nutshell all Affirmations must include a message describing the Affirm being performed.
 *
 * @author oshoukry
 */
public abstract class Affirm {

    private static Affirmation getAffirmation() {
        return AffirmationFactory.getInstance().getAffirmation();
    }

    /**
     * Fail with a message.
     *
     * @param message
     *            The message describing the failure.
     */
    public static void fail(final String message) {
        getAffirmation().fail(message);
    }

    /**
     * This method will only affirm failure if the condition is false.
     *
     * @param message
     *            The message describing the affirmation being performed.
     * @param condition
     *            The condition being affirmed.
     */
    public static void affirmTrue(final String message, final boolean condition) {
        getAffirmation().affirmTrue(message, condition);
    }

    public static void affirmFalse(final String message, final boolean condition) {
        getAffirmation().affirmFalse(message, condition);
    }

    public static void affirmNotNull(final String message, final Object object) {
        getAffirmation().affirmNotNull(message, object);
    }

    public static void affirmNull(final String message, final Object object) {
        getAffirmation().affirmNull(message, object);
    }

    public static void affirmEquals(final String message, final Object first, final Object second) {
        getAffirmation().affirmEquals(message, first, second);
    }

}
