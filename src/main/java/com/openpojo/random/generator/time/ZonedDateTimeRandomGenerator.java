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

package com.openpojo.random.generator.time;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import com.openpojo.random.RandomFactory;
import com.openpojo.random.RandomGenerator;
import com.openpojo.reflection.java.load.ClassUtil;

/**
 * This class generates random ZonedDateTime.
 * The random generation period is +/- 100 years
 * @author oshoukry
 */
public class ZonedDateTimeRandomGenerator implements RandomGenerator {

  private static final String TYPE = "java.time.ZonedDateTime";
  private static final ZonedDateTimeRandomGenerator INSTANCE = new ZonedDateTimeRandomGenerator();
  private static final Long SECONDS_IN_A_DAY = 60L * 60L * 24L;
  private static final Long ONE_HUNDRED_YEARS_IN_SECS = SECONDS_IN_A_DAY * (365L + 4L) * 100L;
  private static final Random RANDOM = new Random(System.currentTimeMillis());

  public static ZonedDateTimeRandomGenerator getInstance() {
    return INSTANCE;
  }

  public Collection<Class<?>> getTypes() {
    List<Class<?>> types = new ArrayList<Class<?>>();
    if (ClassUtil.isClassLoaded(TYPE))
      types.add(ClassUtil.loadClass(TYPE));
    return types;
  }

  @SuppressWarnings({ "Since15", "ConstantConditions" })
  public Object doGenerate(Class<?> type) {
    java.time.ZonedDateTime zonedDateTime = java.time.ZonedDateTime.now();

    Long offset = RANDOM.nextLong() % ONE_HUNDRED_YEARS_IN_SECS;
    java.time.ZonedDateTime randomizedDateTime = zonedDateTime.plusSeconds(offset);

    java.time.ZoneId randomZone = RandomFactory.getRandomValue(java.time.ZoneId.class);

    return java.time.ZonedDateTime.ofInstant(randomizedDateTime.toInstant(), randomZone);
  }

  private ZonedDateTimeRandomGenerator() {
  }
}