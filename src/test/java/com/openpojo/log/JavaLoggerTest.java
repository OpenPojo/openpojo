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
import com.openpojo.log.impl.JavaLogger;
import com.openpojo.utils.log.LogHelper;
import com.openpojo.utils.log.MockAppender;
import com.openpojo.utils.log.MockAppenderJavaLogger;
import com.openpojo.validation.affirm.Affirm;
import org.junit.Before;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class JavaLoggerTest extends AbstractLoggerBase {
  private static final String LOGCATEGORY = JavaLoggerTest.class.getName();
  private static final int LOGLEVELS = 5; // TRACE, DEBUG, INFO, WARN, ERROR, FATAL


  @Before
  public void setup() {
    LogHelper.initializeJavaLogger();
    LoggerFactory.setActiveLogger(JavaLogger.class);
  }

  /**
   * Our own JavaLogger adapter.<br>
   * The Mapping is done as follows:<br>
   * - Trace = Level.FINEST<br>
   * - Debug = Level.FINER<br>
   * - Info = Level.FINE<br>
   * - Warn = Level.WARNING<br>
   * - Error = Level.SEVERE<br>
   * - Fatal = Level.SEVERE<br>
   */

  @Override
  public Class<? extends MockAppender> getMockAppender() {
    return MockAppenderJavaLogger.class;
  }

  @Override
  public String getCategory() {
    return LOGCATEGORY;
  }

  @Override
  public int getLogLevelsCount() {
    return LOGLEVELS;
  }

  @Test
  public void shouldLogInVariousLevels() {
    testWithLogLevel(LogLevel.TRACE);
    testWithLogLevel(LogLevel.DEBUG);
    testWithLogLevel(LogLevel.WARN);
    testWithLogLevel(LogLevel.INFO);
    //ERROR goes to FATAL, needs seperate test case;
    //testWithLogLevel(LogLevel.ERROR);

    testWithLogLevel(LogLevel.FATAL);
  }

  /**
   * There is no Error level in JavaLogger, so all messages are routed to FATAL.
   */
  @Test
  public void shouldTestError() {
    init();
    for (LoggerVarArgsTestData variance : getVarArgsVariations()) {
      log.error(variance.getMessage(), variance.getParams());
      logEvents = LogHelper.getFatalEvents(getMockAppender(), getCategory());

      Affirm.affirmEquals(String.format("Lost [%s] message?", "ERROR"), count + 1, logEvents.size());
      Affirm.affirmEquals("Error wired?", 0, LogHelper.getErrorEvents(getMockAppender(), getCategory()).size());
      Affirm.affirmEquals("Message malformed", variance.getExpected(), logEvents.get(count).getMessage());

      count++;
    }

    for (LoggerMsgTestData variance : getMsgVariations()) {
      log.error(variance.getMessage());
      logEvents = LogHelper.getFatalEvents(getMockAppender(), getCategory());

      Affirm.affirmEquals(String.format("Lost [%s] message?", "ERROR"), count + 1, logEvents.size());
      Affirm.affirmEquals("Error wired?", 0, LogHelper.getErrorEvents(getMockAppender(), getCategory()).size());
      Affirm.affirmEquals("Message malformed", variance.getExpected(), logEvents.get(count).getMessage());

      count++;
    }

  }

  @Test
  public void testToString() {
    Logger log = LoggerFactory.getLogger(getCategory());
    Affirm.affirmTrue(String.format("toString() failed on [%s] got [%s]!", JavaLogger.class.getName(), log.toString()), log
        .toString().startsWith("com.openpojo.log.impl.JavaLogger [@") && log.toString().contains(": logger=java.util" + "" +
        ".logging.Logger@") && log.toString().endsWith("]"));
  }
}
