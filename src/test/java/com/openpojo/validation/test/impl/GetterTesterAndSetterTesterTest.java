/*
 * Copyright (c) 2010-2015 Osman Shoukry
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License or any
 *    later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.openpojo.validation.test.impl;

import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.test.Tester;
import com.openpojo.validation.test.impl.sampleclasses.Good_AnAbstractClassWithAbstractSetterGetter;
import org.junit.Assert;
import org.junit.Test;

public class GetterTesterAndSetterTesterTest {
    private static final String TESTPACKAGE = GetterTesterAndSetterTesterTest.class.getPackage().getName() + ".sampleclasses";

    public List<PojoClass> getGoodPojoClasses() {
        return PojoClassFactory.getPojoClasses(TESTPACKAGE, new PojoClassFilter() {
            public boolean include(PojoClass pojoClass) {
                return pojoClass.getClazz().getSimpleName().startsWith("Good_");
            }
        });
    }

    public List<PojoClass> getBadPojoClasses() {
        return PojoClassFactory.getPojoClassesRecursively(TESTPACKAGE, new PojoClassFilter() {

            public boolean include(final PojoClass pojoClass) {
                return pojoClass.getClazz().getSimpleName().startsWith("Bad_");
            }
        });
    }

    @Test
    public void shouldPassSetterTest() {
        List<PojoClass> goodPojoClasses = getGoodPojoClasses();

        Assert.assertEquals("Classes added/removed?", 3, goodPojoClasses.size());
        for (final PojoClass pojoClass : goodPojoClasses) {
            invokeRun(pojoClass, new SetterTester());
            invokeRun(pojoClass, new GetterTester());
        }
    }

    @Test
    public void setterShouldSkipTestingAbstractMethods() {
        PojoClass pojoClass = PojoClassFactory.getPojoClass(Good_AnAbstractClassWithAbstractSetterGetter.class);
        invokeRun(pojoClass, new SetterTester());
    }

    @Test
    public void shouldFailSetterTest() {
        List<PojoClass> badPojoClasses = getBadPojoClasses();
        Assert.assertEquals("Classes added/removed?", 1, badPojoClasses.size());
        for (final PojoClass pojoClass : badPojoClasses) {
            try {
                invokeRun(pojoClass, new SetterTester());
                Assert.fail("Should not have passed");
            } catch (AssertionError ignored) {

            }
        }
    }

    @Test(expected = AssertionError.class)
    public void shouldFailGetterTest() {
        for (final PojoClass pojoClass : getBadPojoClasses()) {
            invokeRun(pojoClass, new GetterTester());
        }
    }

    private void invokeRun(final PojoClass classToTest, final Tester tester) {
        tester.run(classToTest);
    }

}
