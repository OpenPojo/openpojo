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

package com.openpojo.validation.affirm;

import java.lang.reflect.Array;

/**
 * @author oshoukry
 */
public abstract class AbstractAffirmation implements Affirmation {

  public void affirmArrayEquals(String message, Object expected, Object actual) {
    Integer expectedLength = Array.getLength(expected);
    affirmEquals(message + " : Arrays are not the same length", expectedLength, actual == null ? null : Array.getLength(actual));

    for (int i = 0; i < expectedLength; i++) {
      Object expectedArrayElement = Array.get(expected, i);
      Object actualArrayElement = Array.get(actual, i);
      try {
        affirmEquals(message, actualArrayElement, expectedArrayElement);
      } catch (AssertionError ae) {
        fail("Array element mismatch value at index [" + i + "] :" + ae.getMessage());
      }
    }
  }

  public boolean isArray(Object object) {
    return object != null && object.getClass().isArray();
  }

  public boolean objectPointersAreTheSame(Object expected, Object actual) {
    return (expected == null && actual == null) || (expected == actual);
  }
}
