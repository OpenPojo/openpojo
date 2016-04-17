/*
 * Copyright (c) 2010-2016 Osman Shoukry
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
import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.java.load.ClassUtil;

import static org.junit.Assert.*;

/**
 * @author oshoukry
 */
public class JUnitAssertAffirmation extends AbstractAffirmation implements Affirmation {
  static {
    if (!ClassUtil.isClassLoaded("org.junit.Assert"))
      throw ReflectionException.getInstance("org.junit.Assert class not found");
  }

  private JUnitAssertAffirmation() {
  }

  public void fail(final String message) {
    org.junit.Assert.fail(message);
  }

  public void affirmTrue(final String message, final boolean condition) {
    assertTrue(message, condition);
  }

  public void affirmFalse(final String message, final boolean condition) {
    assertFalse(message, condition);
  }

  public void affirmNotNull(final String message, final Object object) {
    assertNotNull(message, object);
  }

  public void affirmNull(final String message, final Object object) {
    assertNull(message, object);
  }

  public void affirmEquals(final String message, final Object expected, final Object actual) {
    if (objectPointersAreTheSame(expected, actual))
      return;

    if (isArray(expected)) {
      affirmArrayEquals(message, expected, actual);
    } else {
      assertEquals(message, expected, actual);
    }
  }

  public void affirmSame(String message, Object first, Object second) {
    assertSame(message, first, second);
  }

  @Override
  public String toString() {
    return BusinessIdentity.toString(this);
  }
}
