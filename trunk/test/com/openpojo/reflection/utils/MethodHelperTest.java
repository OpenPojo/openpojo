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
package com.openpojo.reflection.utils;

import java.lang.reflect.Method;
import java.util.List;

import org.junit.Test;

import com.openpojo.validation.affirm.Affirm;

/**
 * Test MethodHelper.
 *
 * @author oshoukry
 */
public class MethodHelperTest {

    @Test
    public final void testGetDeclaredMethods() {
        List<Method> methods = MethodHelper.getDeclaredMethods(MethodTestData.class);
        Affirm.affirmEquals(String.format("Methods added/removed from class=[%s]?",
                MethodTestData.class), 5, methods.size());
    }

    /**
     * Dummy class used for testing.
     */
    private static class MethodTestData {

        @SuppressWarnings("unused")
        private static final Object privateStaticFinalObjectMethod() {
            return null;
        }

        @SuppressWarnings("unused")
        public static void publicStaticVoidMethod() {
        }

        @SuppressWarnings("unused")
        public void publicVoidMethod() {
        }

        @SuppressWarnings("unused")
        private void privateVoidMethod() {
        }

        @SuppressWarnings("unused")
        protected void protectedVoidMethod() {
        }
    }

}
