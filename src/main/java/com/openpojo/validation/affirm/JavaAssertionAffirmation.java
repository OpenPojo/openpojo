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

import com.openpojo.business.BusinessIdentity;
import com.openpojo.log.utils.MessageFormatter;

/**
 * @author oshoukry
 */
public class JavaAssertionAffirmation extends AbstractAffirmation implements Affirmation {

  private JavaAssertionAffirmation() {
  }

  public void fail(final String message) {
    throw new AssertionError(message == null ? "" : message);
  }

  public void affirmTrue(final String message, final boolean condition) {
    if (!condition) {
      fail(message);
    }
  }

  public void affirmFalse(final String message, final boolean condition) {
    if (condition) {
      fail(message);
    }
  }

  public void affirmNotNull(final String message, final Object object) {
    if (object == null) {
      fail(message);
    }
  }

  public void affirmNull(final String message, final Object object) {
    if (object != null) {
      fail(message);
    }
  }

  public void affirmEquals(final String message, final Object expected, final Object actual) {
    if (objectPointersAreTheSame(expected, actual))
      return;

    if (isArray(expected)) {
      affirmArrayEquals(message, expected, actual);
      return;
    }

    if (expected != null && expected.equals(actual))
      return;

    fail(MessageFormatter.format("{0} expected <{1}> but was <{2}>", message, expected, actual));
  }

  public void affirmSame(String message, Object first, Object second) {
    if (first != second)
      fail(message);
  }

  @Override
  public String toString() {
    return BusinessIdentity.toString(this);
  }

}
