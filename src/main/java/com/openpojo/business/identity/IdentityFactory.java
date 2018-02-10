/*
 * Copyright (c) 2010-2018 Osman Shoukry
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
    identityHandlers.add(DefaultIdentityHandler.getInstance());
  }

  /**
   * This method looks through the list of registered IdentityHandler(s) and returns the first one that returns true
   * on handlerFor(Object) call.
   *
   * @param object
   *     the object to use for looking up the appropriate handler.
   * @return the identityEvaluator
   */
  public static IdentityHandler getIdentityHandler(final Object object) {
    for (IdentityHandler identityHandler : identityHandlers) {
      if (identityHandler.handlerFor(object)) {
        return identityHandler;
      }
    }
    throw BusinessException.getInstance(
        String.format("Invalid IdentityFactory state, no IdentityHandler found for object [%s]", object));
  }

  /**
   * This method registers an IdentityHandler to the list of possible IdentityHandlers.
   * An IdentityHandler will not be registered more than once.
   *
   * @param identityHandler
   *     The identityHandler to register.
   */
  public static synchronized void registerIdentityHandler(final IdentityHandler identityHandler) {
    if (identityHandler == null)
      throw new IllegalArgumentException("Attempt to register null IdentityHandler");

    LinkedList<IdentityHandler> newList = duplicateIdentityHandlers();
    newList.remove(identityHandler);
    newList.addFirst(identityHandler);
    identityHandlers = newList;
  }

  /**
   * This method unregisters an IdentityHandler.
   *
   * @param identityHandler
   *     The identityHandler to unregister.
   */
  public static synchronized void unregisterIdentityHandler(final IdentityHandler identityHandler) {
    LinkedList<IdentityHandler> newList = duplicateIdentityHandlers();
    if (newList.remove(identityHandler))
      identityHandlers = newList;
  }

  @SuppressWarnings("unchecked")
  private static LinkedList<IdentityHandler> duplicateIdentityHandlers() {
    return (LinkedList<IdentityHandler>) identityHandlers.clone();

  }

  private IdentityFactory() {
    throw new UnsupportedOperationException(IdentityFactory.class.getName() +  " should not be constructed!");
  }
}
