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

package com.openpojo.reflection.filters;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;
import com.openpojo.validation.affirm.Affirm;
import org.junit.Test;

public class FilterClassNameTest extends IdentitiesAreEqual {

    @Test
    public void testRegExToIncludeClassesNamedTest() {

        // Include classNames that have the word Test in the name.
        final PojoClassFilter filter = new FilterClassName("\\w*Test\\w*$");

        String[] classNames = new String[] { "EndingWithTest", "TestStartingWith", "WithTestInTheMiddleClass",
                "package.path.TestClass", "package.path.ClassTest", "package.path.ClassTestClass" };

        for (final String className : classNames) {
            final PojoClass pojoClassStub = PojoStubFactory.getStubPojoClass(className);
            Affirm.affirmTrue(String.format("[%s] didn't include class [%s]!!", filter, className),
                              filter.include(pojoClassStub));
        }

        classNames = new String[] { "package.Test.Class", "TestClass.package" };
        for (final String className : classNames) {
            final PojoClass pojoClassStub = PojoStubFactory.getStubPojoClass(className);
            Affirm.affirmFalse(String.format("[%s] didn't exclude class [%s]!!", filter, className),
                               filter.include(pojoClassStub));
        }
    }

    @Test
    public void testShouldExcludeFileName() {

        // Include only class names that are in the format of xSample
        final PojoClassFilter filter = new FilterClassName(".+Sample$");

        final String[] classNames = new String[] { "packagepath.packageSample.MyClass", "SampleClass", "Sample",
                "sample", "Somesample", "someSampleClass" };

        for (final String className : classNames) {
            PojoClass pojoClassStub = PojoStubFactory.getStubPojoClass(className);
            Affirm.affirmFalse(String.format("[%s] didn't exclude class [%s]!!", filter, className),
                               filter.include(pojoClassStub));
        }

    }

    @Test
    public void shouldBeIdentityEqual() {
        FilterClassName instanceOne = new FilterClassName("\\*Test");
        FilterClassName instanceTwo = new FilterClassName("\\*Test");

        checkEqualityAndHashCode(instanceOne, instanceTwo);
    }

    @Test
    public void shouldFilterBasedOnPackageName() {

        // Include classNames that have the word Test in the name.
        final PojoClassFilter filter = new FilterClassName("^(?:.*\\.)?model(\\..*)+");

        String[] classNames = new String[] { "com.model.MyClass", "model.pojos.MyClass", "com.model.pojos.MyClass" };

        for (final String className : classNames) {
            final PojoClass pojoClassStub = PojoStubFactory.getStubPojoClass(className);
            Affirm.affirmTrue(String.format("[%s] didn't include class [%s]!!", filter, className),
                    filter.include(pojoClassStub));
        }

        classNames = new String[] { "Testmodel", "com.mypackage.modelClass", "model", "pojomodel.model"};
        for (final String className : classNames) {
            final PojoClass pojoClassStub = PojoStubFactory.getStubPojoClass(className);
            Affirm.affirmFalse(String.format("[%s] didn't exclude class [%s]!!", filter, className),
                    filter.include(pojoClassStub));
        }
    }

    private static class PojoStubFactory {

        public static PojoClass getStubPojoClass(String className) {
            return (PojoClass) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[] { PojoClass.class },
                    new StubInvocationHandler(className));
        }
    }

    private static class StubInvocationHandler implements InvocationHandler {
        private String className;

        public StubInvocationHandler(String className) {
            this.className = className;
        }

        public Object invoke(Object o, Method method, Object[] objects) throws Throwable {

            if (method.getName().equals("getName")) return className;

            throw new RuntimeException("UnImplemented!!");
        }
    }
}
