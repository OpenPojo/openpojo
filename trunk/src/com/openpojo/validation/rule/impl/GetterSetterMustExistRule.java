package com.openpojo.validation.rule.impl;

import org.junit.Assert;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.validation.rule.Rule;
import com.openpojo.validation.utils.ValidationHelper;

/**
 * This rule ensures that all Fields have a getter and setter associated with them.
 * Exception are fields defined static final since those are usually constants.
 * 
 * @author oshoukry
 */
public class GetterSetterMustExistRule implements Rule {

    @Override
    public void evaluate(PojoClass pojoClass) {
        for (PojoField fieldEntry : pojoClass.getPojoFields()) {
            if (!ValidationHelper.isStaticFinal(fieldEntry) && !(fieldEntry.hasGetter() && fieldEntry.hasSetter())) {
                Assert.fail(fieldEntry + " is missing a getter/setter");
            }
        }
    }
}
