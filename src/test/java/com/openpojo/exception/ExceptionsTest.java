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

package com.openpojo.exception;

import java.util.List;

import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.affirm.Affirm;
import org.junit.Before;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class ExceptionsTest {

  private List<PojoClass> pojoExceptionClasses;
  private static final int EXPECTED_EXCEPTION_COUNT = 5;

  @Before
  public void setUp() {
    pojoExceptionClasses = PojoClassFactory.enumerateClassesByExtendingType("com.openpojo", Throwable.class, null);
  }

  @Test
  public void checkCount() {
    Affirm.affirmEquals(String.format("Classes added/removed that implement Throwable found[%s]?", pojoExceptionClasses),
        EXPECTED_EXCEPTION_COUNT, pojoExceptionClasses.size());
  }

  @Test
  public void ValidateConstructors() {
    for (final PojoClass pojoExceptionClass : pojoExceptionClasses) {

      ensureConstructorsPrivate(pojoExceptionClass.getPojoConstructors());
      testGetInstance(pojoExceptionClass);
    }
  }

  public void ensureConstructorsPrivate(final List<PojoMethod> constructors) {

    for (final PojoMethod constructor : constructors) {
      Affirm.affirmTrue(String.format("Constructor must be private [%s]!!", constructor), constructor.isPrivate());
    }
  }

  public void testGetInstance(final PojoClass pojoExceptionClass) {
    int getInstanceCount = 0;
    final String someMessage = RandomFactory.getRandomValue(String.class);
    final Throwable cause = new Throwable();

    for (final PojoMethod getInstance : pojoExceptionClass.getPojoMethods()) {
      if (getInstance.getName().equals("getInstance")) {
        if (getInstance.getParameterTypes().length == 1) {
          final Throwable instance = (Throwable) getInstance.invoke(null, someMessage);
          Affirm.affirmEquals(String.format("Message changed in Exception[%s] using getInstance(String)?!", pojoExceptionClass)
              , someMessage, instance.getMessage());
        }

        if (getInstance.getParameterTypes().length == 2) {
          final Throwable instance = (Throwable) getInstance.invoke(null, someMessage, cause);
          Affirm.affirmEquals(String.format("Message changed in Exception[%s] using getInstance(String, Throwable)?!",
              pojoExceptionClass), someMessage, instance.getMessage());
          Affirm.affirmEquals(String.format("Cause changed in Exception[%s] using getInstance(String, Throwable)?!",
              pojoExceptionClass), cause, instance.getCause());
        }
        getInstanceCount++;
      }
    }
    Affirm.affirmEquals(String.format("getInstance methods not in line with constructors count for Exception [%s]",
        pojoExceptionClass), pojoExceptionClass.getPojoConstructors().size(), getInstanceCount);
  }
}
