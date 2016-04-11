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

package com.openpojo.reflection.facade;

import java.util.Arrays;

import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.facade.sampleclasses.FirstClass;
import com.openpojo.reflection.facade.sampleclasses.SecondClass;
import com.openpojo.validation.affirm.Affirm;
import org.junit.Test;

public class FacadeFactoryTest {
  Class<?> firstClass = FirstClass.class;
  Class<?> secondClass = SecondClass.class;

  @Test
  public final void shouldGetFirstClass() {
    checkReturnedFacade(firstClass, firstClass.getName(), secondClass.getName());
    checkReturnedFacade(secondClass, secondClass.getName(), firstClass.getName());
  }

  private final void checkReturnedFacade(final Class<?> expected, final String... facades) {
    final PojoClass facade = FacadeFactory.getLoadedFacadePojoClass(facades);
    Affirm.affirmNotNull(String.format("Failed to load from the valid list of facades [%s]?!", Arrays.toString(facades)), facade);

    Affirm.affirmEquals("Wrong facade returned!!", expected, facade.getClazz());

  }

  @Test(expected = ReflectionException.class)
  public void shouldThrowErorr() {
    FacadeFactory.getLoadedFacadePojoClass(new String[] { RandomFactory.getRandomValue(String.class) });
  }
}
