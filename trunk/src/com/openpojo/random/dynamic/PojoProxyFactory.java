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
public class PojoProxyFactory {

    public static Object getRandomReturnProxyPojoForInterface(final Class<?> clazz) {
        PojoClass pojoClass = PojoClassFactory.getPojoClass(clazz);
        if (!pojoClass.isInterface()) {
            throw ReflectionException.getInstance(String.format(
                    "[%s] is not an interface, can't create a proxy for concrete or abstract types.", pojoClass
                            .getName()));
        }
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[]{ pojoClass
                .getClazz() }, RandomReturnInvocationHandler.getInstance());
    }
}
