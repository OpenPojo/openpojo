/**
 * Copyright (C) 2010 Osman Shoukry
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.openpojo.validation.utils;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.reflection.impl.PojoClassFactory;

public class ValidationHelperTest {

    @Test
    public void testIsStaticFinal() {
        PojoClass pojoClass = PojoClassFactory.getPojoClass(StaticFinalData.class);
        List<PojoField> pojoFields = pojoClass.getPojoFields();
        Assert.assertEquals(4, pojoFields.size());
        for (PojoField fieldEntry : pojoFields) {
            if (fieldEntry.getName() == "staticAndNotFinal") {
                Assert.assertTrue("Static and not Final test failed!!", fieldEntry.isStatic() && !fieldEntry.isFinal()
                        && !ValidationHelper.isStaticFinal(fieldEntry));
            }
            if (fieldEntry.getName() == "notStaticAndNotFinal") {
                Assert.assertTrue("Not static OR final test failed!!", !fieldEntry.isStatic() && !fieldEntry.isFinal()
                        && !ValidationHelper.isStaticFinal(fieldEntry));
            }
            if (fieldEntry.getName() == "STATICANDFINAL") {
                Assert.assertTrue("Static AND Final test failed!!!", fieldEntry.isStatic() && fieldEntry.isFinal()
                        && ValidationHelper.isStaticFinal(fieldEntry));
            }
            if (fieldEntry.getName() == "finalAndNotStatic") {
                Assert.assertTrue("Final and not Static test failed!!", !fieldEntry.isStatic() && fieldEntry.isFinal()
                        && !ValidationHelper.isStaticFinal(fieldEntry));
            }
        }
    }

    private static class StaticFinalData {

        @SuppressWarnings("unused")
        private static int staticAndNotFinal = 0;

        @SuppressWarnings("unused")
        private int notStaticAndNotFinal;

        @SuppressWarnings("unused")
        private static final int STATICANDFINAL = 0;

        @SuppressWarnings("unused")
        private final int finalAndNotStatic = 0;
    }
}
