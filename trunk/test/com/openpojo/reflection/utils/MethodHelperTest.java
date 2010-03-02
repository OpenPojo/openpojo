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
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * Test MethodHelper.
 * 
 * @author oshouMap<K, V>
 */
public class MethodHelperTest {
    private Map<String, Field> fieldsMap;

    @Before
    public final void setup() {
        fieldsMap = new HashMap<String, Field>();
        for (Field field : FieldHelper.getDeclaredFields(MethodTestData.class)) {
            fieldsMap.put(field.getName(), field);
        }
    }

    @Test
    public final void testNativeBooleanUsingIsSetPair() {
        Method setter = MethodHelper.getSetter(fieldsMap.get("nativeBooleanUsingIs"));
        Assert.assertNotNull(setter);
        Assert.assertEquals(setter.getName(), "setNativeBooleanUsingIs");

        Method getter = MethodHelper.getGetter(fieldsMap.get("nativeBooleanUsingIs"));
        Assert.assertNotNull(getter);
        Assert.assertEquals(getter.getName(), "isNativeBooleanUsingIs");
    }

    @Test
    public final void testNativeBooleanUsingGetSetPair() {
        // Native boolean using is/set pair.
        Method setter = MethodHelper.getSetter(fieldsMap.get("nativeBooleanUsingGet"));
        Assert.assertNotNull(setter);
        Assert.assertEquals(setter.getName(), "setNativeBooleanUsingGet");

        Method getter = MethodHelper.getGetter(fieldsMap.get("nativeBooleanUsingGet"));
        Assert.assertNotNull(getter);
        Assert.assertEquals(getter.getName(), "getNativeBooleanUsingGet");
    }

    @Test
    public final void testObjectBooleanUsingIsSetPair() {
        Method setter = MethodHelper.getSetter(fieldsMap.get("objectBooleanUsingIs"));
        Assert.assertNotNull(setter);
        Assert.assertEquals(setter.getName(), "setObjectBooleanUsingIs");

        Method getter = MethodHelper.getGetter(fieldsMap.get("objectBooleanUsingIs"));
        Assert.assertNotNull(getter);
        Assert.assertEquals(getter.getName(), "isObjectBooleanUsingIs");
    }

    @Test
    public final void testObjectBooleanUsingGetSetPair() {
        // Native boolean using is/set pair.
        Method setter = MethodHelper.getSetter(fieldsMap.get("objectBooleanUsingGet"));
        Assert.assertNotNull(setter);
        Assert.assertEquals(setter.getName(), "setObjectBooleanUsingGet");

        Method getter = MethodHelper.getGetter(fieldsMap.get("objectBooleanUsingGet"));
        Assert.assertNotNull(getter);
        Assert.assertEquals(getter.getName(), "getObjectBooleanUsingGet");
    }

    @Test
    public final void testFullGetSetPair() {
        Method setter = MethodHelper.getSetter(fieldsMap.get("dummyString"));
        Assert.assertNotNull(setter);
        Assert.assertEquals(setter.getName(), "setDummyString");

        Method getter = MethodHelper.getGetter(fieldsMap.get("dummyString"));
        Assert.assertNotNull(getter);
        Assert.assertEquals(getter.getName(), "getDummyString");
    }

    @Test
    public final void testOnlyGet() {
        Method setter = MethodHelper.getSetter(fieldsMap.get("onlyGet"));
        Assert.assertNull(setter);

        Method getter = MethodHelper.getGetter(fieldsMap.get("onlyGet"));
        Assert.assertNotNull(getter);
        Assert.assertEquals(getter.getName(), "getOnlyGet");
    }

    @Test
    public final void testOnlySet() {
        Method setter = MethodHelper.getSetter(fieldsMap.get("onlySet"));
        Assert.assertNotNull(setter);
        Assert.assertEquals(setter.getName(), "setOnlySet");

        Method getter = MethodHelper.getGetter(fieldsMap.get("onlySet"));
        Assert.assertNull(getter);
    }

    @Test
    public final void testNoSetOrGet() {
        Method setter = MethodHelper.getSetter(fieldsMap.get("noSetOrGet"));
        Assert.assertNull(setter);

        Method getter = MethodHelper.getGetter(fieldsMap.get("noSetOrGet"));
        Assert.assertNull(getter);
    }

    /**
     * Dummy class used for testing.
     */
    private static class MethodTestData {
        private boolean nativeBooleanUsingIs;
        private boolean nativeBooleanUsingGet;
        private Boolean objectBooleanUsingIs;
        private Boolean objectBooleanUsingGet;
        private String dummyString;
        private Integer onlyGet;

        @SuppressWarnings("unused")
        private Character onlySet;

        @SuppressWarnings("unused")
        private Byte noSetOrGet;

        @SuppressWarnings("unused")
        public final boolean isNativeBooleanUsingIs() {
            return nativeBooleanUsingIs;
        }

        @SuppressWarnings("unused")
        public final void setNativeBooleanUsingIs(boolean nativeBooleanUsingIs) {
            this.nativeBooleanUsingIs = nativeBooleanUsingIs;
        }

        @SuppressWarnings("unused")
        public final boolean getNativeBooleanUsingGet() {
            return nativeBooleanUsingGet;
        }

        @SuppressWarnings("unused")
        public final void setNativeBooleanUsingGet(boolean nativeBooleanUsingGet) {
            this.nativeBooleanUsingGet = nativeBooleanUsingGet;
        }

        @SuppressWarnings("unused")
        public final Boolean isObjectBooleanUsingIs() {
            return objectBooleanUsingIs;
        }

        @SuppressWarnings("unused")
        public final void setObjectBooleanUsingIs(Boolean objectBooleanUsingIs) {
            this.objectBooleanUsingIs = objectBooleanUsingIs;
        }

        @SuppressWarnings("unused")
        public final Boolean getObjectBooleanUsingGet() {
            return objectBooleanUsingGet;
        }

        @SuppressWarnings("unused")
        public final void setObjectBooleanUsingGet(Boolean objectBooleanUsingGet) {
            this.objectBooleanUsingGet = objectBooleanUsingGet;
        }

        @SuppressWarnings("unused")
        public final String getDummyString() {
            return dummyString;
        }

        @SuppressWarnings("unused")
        public final void setDummyString(String dummyString) {
            this.dummyString = dummyString;
        }

        @SuppressWarnings("unused")
        public final Integer getOnlyGet() {
            return onlyGet;
        }

        @SuppressWarnings("unused")
        public final void setOnlySet(Character onlySet) {
            this.onlySet = onlySet;
        }

    }
}
