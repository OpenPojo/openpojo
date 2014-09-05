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

package com.openpojo.validation.rule.impl.sampleclasses;

/**
 * @author oshoukry
 */
public final class GetterDoesExistClass {

    // Okay
    public static final String PRIVATE_STATIC_FINAL = "Some String";

    private final String privateFinalString = "Some String";
    private String privateString;
    private static String privateStaticString = "Some String";

    /**
     * @return the privateFinalString
     */
    public String getPrivateFinalString() {
        return privateFinalString;
    }

    /**
     * @return the privateStaticString
     */
    public static String getPrivateStaticString() {
        return privateStaticString;
    }

    /**
     * @return the privateString
     */
    public String getPrivateString() {
        return privateString;
    }

}
