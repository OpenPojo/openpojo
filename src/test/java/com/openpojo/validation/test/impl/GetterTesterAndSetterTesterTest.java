/*
 * Copyright (c) 2010-2012 Osman Shoukry
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation, either version 3 of the License or any
 *   later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
                return !pojoClass.getName().toLowerCase().contains("bad") && pojoClass.isConcrete();
            }
        });
    }

    public List<PojoClass> getBadPojoClasses() {
        return PojoClassFactory.getPojoClassesRecursively(TESTPACKAGE, new PojoClassFilter() {

            public boolean include(final PojoClass pojoClass) {
                return pojoClass.getName().toLowerCase().contains("bad");
            }
        });
    }

    @Test
    public void shouldPassSetterTest() {
        for (final PojoClass pojoClass : getGoodPojoClasses()) {
            invokeRun(pojoClass, new SetterTester());
            invokeRun(pojoClass, new GetterTester());
        }
    }

    @Test(expected = AssertionError.class)
    public void shouldFailSetterTest() {
        for (final PojoClass pojoClass : getBadPojoClasses()) {
            invokeRun(pojoClass, new SetterTester());
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
