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

package com.openpojo.log;

import com.openpojo.log.common.AbstractLoggerBase;
import com.openpojo.log.common.LoggerMsgTestData;
import com.openpojo.log.common.LoggerVarArgsTestData;
import com.openpojo.log.impl.SLF4JLogger;
import com.openpojo.utils.log.LogHelper;
import com.openpojo.utils.log.MockAppender;
import com.openpojo.utils.log.MockAppenderLog4J;
import com.openpojo.validation.affirm.Affirm;
import org.junit.Before;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class SL4JLoggerTest extends AbstractLoggerBase {
  private static final String LOGCATEGORY = SL4JLoggerTest.class.getName();
  private static final int LOGLEVELS = 5; // TRACE, DEBUG, INFO, WARN, ERROR

  /**
   * This method does the test setup, currently initializes the loggers.
   */
  @Before
  public final void setUp() {
    LoggerFactory.setActiveLogger(SLF4JLogger.class);
  }

  @Override
  public String getCategory() {
    return LOGCATEGORY;
  }

  @Override
  public int getLogLevelsCount() {
    return LOGLEVELS;
  }

  @Override
  public Class<? extends MockAppender> getMockAppender() {
    return MockAppenderLog4J.class;
  }

  @Test
  public void shouldLogInVariousLevels() {
    testWithLogLevel(LogLevel.TRACE);
    testWithLogLevel(LogLevel.DEBUG);
    testWithLogLevel(LogLevel.WARN);
    testWithLogLevel(LogLevel.INFO);
    testWithLogLevel(LogLevel.ERROR);
  }

  /**
   * There is no fatal level in SLF4J, so all messages are routed to ERROR.
   */
  @Test
  public void shouldTestFatal() {
    init();
    for (LoggerVarArgsTestData variance : getVarArgsVariations()) {
      log.fatal(variance.getMessage(), variance.getParams());
      logEvents = LogHelper.getErrorEvents(getMockAppender(), getCategory());

      Affirm.affirmEquals(String.format("Lost [%s] message?", "FATAL"), count + 1, logEvents.size());
      Affirm.affirmEquals("Fatal wired?", 0, LogHelper.getFatalEvents(getMockAppender(), getCategory()).size());
      Affirm.affirmEquals("Message malformed", variance.getExpected(), logEvents.get(count).getMessage());

      count++;
    }

    for (LoggerMsgTestData variance : getMsgVariations()) {
      log.fatal(variance.getMessage());
      logEvents = LogHelper.getErrorEvents(getMockAppender(), getCategory());

      Affirm.affirmEquals(String.format("Lost [%s] message?", "FATAL"), count + 1, logEvents.size());
      Affirm.affirmEquals("Fatal wired?", 0, LogHelper.getFatalEvents(getMockAppender(), getCategory()).size());
      Affirm.affirmEquals("Message malformed", variance.getExpected(), logEvents.get(count).getMessage());

      count++;
    }

  }

  @Test
  public void testToString() {
    Logger log = LoggerFactory.getLogger(getCategory());
    Affirm.affirmTrue(String.format("toString() failed on [%s]!", SLF4JLogger.class.getName()),
        log.toString().startsWith("com.openpojo.log.impl.SLF4JLogger [@")
            && log.toString().endsWith(": logger=org.slf4j.impl.Log4jLoggerAdapter(com.openpojo.log.SL4JLoggerTest)]"));
  }
}
