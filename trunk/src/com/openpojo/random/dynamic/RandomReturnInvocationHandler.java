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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.openpojo.random.RandomFactory;

/**
 * @author oshoukry
 */
public class RandomReturnInvocationHandler implements InvocationHandler {
    private RandomReturnInvocationHandler() {

    }

    public static InvocationHandler getInstance() {
        return Instance.INSTANCE;
    }

    public Object invoke(final Object proxy, final Method method, final Object[] args) {
        return RandomFactory.getRandomValue(method.getReturnType());
    }

    private static class Instance {
        private static final RandomReturnInvocationHandler INSTANCE = new RandomReturnInvocationHandler();
    }
}
