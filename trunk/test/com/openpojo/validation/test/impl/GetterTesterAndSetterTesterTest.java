package com.openpojo.validation.test.impl;

import org.junit.Test;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.test.Tester;
import com.openpojo.validation.test.impl.sampleclasses.ABadGetterAndSetterClass;
import com.openpojo.validation.test.impl.sampleclasses.AGoodGetterAndSetterClass;

public class GetterTesterAndSetterTesterTest {

    @Test
    public void shouldPassSetterTest() {
        invokeRun(AGoodGetterAndSetterClass.class, new SetterTester());
    }

    @Test (expected = AssertionError.class)
    public void shouldFailSetterTest() {
        invokeRun(ABadGetterAndSetterClass.class, new SetterTester());
    }

    @Test
    public void shouldPassGetterTest() {
        invokeRun(AGoodGetterAndSetterClass.class, new GetterTester());
    }

    @Test (expected = AssertionError.class)
    public void shouldFailGetterTest() {
        invokeRun(ABadGetterAndSetterClass.class, new GetterTester());
    }

    private void invokeRun(final Class<?> classToTest, final Tester tester) {
        PojoClass pojoClass = PojoClassFactory.getPojoClass(classToTest);
        tester.run(pojoClass);
    }

}
