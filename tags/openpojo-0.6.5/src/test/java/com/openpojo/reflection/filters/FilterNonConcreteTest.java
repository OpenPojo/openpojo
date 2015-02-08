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

/**
 * @author oshoukry
 */
public class FilterNonConcreteTest extends IdentitiesAreEqual {

    @Test
    public void testInclude() {
        PojoClassFilter pojoClassFilter = new FilterNonConcrete();
        PojoClass stubPojoClass = PojoStubFactory.getStubPojoClass(false);

        Affirm.affirmTrue(String.format("Filter[%s] was supposed to filter OUT non concrete class", pojoClassFilter), stubPojoClass
                .isConcrete() == pojoClassFilter.include(stubPojoClass));

        stubPojoClass = PojoStubFactory.getStubPojoClass(true);
        Affirm.affirmTrue(String.format("Filter[%s] was supposed to filter IN concrete class", pojoClassFilter), stubPojoClass.isConcrete
                () == pojoClassFilter.include(stubPojoClass));

        final StubPojoClassFilter stubPojoClassFilter = new StubPojoClassFilter();
        pojoClassFilter = new FilterChain(new FilterNonConcrete(), stubPojoClassFilter);

        stubPojoClass = PojoStubFactory.getStubPojoClass(true);
        pojoClassFilter.include(stubPojoClass);
        Affirm.affirmTrue(String.format("Filter [%s] didn't invoke next in filter chain", pojoClassFilter), stubPojoClassFilter
                .includeCalled);
    }

    private static class StubPojoClassFilter implements PojoClassFilter {
        private boolean includeCalled = false;

        public boolean include(final PojoClass pojoClass) {
            includeCalled = true;
            return true;
        }
    }

    @Test
    public void shouldBeIdentityEqual() {
        FilterNonConcrete instanceOne = new FilterNonConcrete();
        FilterNonConcrete instanceTwo = new FilterNonConcrete();

        checkEqualityAndHashCode(instanceOne, instanceTwo);
    }

    private static class PojoStubFactory {

        public static PojoClass getStubPojoClass(boolean isConcrete) {
            return (PojoClass) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[] { PojoClass.class },
                    new StubInvocationHandler(isConcrete));
        }
    }

    private static class StubInvocationHandler implements InvocationHandler {
        private boolean isConcrete;

        public StubInvocationHandler(boolean isConcrete) {
            this.isConcrete = isConcrete;
        }

        public Object invoke(Object o, Method method, Object[] objects) throws Throwable {

            if (method.getName().equals("isConcrete")) return isConcrete;

            throw new RuntimeException("UnImplemented!!");
        }
    }
}
