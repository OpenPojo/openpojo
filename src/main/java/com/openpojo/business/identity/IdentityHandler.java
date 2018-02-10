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

/**
 * @author oshoukry
 */
public interface IdentityHandler {
  /**
   * This method will calculate hash code based on the BusinessKey.
   *
   * @param object
   *     Object to generate hashCode for.
   * @return generated hash code.
   */
  int generateHashCode(Object object);

  /**
   * This method is responsible for evaluating two objects as equal using the identity.
   *
   * @param first
   *     First object in the equality.
   * @param second
   *     Second object in the equality.
   * @return True if both objects are equal.
   */
  boolean areEqual(final Object first, final Object second);

  /**
   * This method is responsible for stringanizing fields in an object.
   * @param object
   *     The Object to be flattened into a string.
   * @return string representation of the object being passed in, "null" if object is null.
   */
  String toString(Object object);

  /**
   * This method validates an object to comply with the BusinessKey annotation rules.
   * If an object fails the validation, a BusinessException will be thrown.
   *
   * @param object
   *     The Business Object to be validated.
   */
  void validate(Object object);

  /**
   * This method returns true or false depending on whether this IdentityHandler handles identity for this object
   *
   * @param object
   *     Object to handle.
   * @return true if all identity calls for this object should go through this IdentityHandler.
   */
  boolean handlerFor(Object object);
}
