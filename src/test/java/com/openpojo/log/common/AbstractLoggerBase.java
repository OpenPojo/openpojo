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

package com.openpojo.log.common;

import java.util.LinkedList;
import java.util.List;

import com.openpojo.log.Logger;
import com.openpojo.log.LoggerFactory;
import com.openpojo.utils.log.LogEvent;
import com.openpojo.utils.log.LogHelper;
import com.openpojo.utils.log.MockAppender;
import com.openpojo.validation.affirm.Affirm;

/**
 * @author oshoukry
 */
public abstract class AbstractLoggerBase {
  protected List<LogEvent> logEvents;
  protected Logger log;
  protected int count;

  public enum LogLevel {
    TRACE, DEBUG, INFO, WARN, ERROR, FATAL
  }

  public abstract String getCategory();

  public abstract int getLogLevelsCount();

  public abstract Class<? extends MockAppender> getMockAppender();

  /**
   * This method is the real one that has all the variations our logging supports.
   */
  protected List<LoggerVarArgsTestData> getVarArgsVariations() {
    List<LoggerVarArgsTestData> variations = new LinkedList<LoggerVarArgsTestData>();

    variations.add(new LoggerVarArgsTestData("Exception=[{0}]",
        new Object[] { new Exception("This is an exception") }));
    variations.add(new LoggerVarArgsTestData("Nested Array unfolds [{0}, {1}]",
        new Object[] { new Integer[] { 1, 2, 3 }, new String[] { "check", "me", "out" } }));
    variations.add(new LoggerVarArgsTestData(null, new Object[] { "just args, no message" }));
    return variations;
  }

  /**
   * This method returns the various variations for single argument logging.
   */
  @SuppressWarnings("RedundantCast")
  protected List<LoggerMsgTestData> getMsgVariations() {
    List<LoggerMsgTestData> variations = new LinkedList<LoggerMsgTestData>();

    variations.add(new LoggerMsgTestData("Simple message"));
    variations.add(new LoggerMsgTestData("old style message" + " some arg" + " some other arg"));
    variations.add(new LoggerMsgTestData((String) null));
    variations.add(new LoggerMsgTestData(new RuntimeException("uh oh, runtime exception occurred")));
    return variations;
  }

  public final void init() {
    count = 0;
    log = LoggerFactory.getLogger(getCategory());
    LogHelper.initialize(getMockAppender());
  }

  public final void testWithLogLevel(final LogLevel logLevel) {
    init();
    for (LoggerVarArgsTestData variance : getVarArgsVariations()) {
      switch (logLevel) {
        case TRACE:
          log.trace(variance.getMessage(), variance.getParams());
          logEvents = LogHelper.getTraceEvents(getMockAppender(), getCategory());
          break;
        case DEBUG:
          log.debug(variance.getMessage(), variance.getParams());
          logEvents = LogHelper.getDebugEvents(getMockAppender(), getCategory());
          break;
        case INFO:
          log.info(variance.getMessage(), variance.getParams());
          logEvents = LogHelper.getInfoEvents(getMockAppender(), getCategory());
          break;
        case WARN:
          log.warn(variance.getMessage(), variance.getParams());
          logEvents = LogHelper.getWarnEvents(getMockAppender(), getCategory());
          break;
        case ERROR:
          log.error(variance.getMessage(), variance.getParams());
          logEvents = LogHelper.getErrorEvents(getMockAppender(), getCategory());
          break;
        case FATAL:
          log.fatal(variance.getMessage(), variance.getParams());
          logEvents = LogHelper.getFatalEvents(getMockAppender(), getCategory());
          break;
        default:
          throw new IllegalArgumentException("Unknown LogLevel");
      }

      Affirm.affirmEquals(String.format("Lost [%s] message?", logLevel.name()), count + 1, logEvents.size());
      Affirm.affirmEquals("Message malformed", variance.getExpected(), logEvents.get(count).getMessage());

      count++;
    }
    for (LoggerMsgTestData variance : getMsgVariations()) {
      switch (logLevel) {
        case TRACE:
          log.trace(variance.getMessage());
          logEvents = LogHelper.getTraceEvents(getMockAppender(), getCategory());
          break;
        case DEBUG:
          log.debug(variance.getMessage());
          logEvents = LogHelper.getDebugEvents(getMockAppender(), getCategory());
          break;
        case INFO:
          log.info(variance.getMessage());
          logEvents = LogHelper.getInfoEvents(getMockAppender(), getCategory());
          break;
        case WARN:
          log.warn(variance.getMessage());
          logEvents = LogHelper.getWarnEvents(getMockAppender(), getCategory());
          break;
        case ERROR:
          log.error(variance.getMessage());
          logEvents = LogHelper.getErrorEvents(getMockAppender(), getCategory());
          break;
        case FATAL:
          log.fatal(variance.getMessage());
          logEvents = LogHelper.getFatalEvents(getMockAppender(), getCategory());
          break;
        default:
          throw new IllegalArgumentException("Unknown LogLevel");
      }

      Affirm.affirmEquals(String.format("Lost [%s] message?", logLevel.name()), count + 1, logEvents.size());
      Affirm.affirmEquals("Message malformed", variance.getExpected(), logEvents.get(count).getMessage());

      count++;
    }

  }
}
