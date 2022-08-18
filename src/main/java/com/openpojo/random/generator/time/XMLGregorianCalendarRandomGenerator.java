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

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.openpojo.random.RandomFactory;
import com.openpojo.random.RandomGenerator;
import com.openpojo.random.exception.RandomGeneratorException;

/**
 * @author oshoukry
 */
public class XMLGregorianCalendarRandomGenerator implements RandomGenerator {
  private static final Class<?>[] TYPES = new Class<?>[] { XMLGregorianCalendar.class };
  private static final XMLGregorianCalendarRandomGenerator INSTANCE = new XMLGregorianCalendarRandomGenerator();

  private XMLGregorianCalendarRandomGenerator() {}

  public static RandomGenerator getInstance() {
    return INSTANCE;
  }

  public Collection<Class<?>> getTypes() {
    return Arrays.asList(TYPES);
  }

  public Object doGenerate(final Class<?> type) {
    GregorianCalendar gregorianCalendar = new GregorianCalendar();

    //noinspection ConstantConditions
    gregorianCalendar.setTime(RandomFactory.getRandomValue(Timestamp.class));

    try {
      return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
    } catch (DatatypeConfigurationException e) {
      throw RandomGeneratorException.getInstance(e.getMessage(), e);
    }
  }

}
