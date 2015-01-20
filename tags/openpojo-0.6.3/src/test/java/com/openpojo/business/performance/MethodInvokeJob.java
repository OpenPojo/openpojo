/*
 * Copyright (c) 2010-2014 Osman Shoukry
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

package com.openpojo.business.performance;

import java.lang.reflect.Method;

/**
* @author oshoukry
*/
class MethodInvokeJob implements Runnable {

    private Object instance;
    private Method methodToInvoke;
    private Object [] parameters;


    public MethodInvokeJob(final Object instance, final Method methodToInvoke, final Object... parameters) {
        this.instance = instance;
        this.methodToInvoke = methodToInvoke;
        this.parameters = parameters;
    }

    public void run() {
        try {
            methodToInvoke.invoke(instance, parameters);
        } catch (Throwable throwable) {
            if (!threadWasInterrupted(throwable)) {
                throw new RuntimeException(throwable);
            }
        }
    }

    private boolean threadWasInterrupted(Throwable throwable) {
        Throwable cause = throwable;
        while (cause.getCause() != null)
            cause = cause.getCause();
        return cause.getClass().equals(InterruptedException.class);
    }
}
