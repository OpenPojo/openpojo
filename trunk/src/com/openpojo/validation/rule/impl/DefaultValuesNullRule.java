package com.openpojo.validation.rule.impl;

import org.junit.Assert;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.validation.rule.Rule;
import com.openpojo.validation.utils.ValidationHelper;

/**
 * This Rule ensures that all Fields are initialized to Null.
 * Exception to this rule are things defined "static final", or primitive types.
 * 
 * @author oshoukry
 */
public class DefaultValuesNullRule implements Rule {

    @Override
    public void evaluate(PojoClass pojoClass) {
        Object classInstance = null;

        classInstance = pojoClass.newInstance();
        for (PojoField fieldEntry : pojoClass.getPojoFields()) {
            if (!fieldEntry.isPrimitive() && !ValidationHelper.isStaticFinal(fieldEntry)) {
                Assert.assertNull("Expected null value for for field=[" + fieldEntry + "]", fieldEntry
                        .get(classInstance));
            }
        }
    }

}
