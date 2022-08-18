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

package com.openpojo.random.generator.time;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import com.openpojo.random.RandomFactory;
import com.openpojo.random.RandomGenerator;
import com.openpojo.reflection.java.load.ClassUtil;

import static com.openpojo.random.generator.time.util.ReflectionHelper.getMethod;
import static com.openpojo.random.generator.time.util.ReflectionHelper.invokeMethod;

/**
 * This class generates random ZonedDateTime.
 * The random generation period is within +/- 100 years
 *
 * @author oshoukry
 */
public class ZonedDateTimeRandomGenerator implements RandomGenerator {

  private static final String TYPE = "java.time.ZonedDateTime";
  private static final ZonedDateTimeRandomGenerator INSTANCE = new ZonedDateTimeRandomGenerator();
  private static final Random RANDOM = new Random(System.currentTimeMillis());

  private static final String JAVA_TIME_INSTANT_CLASS = "java.time.Instant";
  private static final String JAVA_TIME_ZONEID_CLASS = "java.time.ZoneId";
  private final Class<?> zoneDateTimeClass;
  private final Class<?> javaTimeInstantClass;
  private final Class<?> javaTimeZoneIdClass;

  public static ZonedDateTimeRandomGenerator getInstance() {
    return INSTANCE;
  }

  public Collection<Class<?>> getTypes() {
    List<Class<?>> types = new ArrayList<Class<?>>();
    if (zoneDateTimeClass != null)
      types.add(zoneDateTimeClass);
    return types;
  }

  public Object doGenerate(Class<?> type) {
    Object randomLocalDateTime = randomizeTimeWithin100Years(getLocalDateTime());
    return randomizeTimeZone(randomLocalDateTime);
  }

  private Object getLocalDateTime() {
    Method nowMethod = getMethod(zoneDateTimeClass, "now");
    return invokeMethod(nowMethod, null);
  }

  private Object randomizeTimeWithin100Years(Object localDateTime) {
    Long offset = RANDOM.nextLong() % Duration.ONE_HUNDRED_YEARS_IN_SECS;

    Method plusSecondsMethod = getMethod(zoneDateTimeClass, "plusSeconds", long.class);
    return invokeMethod(plusSecondsMethod, localDateTime, offset);
  }

  private Object randomizeTimeZone(Object randomLocalDateTime) {
    Object randomZone = RandomFactory.getRandomValue(javaTimeZoneIdClass);

    Method ofInstantMethod = getMethod(zoneDateTimeClass, "ofInstant", javaTimeInstantClass, javaTimeZoneIdClass);
    return invokeMethod(ofInstantMethod, randomLocalDateTime, getNowAsInstant(randomLocalDateTime), randomZone);
  }

  private Object getNowAsInstant(Object now) {
    Method toInstantMethod = getMethod(zoneDateTimeClass, "toInstant");
    return invokeMethod(toInstantMethod, now);
  }

  private ZonedDateTimeRandomGenerator() {
    zoneDateTimeClass = ClassUtil.loadClass(TYPE);
    javaTimeInstantClass = ClassUtil.loadClass(JAVA_TIME_INSTANT_CLASS);
    javaTimeZoneIdClass = ClassUtil.loadClass(JAVA_TIME_ZONEID_CLASS);
  }

  private interface Duration {
    long SECONDS_IN_A_DAY = 60 * 60 * 24;
    long DAYS = 365;
    long YEARS = 100;
    long LEAP_YEARS = YEARS / 4;
    long TOTAL_DAYS = DAYS * YEARS + LEAP_YEARS;
    long ONE_HUNDRED_YEARS_IN_SECS = SECONDS_IN_A_DAY * TOTAL_DAYS;
  }
}
