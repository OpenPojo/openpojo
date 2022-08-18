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

package com.openpojo.validation.test;

import com.openpojo.reflection.PojoClass;

/**
 * This interface is to be implemented by all testers that will check for behavioural concerns on a Pojo.
 *
 * @author oshoukry
 */
public interface Tester {
  /**
   * This method starts the test, and has no return value.
   * Every test should perform its own "Affirm"ions and fail accordingly.
   *
   * @param pojoClass
   *     The PojoClass being tested.
   */
  void run(PojoClass pojoClass);
}
