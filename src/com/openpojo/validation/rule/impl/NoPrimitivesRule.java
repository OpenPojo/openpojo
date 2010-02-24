package com.openpojo.validation.rule.impl;

import junit.framework.Assert;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.validation.rule.Rule;
import com.openpojo.validation.utils.ValidationHelper;

/**
 * This Rule ensures that you aren't using any primitive type fields.
 * This is a best practice, ideally, you want to know the difference between "set/unset" and value.
 * 
 * @author oshoukry
 */
public class NoPrimitivesRule implements Rule {

    @Override
    public void evaluate(PojoClass pojoClass) {
        for (PojoField fieldEntry : pojoClass.getPojoFields()) {
            if (fieldEntry.isPrimitive() && !ValidationHelper.isStaticFinal(fieldEntry)) {
                Assert.fail("Primitive fields (byte, short, int, long, float, double, boolean, char) not allowed "
                        + fieldEntry);
            }
        }
    }

}
