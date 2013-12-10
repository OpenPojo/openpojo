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

package com.openpojo.business.identity;

import java.util.LinkedList;

import com.openpojo.business.exception.BusinessException;
import com.openpojo.business.identity.impl.DefaultIdentityHandler;

/**
 * This is the Default factory that holds the default implementation of {@link IdentityEvaluator},
 * {@link HashCodeGenerator} and {@link BusinessValidator}. <br>
 * This IdentityFactory can route calls to other IdentityHandlers based on registration.
 *
 * @author oshoukry
 */
public final class IdentityFactory {

    private static volatile LinkedList<IdentityHandler> identityHandlers = new LinkedList<IdentityHandler>();

    static {
        registerIdentityHandler(DefaultIdentityHandler.getInstance());
    }

    /**
     * This method looks through the list of registered IdentityHandler(s) and returns the first one that returns true
     * on handlerFor(Object) call.
     *
     * @return the identityEvaluator
     */
    public static IdentityHandler getIdentityHandler(final Object object) {
        for (IdentityHandler identityHandler : identityHandlers) {
            if (identityHandler.handlerFor(object)) {
                return identityHandler;
            }
        }
        throw BusinessException.getInstance(String.format(
                "Invalid IdentityFactory state, no IdentityHandler found for object [%s]", object));
    }

    /**
     * This method registers an IdentityHandler to the list of possible IdentityHandlers.
     * An IdentityHandler will not be registered more than once.
     *
     * @param identityHandler
     *            The identityHandler to register.
     */
    public static void registerIdentityHandler(final IdentityHandler identityHandler) {
        if (identityHandler == null) {
            throw new IllegalArgumentException("Attempt to register null IdentityHandler");
        }
        LinkedList<IdentityHandler> newList = duplicateIdentityHandlers();
        newList.remove(identityHandler);
        newList.addFirst(identityHandler);
        identityHandlers = newList;

    }

    /**
     * This method unregisters an IdentityHandler.
     *
     * @param identityHandler
     *            The identityHandler to unregister.
     */
    public static void unregisterIdentityHandler(final IdentityHandler identityHandler) {
        LinkedList<IdentityHandler> newList = duplicateIdentityHandlers();
        if (newList.remove(identityHandler)){
            identityHandlers = newList;
        }
    }

    @SuppressWarnings("unchecked")
    private static LinkedList<IdentityHandler> duplicateIdentityHandlers() {
        return (LinkedList <IdentityHandler>) identityHandlers.clone();

    }
}
