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

package com.openpojo.random.impl;

import com.openpojo.random.RandomGenerator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class VoidRandomGeneratorTest {
  RandomGenerator voidRandomGenerator;
  Class<?> voidClass = void.class;
  private static final int EXPECTED_COUNT = 1;

  @Before
  public void setUp() throws Exception {
    voidRandomGenerator = VoidRandomGenerator.getInstance();
  }

  @Test
  public void testGetInstance() {
    Assert.assertNotNull("Null object returned for VoidRandomGenerator.getInstance()", voidRandomGenerator);
    Assert.assertTrue(String.format("Incorrect type returned=[%s] for requested type=[%s]",
            voidRandomGenerator.getClass(), VoidRandomGenerator.class),
        voidRandomGenerator instanceof VoidRandomGenerator);
  }

  @Test
  public void testDoGenerate() {
    Assert.assertNull("Non null returned when invoking void random generation", voidRandomGenerator.doGenerate(voidClass));
  }

  @Test
  public void testGetTypes() {
    CommonCode.testGetType(voidRandomGenerator, voidClass, EXPECTED_COUNT);

  }

}
