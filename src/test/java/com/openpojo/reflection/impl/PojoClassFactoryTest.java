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

package com.openpojo.reflection.impl;

import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.utils.dummypackage.Persistable;
import com.openpojo.utils.dummypackage.Person;
import com.openpojo.utils.filter.LoggingPojoClassFilter;
import com.openpojo.validation.affirm.Affirm;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class PojoClassFactoryTest {
  private static final String DUMMY_PACKAGE = "com.openpojo.utils.dummypackage";
  private static final Class<?>[] DUMMY_PACKAGE_CLASSES = new Class<?>[] { Persistable.class, Person.class };

  /**
   * Test that the factory is able to create a pojoClass correctly mapping back to PojoClassFactoryTest.
   */
  @Test
  public void testGetPojoClass() {
    PojoClass pojoClass = PojoClassFactory.getPojoClass(this.getClass());
    Affirm.affirmNotNull(String.format("PojoClassFactory failed to create PojoClass for [%s]", this.getClass()), pojoClass);
    Affirm.affirmEquals(String.format("PojoClassFactory returned invalid meta-definition PojoClass for [%s]", this.getClass()),
        this.getClass(), pojoClass.getClazz());
  }

  /**
   * Test that the factory gets classes from a package correctly.
   */
  @Test
  public void testGetPojoClasses() {
    List<PojoClass> pojoClasses = PojoClassFactory.getPojoClasses(DUMMY_PACKAGE, null);
    Affirm.affirmNotNull(String.format("PojoClassFactory returned null list while getting list for package=[%s]",
        DUMMY_PACKAGE), pojoClasses);
    Affirm.affirmEquals(String.format("Classes added/removed from [%s]?", DUMMY_PACKAGE), 2, pojoClasses.size());
    for (Class<?> clazz : DUMMY_PACKAGE_CLASSES) {
      Affirm.affirmTrue(String.format("Unexpected class=[%s] retrieved from package=[%s], ", clazz, DUMMY_PACKAGE),
          pojoClasses.contains(PojoClassFactory.getPojoClass(clazz)));
    }
  }

  /**
   * Test that the factory utilizes pojo filter.
   */
  @Test
  public void testGetFilteredPojoClasses() {
    LoggingPojoClassFilter loggingPojoClassFilter = new LoggingPojoClassFilter();
    // Set Filter to reject all
    loggingPojoClassFilter.setReturnValue(false);

    List<PojoClass> pojoClasses = PojoClassFactory.getPojoClasses(DUMMY_PACKAGE, loggingPojoClassFilter);
    Affirm.affirmNotNull(String.format(
        "PojoClassFactory returned null list while getting list for package=[%s] using filter=[%s] in filter out all mode!!",
        DUMMY_PACKAGE, loggingPojoClassFilter.getClass()), pojoClasses);

    Affirm.affirmEquals(String.format(
        "PojoClassFactory returned non-empty list for package=[%s] using filter=[%s] in filter out all mode!!",
        DUMMY_PACKAGE, loggingPojoClassFilter.getClass()), 0, pojoClasses.size());

    Affirm.affirmTrue(String.format(
        "Too few number of times filter was triggered while in filter-out all mode!! Classes removed from package=[%s] " +
            "found [%s]? expected at least 2 but was [%s]",
        DUMMY_PACKAGE, loggingPojoClassFilter
        .getPojoClassCallLogs(), loggingPojoClassFilter.getPojoClassCallLogs().size()), 2 <= loggingPojoClassFilter
        .getPojoClassCallLogs().size());

    // Set Filter to allow all
    loggingPojoClassFilter.setReturnValue(true);
    pojoClasses = PojoClassFactory.getPojoClasses(DUMMY_PACKAGE, loggingPojoClassFilter);
    Affirm.affirmNotNull(String.format(
        "PojoClassFactory returned null for package=[%s] using filter=[%s] in allow all mode!!", DUMMY_PACKAGE,
        loggingPojoClassFilter.getClass()), pojoClasses);

    Affirm.affirmEquals(String.format("Wrong number of classes retrieved!! Classes added/removed from package=[%s]?",
        DUMMY_PACKAGE), 2, pojoClasses.size());
  }

  /**
   * Test getting classes from a hierarchy.
   */
  @Test
  public void testGetPojoClassesRecursively() {
    List<PojoClass> pojoClasses = PojoClassFactory.getPojoClassesRecursively(DUMMY_PACKAGE, null);
    Affirm.affirmEquals(pojoClasses.toString(), 4, pojoClasses.size());
  }

  /**
   * Test getting classes by extending type (implementing interface, or extending abstract or another class).
   */
  @Test
  public void testEnumerateClassesByExtendingType() {
    List<PojoClass> pojoClasses = PojoClassFactory.enumerateClassesByExtendingType(DUMMY_PACKAGE, Persistable.class, null);
    Affirm.affirmEquals(pojoClasses.toString(), 3, pojoClasses.size());
  }

}
