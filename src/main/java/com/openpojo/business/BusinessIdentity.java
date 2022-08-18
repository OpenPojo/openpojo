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

package com.openpojo.business;

import com.openpojo.business.identity.IdentityFactory;
import com.openpojo.business.utils.BusinessIdentityUtils;

/**
 * This is the entry point and the main class to use for all your business evaluation, hashCode generation,
 * as well as toString rendering.
 *
 * <br>
 * This class was created to simplify the entry into the business package and is ready for use without writing any code.
 * Simply configure your POJOs with {@link com.openpojo.business.annotation.BusinessKey} annotation, and then delegate
 * your equals and hashCode methods.
 * <br>
 * The toString method can be called even if your POJOs aren't configured with a BusinessKey, it is simply an easy way
 * to print in clean normalized way ALL contents of a Java Class.
 * <br>
 *
 * <strong>Note:</strong><br>
 * All calls to areEqual &amp; getHashCode will result in a call to {@link com.openpojo.business.identity
 * .BusinessValidator}.validate
 * (Object).<br>
 * A {@link com.openpojo.business.exception.BusinessException} will be thrown if the objects don't pass business validation.
 *
 * @author oshoukry
 */
public final class BusinessIdentity {

  /**
   * This method is responsibly for handling equality between two objects.
   *
   * @param first
   *     The first object to compare with.
   * @param second
   *     The second object to compare with.
   * @return True if both objects are equal, false otherwise.  if either of those objects is null, equality is false.
   */
  public static boolean areEqual(final Object first, final Object second) {
    if (BusinessIdentityUtils.anyNull(first, second) || !BusinessIdentityUtils.sameClass(first, second)) {
      return false;
    }

    IdentityFactory.getIdentityHandler(first).validate(first);
    IdentityFactory.getIdentityHandler(second).validate(second);
    return IdentityFactory.getIdentityHandler(first).areEqual(first, second);
  }

  /**
   * This method handles generation of the hashCode for a given object.
   *
   * @param object
   *     Object to generate hashCode for.
   * @return Generated hash code.
   */
  public static int getHashCode(final Object object) {
    IdentityFactory.getIdentityHandler(object).validate(object);
    return IdentityFactory.getIdentityHandler(object).generateHashCode(object);
  }

  public static String toString(final Object instance) {
    return IdentityFactory.getIdentityHandler(instance).toString(instance);
  }

  private BusinessIdentity() {
    throw new UnsupportedOperationException(BusinessIdentity.class.getName() + " should not be constructed!");
  }
}
