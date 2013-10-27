/*
 * Copyright (c) 2010-2013 Osman Shoukry
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

import java.lang.annotation.Annotation;
import java.util.List;

import org.junit.Test;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;
import com.openpojo.reflection.PojoField;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.validation.affirm.Affirm;

public class FilterClassNameTest {

    @Test
    public void testRegExToIncludeClassesNamedTest() {

        // Include classNames that have the word Test in the name.
        final PojoClassFilter filter = new FilterClassName("\\w*Test\\w*$");

        String[] classNames = new String[] { "EndingWithTest", "TestStartingWith", "WithTestInTheMiddleClass",
                "package.path.TestClass", "package.path.ClassTest", "package.path.ClassTestClass" };
        final PojoClassStub pojoClassStub = new PojoClassStub();

        for (final String className : classNames) {
            pojoClassStub.className = className;
            Affirm.affirmTrue(String.format("[%s] didn't include class [%s]!!", filter, className),
                              filter.include(pojoClassStub));
        }

        classNames = new String[] { "package.Test.Class", "TestClass.package" };
        for (final String className : classNames) {
            pojoClassStub.className = className;
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
        final PojoClassStub pojoClassStub = new PojoClassStub();

        for (final String className : classNames) {
            pojoClassStub.className = className;
            Affirm.affirmFalse(String.format("[%s] didn't exclude class [%s]!!", filter, className),
                               filter.include(pojoClassStub));
        }

    }

    private class PojoClassStub implements PojoClass {
        private String className;

        public String getName() {
            return className;
        }

        public List<? extends Annotation> getAnnotations() {
            throw new IllegalStateException("UnImplemented!!");
        }

        public <T extends Annotation> T getAnnotation(final Class<T> annotationClass) {
            throw new IllegalStateException("UnImplemented!!");
        }

        public boolean isInterface() {
            throw new IllegalStateException("UnImplemented!!");
        }

        public boolean isAbstract() {
            throw new IllegalStateException("UnImplemented!!");
        }

        public boolean isConcrete() {
            throw new IllegalStateException("UnImplemented!!");
        }

        public boolean isEnum() {
            throw new IllegalStateException("UnImplemented!!");
        }

        public boolean isArray() {
            throw new IllegalStateException("UnImplemented!!");
        }

        public boolean isFinal() {
            throw new IllegalStateException("UnImplemented!!");
        }

        public List<PojoField> getPojoFields() {
            throw new IllegalStateException("UnImplemented!!");
        }

        public List<PojoMethod> getPojoMethods() {
            throw new IllegalStateException("UnImplemented!!");
        }

        public List<PojoMethod> getPojoConstructors() {
            throw new IllegalStateException("UnImplemented!!");
        }

        public boolean extendz(final Class<?> type) {
            throw new IllegalStateException("UnImplemented!!");
        }

        public PojoClass getSuperClass() {
            throw new IllegalStateException("UnImplemented!!");
        }

        public List<PojoClass> getInterfaces() {
            throw new IllegalStateException("UnImplemented!!");
        }

        public Class<?> getClazz() {
            throw new IllegalStateException("UnImplemented!!");
        }

        public boolean isNestedClass() {
            throw new IllegalStateException("UnImplemented!!");
        }

        public void copy(final Object from, final Object to) {
            throw new IllegalStateException("UnImplemented!!");
        }

        public String toString(final Object instance) {
            throw new IllegalStateException("UnImplemented!!");
        }

        public String getSourcePath() {
            throw new IllegalStateException("UnImplemented!!");
        }

    }
}
