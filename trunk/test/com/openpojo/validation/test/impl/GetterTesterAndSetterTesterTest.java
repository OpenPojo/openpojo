package com.openpojo.validation.test.impl;

import java.util.List;

import org.junit.Test;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.test.Tester;

public class GetterTesterAndSetterTesterTest {
    private static final String TESTPACKAGE = GetterTesterAndSetterTesterTest.class.getPackage().getName() + ".sampleclasses";

    public List<PojoClass> getGoodPojoClasses() {
        return PojoClassFactory.getPojoClassesRecursively(TESTPACKAGE, new PojoClassFilter() {

            public boolean include(final PojoClass pojoClass) {
                return !pojoClass.getClazz().getName().toLowerCase().contains("bad");
            }
        });
    }

    public List<PojoClass> getBadPojoClasses() {
        return PojoClassFactory.getPojoClassesRecursively(TESTPACKAGE, new PojoClassFilter() {

            public boolean include(final PojoClass pojoClass) {
                return pojoClass.getClazz().getName().toLowerCase().contains("bad");
            }
        });
    }

    @Test
    public void shouldPassSetterTest() {
        for (PojoClass pojoClass : getGoodPojoClasses()) {
            invokeRun(pojoClass, new SetterTester());
            invokeRun(pojoClass, new GetterTester());
        }
    }

    @Test(expected = AssertionError.class)
    public void shouldFailSetterTest() {
        for (PojoClass pojoClass : getBadPojoClasses()) {
            invokeRun(pojoClass, new SetterTester());
        }
    }

    @Test(expected = AssertionError.class)
    public void shouldFailGetterTest() {
        for (PojoClass pojoClass : getBadPojoClasses()) {
            invokeRun(pojoClass, new GetterTester());
        }
    }

    private void invokeRun(final PojoClass classToTest, final Tester tester) {
        tester.run(classToTest);
    }

}
