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

package com.openpojo.random.dynamic;

import java.lang.reflect.Proxy;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.impl.PojoClassFactory;

/**
 * This Factory returns a proxy pojo for any provided interface.
 * The Proxy pojo will then trap all method calls to the interface and return random generated values for the calls.
 *
 * @author oshoukry
 */
public class RandomInstanceFromInterfaceRandomGenerator {

    public static RandomInstanceFromInterfaceRandomGenerator getInstance() {
        return Instance.INSTANCE;
    }

    /**
     * This method returns a random instance for a given interface.
     * The instance will return random values upon method invocations.
     *
     * @param <T>
     *            The templated type to generate an instance of.
     * @param clazz
     *            The interface to generate the implementations on.
     * @return
     *         An instance of the interface.
     */
    @SuppressWarnings("unchecked")
    public <T> T doGenerate(final Class<T> clazz) {
        PojoClass pojoClass = PojoClassFactory.getPojoClass(clazz);
        if (!pojoClass.isInterface()) {
            throw ReflectionException.getInstance(String.format(
                    "[%s] is not an interface, can't create a proxy for concrete or abstract types.", pojoClass
                            .getName()));
        }
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[]{ pojoClass
                .getClazz() }, RandomReturnInvocationHandler.getInstance());
    }

    private static class Instance {
        private static final RandomInstanceFromInterfaceRandomGenerator INSTANCE = new RandomInstanceFromInterfaceRandomGenerator();
    }

}
