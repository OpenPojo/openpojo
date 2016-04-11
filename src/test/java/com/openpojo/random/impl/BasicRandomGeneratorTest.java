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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;

import com.openpojo.random.RandomGenerator;
import com.openpojo.validation.affirm.Affirm;
import org.junit.Before;
import org.junit.Test;

import static com.openpojo.random.impl.CommonCode.testDoGenerateForClass;

public class BasicRandomGeneratorTest {
  private RandomGenerator basicRandomGenerator;
  private static final int EXPECTED_BASIC_TYPES = 21;

  @Before
  public void setUp() throws Exception {
    basicRandomGenerator = BasicRandomGenerator.getInstance();
  }

  /**
   * Test random String.
   */
  @Test
  public void testString() {
    testDoGenerateForClass(basicRandomGenerator, String.class);
  }

  /**
   * Test random Date.
   */
  @Test
  public void testDate() {
    testDoGenerateForClass(basicRandomGenerator, Date.class);
  }

  /**
   * Test random Calendar.
   */
  @Test
  public void testCalendar() {
    testDoGenerateForClass(basicRandomGenerator, Calendar.class);
  }

  /**
   * Test random BigDecimal.
   */
  @Test
  public void testBigDecimal() {
    testDoGenerateForClass(basicRandomGenerator, BigDecimal.class);
  }

  /**
   * Test random BigInteger.
   */
  @Test
  public void testBigInteger() {
    testDoGenerateForClass(basicRandomGenerator, BigInteger.class);
  }

  /**
   * Test random Character.
   */
  @Test
  public void testCharacter() {
    // Character.

    @SuppressWarnings("unused")
    char primitiveChar = (Character) basicRandomGenerator.doGenerate(char.class);
    testDoGenerateForClass(basicRandomGenerator, Character.class);
  }

  /**
   * Test random Byte.
   */
  @Test
  public void testByte() {
    // Byte
    @SuppressWarnings("unused")
    byte primitiveByte = (Byte) basicRandomGenerator.doGenerate(byte.class);
    testDoGenerateForClass(basicRandomGenerator, Byte.class);
  }

  /**
   * Test random Short.
   */
  @Test
  public void testShort() {
    // Short
    @SuppressWarnings("unused")
    short primitiveShort = (Short) basicRandomGenerator.doGenerate(short.class);
    testDoGenerateForClass(basicRandomGenerator, Short.class);
  }

  /**
   * Test random Long.
   */
  @Test
  public void testLong() {
    // Long.
    @SuppressWarnings("unused")
    long primitiveLong = (Long) basicRandomGenerator.doGenerate(long.class);
    testDoGenerateForClass(basicRandomGenerator, Long.class);
  }

  /**
   * Test random Double.
   */
  @Test
  public void testDouble() {
    // Double.
    @SuppressWarnings("unused")
    double primitiveDouble = (Double) basicRandomGenerator.doGenerate(double.class);
    testDoGenerateForClass(basicRandomGenerator, Double.class);
  }

  /**
   * Test random Float.
   */
  @Test
  public void testFloat() {
    // Float.
    @SuppressWarnings("unused")
    float primitiveFloat = (Float) basicRandomGenerator.doGenerate(float.class);
    testDoGenerateForClass(basicRandomGenerator, Float.class);
  }

  /**
   * Test random Integer.
   */
  @Test
  public void testInteger() {
    // Integer.
    @SuppressWarnings("unused")
    int primitiveInt = (Integer) basicRandomGenerator.doGenerate(int.class);
    testDoGenerateForClass(basicRandomGenerator, Integer.class);
  }

  /**
   * Test random Boolean.
   */
  @Test
  public void testBoolean() {
    // Boolean.
    @SuppressWarnings("unused")
    boolean primitiveBoolean = (Boolean) basicRandomGenerator.doGenerate(boolean.class);
    testDoGenerateForClass(basicRandomGenerator, Boolean.class);
  }

  /**
   * Test unregisterd type.
   */
  @Test
  public void testUnregisteredType() {
    Affirm.affirmNull(String.format("Request to non-registered type [%s] must return null!!", this.getClass()),
        basicRandomGenerator.doGenerate(this.getClass()));

  }

  /**
   * Test method for {@link com.openpojo.random.impl.BasicRandomGenerator#getTypes()}.
   */
  @Test
  public final void testGetTypes() {
    Affirm.affirmEquals("New Types added/removed to BasicRandomGenerator?", EXPECTED_BASIC_TYPES,
        basicRandomGenerator.getTypes().size());
  }

}
