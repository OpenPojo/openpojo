package com.openpojo.validation.rule.impl;

import junit.framework.Assert;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.validation.rule.Rule;

/**
 * This rule ensures that no fields declared with public visibility.
 * 
 * @author oshoukry
 */
public final class NoPublicFieldsRule implements Rule {

    @Override
    public void evaluate(PojoClass pojoClass) {
        for (PojoField fieldEntry : pojoClass.getPojoFields()) {
            if (fieldEntry.isPublic()) {
                Assert.fail("Public fields not allowed " + fieldEntry);
            }
        }
    }

}
