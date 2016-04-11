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

import java.sql.Timestamp;

import com.openpojo.random.RandomGenerator;
import org.junit.Before;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class TimestampRandomGeneratorTest {
  private RandomGenerator timestampRandomGenerator;
  Class<?> timestampClass = Timestamp.class;
  private static final int EXPECTED_TYPES = 1;

  @Before
  public void setUp() {
    timestampRandomGenerator = TimestampRandomGenerator.getInstance();
  }

  /**
   * Test method for {@link com.openpojo.random.impl.TimestampRandomGenerator#doGenerate(java.lang.Class)}.
   */
  @Test
  public final void testDoGenerate() {
    CommonCode.testDoGenerateForClass(timestampRandomGenerator, timestampClass);
  }

  /**
   * Test method for {@link com.openpojo.random.impl.TimestampRandomGenerator#getTypes()}.
   */
  @Test
  public final void testGetTypes() {
    CommonCode.testGetType(timestampRandomGenerator, timestampClass, EXPECTED_TYPES);
  }

}
