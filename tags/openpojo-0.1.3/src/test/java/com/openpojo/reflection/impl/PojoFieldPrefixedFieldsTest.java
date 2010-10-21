package com.openpojo.reflection.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.reflection.cache.PojoCache;
import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.impl.sampleclasses.AClassWithFieldsPrefixed;
import com.openpojo.reflection.utils.AttributeHelper;
import com.openpojo.validation.affirm.Affirm;

public class PojoFieldPrefixedFieldsTest {

    @Before
    @After
    public void cleanup() {
        PojoCache.clear();
        AttributeHelper.clearRegistery();
    }

    @Test
    public void shouldHaveGettersAndSetters() {
        AttributeHelper.registerFieldPrefix("m");
        PojoClass pojoClass = PojoClassFactory.getPojoClass(AClassWithFieldsPrefixed.class);
        for (PojoField pojoField : pojoClass.getPojoFields()) {
            Affirm.affirmTrue(String.format("Getters / Setters not found on field =[%s]", pojoField), pojoField
                    .hasGetter()
                    && pojoField.hasSetter());
        }
    }

    @Test
    public void shouldNotHaveGettersAndSetters() {
        PojoClass pojoClass = PojoClassFactory.getPojoClass(AClassWithFieldsPrefixed.class);
        for (PojoField pojoField : pojoClass.getPojoFields()) {
            Affirm.affirmFalse(String.format("Getters / Setters not found on field =[%s]", pojoField), pojoField
                    .hasGetter()
                    || pojoField.hasSetter());
        }
    }

    @Test(expected = ReflectionException.class)
    public void shouldFailAttributeName() {
        AttributeHelper.registerFieldPrefix("mName");
        PojoClassFactory.getPojoClass(AClassWithFieldsPrefixed.class);
    }
}
