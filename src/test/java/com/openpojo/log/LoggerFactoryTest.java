/*
 * Copyright (c) 2010-2015 Osman Shoukry
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License or any
 *    later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.openpojo.log;

import com.openpojo.log.impl.Log4JLogger;
import com.openpojo.validation.affirm.Affirm;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class LoggerFactoryTest {
  private Class<? extends Logger> defaultLoggerClass = Log4JLogger.class;

  private final String[] supportedLoggers = {
      "com.openpojo.log.impl.SLF4JLogger",
      "com.openpojo.log.impl.Log4JLogger",
      "com.openpojo.log.impl.JavaLogger" };

  @Before
  public final void setUp() {
    LoggerFactory.setActiveLogger(defaultLoggerClass);
  }

  @Test
  public final void shouldReturnDefaultLoggerClassByClass() {
    final Logger log = LoggerFactory.getLogger(LoggerFactoryTest.class);
    Assert.assertNotNull(log);
    Assert.assertEquals(defaultLoggerClass.getName(), log.getClass().getName());
  }

  @Test
  public final void shouldReturnDefaultLoggerClassByCategory() {
    final Logger log = LoggerFactory.getLogger("TestLogger");
    Assert.assertNotNull(log);
    Assert.assertEquals(defaultLoggerClass.getName(), log.getClass().getName());
  }

  @Test
  public final void shouldReturnDefaultCategoryByClass() {
    Logger log = LoggerFactory.getLogger((Class<?>) null);
    Affirm.affirmNotNull("Null logger returned when requested with null class", log);
    log = LoggerFactory.getLogger((String) null);
    Affirm.affirmNotNull("Null logger returned when requested with null category", log);
  }


  @Test
  public final void ensureSupportedLoggersAndOrder() {
    Affirm.affirmEquals("Supported loggers added/removed?", 3, LoggerFactory.SUPPORTED_LOGGERS.length);

    String message = "Changed supported loggers order? expected position[%s] to be [%s]";

    for (int position = 0; position < supportedLoggers.length; position++) {
      Affirm.affirmEquals(String.format(message, position, supportedLoggers[position]), supportedLoggers[position],
          LoggerFactory.SUPPORTED_LOGGERS[position]);
    }
  }

  @Test
  @SuppressWarnings("unchecked")
  public final void shouldReturnSetLogger() throws ClassNotFoundException {
    for (String logger : supportedLoggers) {
      Class<Logger> loggerClass = (Class<Logger>) Class.forName(logger);
      LoggerFactory.setActiveLogger(loggerClass);
      Affirm.affirmTrue(String.format("Expected LoggerFactory to be set to [%s] but was [%s]", loggerClass,
          LoggerFactory.getLogger((String) null)), LoggerFactory.getLogger((String) null).getClass().equals(loggerClass));
    }
  }
}
