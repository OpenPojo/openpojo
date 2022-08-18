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
 * This Interface defines the contract for business validation on an object.
 * Business validation ensures that objects is a valid business object for use.
 *
 * @author oshoukry
 */
public interface BusinessValidator {
  /**
   * This method validates an object to comply with the BusinessKey annotation rules.
   * If an object fails the validation, a BusinessException will be thrown.
   *
   * @param object
   *     The Business Object to be validated.
   */
  void validate(Object object);
}
