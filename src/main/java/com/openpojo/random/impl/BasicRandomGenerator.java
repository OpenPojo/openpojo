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

package com.openpojo.random.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import com.openpojo.random.RandomFactory;
import com.openpojo.random.RandomGenerator;
import com.openpojo.reflection.java.type.Primitives;

/**
 * This is the most basic random generator, it handles all basic java types (20 in total).<br>
 * <strong>Namely:</strong><br>
 * 1. boolean &amp; Boolean<br>
 * 2. int &amp; Integer <br>
 * 3. float &amp; Float <br>
 * 4. double &amp; Double <br>
 * 5. long &amp; Long <br>
 * 6. short &amp; Short <br>
 * 7. byte &amp; Byte <br>
 * 8. char &amp; Character <br>
 * 9. String <br>
 * 10. Date<br>
 * 11. Calendar<br>
 * 12. java.math.BigDecimal <br>
 * 13. java.math.BigInteger <br>
 * <br>
 * You can overwrite any of those types with your own generator and register it with the RandomFactory. <br>
 * <strong>Note:</strong><br>
 * 1. char &amp; Character random generation is <i>currently</i> limited to the following set of
 * characters A-Z, a-z and 0-9. <br>
 * 2. String random generation relies on the Character random generation, all strings are generated as random sequence
 * of characters with length between 1 and 32 inclusive. So If you over-write the Character generator,
 * you will implicitly be modifying the types of character the random characters a random String is made of.
 *
 * @author oshoukry
 */
public final class BasicRandomGenerator implements RandomGenerator {
  private static final Random RANDOM = new Random(new Date().getTime());
  private static final int MAX_RANDOM_STRING_LENGTH = 32;

  private BasicRandomGenerator() {
  }

  public static RandomGenerator getInstance() {
    return Instance.INSTANCE;
  }

  private static final Class<?>[] TYPES = new Class<?>[] {
      boolean.class, Boolean.class,
      int.class, Integer.class,
      float.class, Float.class,
      double.class, Double.class,
      long.class, Long.class,
      short.class, Short.class,
      byte.class, Byte.class,
      char.class, Character.class,
      String.class,
      Date.class,
      Calendar.class,
      BigDecimal.class,
      BigInteger.class };

  private static final char[] CHARACTERS = new char[] {
      'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
      'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
      'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
      'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
      '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
  };

  public Object doGenerate(final Class<?> type) {
    final Class<?> autoBoxedType = Primitives.getInstance().autoBox(type);

    if (autoBoxedType == Boolean.class)
      return RANDOM.nextBoolean();

    if (autoBoxedType == Integer.class)
      return RANDOM.nextInt();

    if (autoBoxedType == BigInteger.class)
      return BigInteger.valueOf(RANDOM.nextLong());

    if (autoBoxedType == Float.class)
      return RANDOM.nextFloat();

    if (autoBoxedType == Double.class)
      return RANDOM.nextDouble();

    if (autoBoxedType == BigDecimal.class)
      return BigDecimal.valueOf(RANDOM.nextDouble());

    if (autoBoxedType == Long.class)
      return RANDOM.nextLong();

    if (autoBoxedType == Short.class)
      return (short) (RANDOM.nextInt(Short.MAX_VALUE + 1) * (RANDOM.nextBoolean() ? 1 : -1));

    if (autoBoxedType == Byte.class) {
      final byte[] randomByte = new byte[1];
      RANDOM.nextBytes(randomByte);
      return randomByte[0];
    }

    if (autoBoxedType == Character.class)
      return CHARACTERS[RANDOM.nextInt(CHARACTERS.length)];

    if (autoBoxedType == String.class) {
      String randomString = "";

      /* prevent zero length string lengths */
      for (int count = 0; count < RANDOM.nextInt(MAX_RANDOM_STRING_LENGTH + 1) + 1; count++) {
        randomString += RandomFactory.getRandomValue(Character.class);
      }
      return randomString;
    }

    if (autoBoxedType == Date.class)
      return new Date(RANDOM.nextLong());

    if (autoBoxedType == Calendar.class) {
      final Calendar calendar = Calendar.getInstance();
      calendar.setTimeInMillis(RANDOM.nextLong());
      return calendar;
    }

    return null;
  }

  public Collection<Class<?>> getTypes() {
    return Arrays.asList(TYPES);
  }

  private static class Instance {
    private static final RandomGenerator INSTANCE = new BasicRandomGenerator();
  }
}
