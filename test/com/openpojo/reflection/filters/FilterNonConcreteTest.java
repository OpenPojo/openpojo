/**
 * Copyright (C) 2010 Osman Shoukry
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
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

/**
 * @author oshoukry
 */
public class FilterNonConcreteTest {

    /**
     * Test method for
     * {@link com.openpojo.reflection.filters.FilterNonConcrete#include(com.openpojo.reflection.PojoClass)}.
     */
    @Test
    public void testInclude() {
        PojoClassFilter pojoClassFilter = new FilterNonConcrete();
        StubPojoClass stubPojoClass = new StubPojoClass();

        stubPojoClass.isConcrete = false;
        Affirm.affirmTrue(String.format("Filter[%s] was supposed to filter OUT non concrete class", pojoClassFilter),
                stubPojoClass.isConcrete() == pojoClassFilter.include(stubPojoClass));

        stubPojoClass.isConcrete = true;
        Affirm.affirmTrue(String.format("Filter[%s] was supposed to filter IN concrete class", pojoClassFilter),
                stubPojoClass.isConcrete() == pojoClassFilter.include(stubPojoClass));

        StubPojoClassFilter stubPojoClassFilter = new StubPojoClassFilter();
        pojoClassFilter = new FilterNonConcrete(stubPojoClassFilter);
        stubPojoClass.isConcrete = true;
        pojoClassFilter.include(stubPojoClass);
        Affirm.affirmTrue(String.format("Filter [%s] didn't invoke next in filter chain", pojoClassFilter),
                stubPojoClassFilter.includeCalled);
    }

    private static class StubPojoClassFilter implements PojoClassFilter {
        private boolean includeCalled = false;

        public boolean include(final PojoClass pojoClass) {
            includeCalled = true;
            return true;
        }

    }

    private static class StubPojoClass implements PojoClass {
        private boolean isConcrete;

        public boolean isConcrete() {
            return isConcrete;
        }

        public void copy(final Object from, final Object to) {
            throw new RuntimeException("Unimplemented!!");
        }

        public boolean extendz(final Class<?> type) {
            throw new RuntimeException("Unimplemented!!");
        }

        public Class<?> getClazz() {
            throw new RuntimeException("Unimplemented!!");
        }

        public List<PojoClass> getInterfaces() {
            throw new RuntimeException("Unimplemented!!");
        }

        public List<PojoMethod> getPojoConstructors() {
            throw new RuntimeException("Unimplemented!!");
        }

        public List<PojoField> getPojoFields() {
            throw new RuntimeException("Unimplemented!!");
        }

        public List<PojoMethod> getPojoMethods() {
            throw new RuntimeException("Unimplemented!!");
        }

        public PojoClass getSuperClass() {
            throw new RuntimeException("Unimplemented!!");
        }

        public boolean isAbstract() {
            throw new RuntimeException("Unimplemented!!");
        }

        public boolean isEnum() {
            throw new RuntimeException("Unimplemented!!");
        }

        public boolean isFinal() {
            throw new RuntimeException("Unimplemented!!");
        }

        public boolean isInterface() {
            throw new RuntimeException("Unimplemented!!");
        }

        public boolean isNestedClass() {
            throw new RuntimeException("Unimplemented!!");
        }

        public String toString(final Object instance) {
            throw new RuntimeException("Unimplemented!!");
        }

        public <T extends Annotation> T getAnnotation(final Class<T> annotationClass) {
            throw new RuntimeException("Unimplemented!!");
        }

        public List<? extends Annotation> getAnnotations() {
            throw new RuntimeException("Unimplemented!!");
        }

        public String getName() {
            throw new RuntimeException("Unimplemented!!");
        }

    }
}