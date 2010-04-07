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
 * This Affirmation factory is responsible to return affirmation implementation.<br>
 *
 * @author oshoukry
 */
public final class AffirmationFactory {

    /**
     * The only affimation implemented so far, so default to that.
     */
    private final Affirmation affirmation = new JUnitAssertAffirmation();

    private AffirmationFactory() {
    }

    public static AffirmationFactory getInstance() {
        return Instance.instance;
    }

    public Affirmation getAffirmation() {
        return affirmation;
    }

    /**
     * This inner static class holds an instance of the holding class.<br>
     * This allows to have lazy instantiation on singleton and not need any
     * synchronization delays.
     *
     * @author oshoukry
     */
    private static class Instance {
        private static AffirmationFactory instance = new AffirmationFactory();
    }
}
