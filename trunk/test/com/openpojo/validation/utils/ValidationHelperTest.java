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
            if (fieldEntry.getName() == "staticAndFinal") {
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
        private static final int staticAndFinal = 0;

        @SuppressWarnings("unused")
        private final int finalAndNotStatic = 0;
    }
}
