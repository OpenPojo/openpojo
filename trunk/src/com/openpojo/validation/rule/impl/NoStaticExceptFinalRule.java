package com.openpojo.validation.rule.impl;

import org.junit.Assert;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.validation.rule.Rule;

/**
 * This rule ensures that there are no static fields unless they are final.
 * Another best practice, using static fields for memory sharing and allowing read/write
 * should be very tightly controlled, and generally don't belong in POJOs or other similar
 * class of data repositories.
 * 
 * @author oshoukry
 */
public class NoStaticExceptFinalRule implements Rule {

    @Override
    public void evaluate(PojoClass pojoClass) {
        for (PojoField fieldEntry : pojoClass.getPojoFields()) {
            if (fieldEntry.isStatic() && !fieldEntry.isFinal()) {
                Assert.fail("Static fields not marked final are not allowed " + fieldEntry);
            }
        }
    }
}
