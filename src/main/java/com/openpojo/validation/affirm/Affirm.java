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


import java.util.Collection;

/**
 * This class acts as a facade to JUnit Assert-ions.<br>
 * Written to mainly facilitate:<br>
 * 1. To enforce how the Assert is to be used.<br>
 * 2. To simplify available Assert calls.<br>
 * <br>
 * In a nutshell all Affirmations must include a message describing the Affirm being performed.
 *
 * @author oshoukry
 */
public class Affirm {

  private static Affirmation getAffirmation() {
    return AffirmationFactory.getInstance().getAffirmation();
  }

  /**
   * Fail with a message.
   *
   * @param message
   *     The message describing the failure.
   */
  public static void fail(final String message) {
    getAffirmation().fail(message);
  }

  /**
   * This method will only affirm failure if the condition is false.
   *
   * @param message
   *     The message describing the affirmation being performed.
   * @param condition
   *     The condition being affirmed.
   */
  public static void affirmTrue(final String message, final boolean condition) {
    getAffirmation().affirmTrue(message, condition);
  }

  public static void affirmFalse(final String message, final boolean condition) {
    getAffirmation().affirmFalse(message, condition);
  }

  public static void affirmNotNull(final String message, final Object object) {
    getAffirmation().affirmNotNull(message, object);
  }

  public static void affirmNull(final String message, final Object object) {
    getAffirmation().affirmNull(message, object);
  }

  public static void affirmEquals(final String message, final Object first, final Object second) {
    getAffirmation().affirmEquals(message, first, second);
  }

  public static void affirmSame(final String message, final Object first, final Object second) {
    getAffirmation().affirmSame(message, first, second);
  }

  public static void affirmContains(final String message, final Object expected, final Collection<?> collection) {
    for (Object entry : collection) {
      if (expected == null) {
        if (entry == null) {
          return;
        }
      } else {
        if (expected.equals(entry)) {
          return;
        }
      }
    }
    Affirm.fail(message + ":expected value [" + expected + "] not found");
  }

  private Affirm() {
    throw new UnsupportedOperationException(Affirm.class.getName() +  " should not be constructed!");
  }

}
