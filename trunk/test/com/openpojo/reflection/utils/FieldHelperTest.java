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

import java.lang.reflect.Field;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Test FieldHelper.
 * 
 * @author oshoukry
 */
public class FieldHelperTest {

    /**
     * Test that Fields.getDeclaredFields method returns correct fields from a class.
     */
    @Test
    public final void testGetDeclaredFields() {
        List<Field> fields = FieldHelper.getDeclaredFields(DeclaredFieldsTestData.class);
        Assert.assertEquals(3, fields.size());

        boolean foundString1 = false;
        boolean foundNumber1 = false;
        boolean foundChar1 = false;

        for (Field entry : fields) {
            if (entry.getName() == "string1") {
                Assert.assertEquals(entry.getType(), String.class);
                foundString1 = true;
            }
            if (entry.getName() == "number1") {
                Assert.assertEquals(entry.getType(), int.class);
                foundNumber1 = true;
            }
            if (entry.getName() == "character1") {
                Assert.assertEquals(entry.getType(), char.class);
                foundChar1 = true;
            }
        }
        Assert.assertTrue(foundString1 && foundNumber1 && foundChar1);
    }

    /**
     * Dummy class used for testing declared fields lookup.
     */
    private static class DeclaredFieldsTestData {

        @SuppressWarnings("unused")
        private String string1;

        @SuppressWarnings("unused")
        public int number1;

        @SuppressWarnings("unused")
        protected char character1;
    }
}
