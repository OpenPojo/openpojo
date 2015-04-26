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

package com.openpojo.random.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.impl.ParameterizableFactory;

/**
 * This Class is responsible for creating on the fly random instances from Interfaces.
 * These random instances will return random data on all invocations for their methods.
 * equals, hashCode &amp; toString however will behave consistently with java's default behaviour.
 * 
 * @author oshoukry
 */
public class RandomReturnInvocationHandler implements InvocationHandler {
    private RandomReturnInvocationHandler() {

    }

    public static InvocationHandler getInstance() {
        return Instance.INSTANCE;
    }

    public Object invoke(final Object proxy, final Method method, final Object[] args) {
        if (method.getName().equals("toString")) {
            return objectToString(proxy);
        }

        if (method.getName().equals("equals")) {
            return objectEquals(proxy, args[0]);
        }

        if (method.getName().equals("hashCode")) {
            return objectHashCode(proxy);
        }

        return RandomFactory.getRandomValue(ParameterizableFactory.getInstance(method.getGenericReturnType()));
    }

    private String objectToString(final Object proxy) {
        return proxy.getClass().getName() + '@' + System.identityHashCode(proxy);
    }

    private int objectHashCode(final Object proxy) {
        return System.identityHashCode(proxy);
    }

    private boolean objectEquals(final Object proxy, final Object other) {
        return System.identityHashCode(proxy) == System.identityHashCode(other);
    }

    private static class Instance {
        private static final InvocationHandler INSTANCE = new RandomReturnInvocationHandler();
    }
}
