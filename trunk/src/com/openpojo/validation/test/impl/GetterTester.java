package com.openpojo.validation.test.impl;

import org.junit.Assert;

import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.validation.test.Tester;

import com.openpojo.validation.utils.ValidationHelper;

/**
 * Test the getter and ensure it retrieves from the field being tested.
 * Exception are any fields defined as "static final"
 * 
 * @author oshoukry
 */
public class GetterTester implements Tester {

    @Override
    public void run(PojoClass pojoClass) {
        Object classInstance = null;

        classInstance = pojoClass.newInstance();
        for (PojoField fieldEntry : pojoClass.getPojoFields()) {
            if (!ValidationHelper.isStaticFinal(fieldEntry)) {
                Object value = RandomFactory.getRandomValue(fieldEntry.getType());
                fieldEntry.set(classInstance, value);
                Assert.assertEquals("Getter returned non equal value for field=[" + fieldEntry + "]", value, fieldEntry
                        .invokeGetter(classInstance));
            }
        }
    }
}
