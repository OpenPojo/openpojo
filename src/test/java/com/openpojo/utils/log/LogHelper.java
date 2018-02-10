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

package com.openpojo.utils.log;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import com.openpojo.utils.log.LogEvent.Priority;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;

/**
 * @author oshoukry
 */
public final class LogHelper {

  private LogHelper() {
  }

  public static void initializeLog4J() {
    EventLogger.resetEvents(MockAppenderLog4J.class);
    BasicConfigurator.resetConfiguration();
    Properties props = new Properties();
    try {
      props.load(LogHelper.class.getResourceAsStream("/com/openpojo/utils/log/test.log4j.properties"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    PropertyConfigurator.configure(props);
  }

  public static void initializeJavaLogger() {
    EventLogger.resetEvents(MockAppenderJavaLogger.class);
    // Reset Java Logger
    java.util.logging.LogManager.getLogManager().reset();

    MockAppenderJavaLogger mockAppenderJavaLogger = new MockAppenderJavaLogger();
    mockAppenderJavaLogger.setLevel(java.util.logging.Level.ALL);
    java.util.logging.Logger.getLogger("").addHandler(mockAppenderJavaLogger);
    java.util.logging.Logger.getLogger("").setLevel(java.util.logging.Level.ALL);
  }

  /**
   * This method initializes the loggers, currently just reseting the Mock Appender captured events.
   */
  public static void initializeLoggers() {
    initializeLog4J();
    initializeJavaLogger();
  }

  /**
   * This method returns the counts on trace events by source.
   *
   * @param source
   *     Source of the logs
   * @return Total count recieved
   */
  public static Integer getTraceCountBySource(final Class<? extends MockAppender> appender, final String source) {
    return EventLogger.getCountByAppenderBySourceByPriority(appender, source, Priority.TRACE);
  }

  /**
   * This method returns the counts on debug events by source.
   *
   * @param source
   *     Source of the logs
   * @return Total count recieved
   */
  public static Integer getDebugCountBySource(final Class<? extends MockAppender> appender, final String source) {
    return EventLogger.getCountByAppenderBySourceByPriority(appender, source, Priority.DEBUG);
  }

  /**
   * This method returns the counts on info events by source.
   *
   * @param source
   *     Source of the logs
   * @return Total count recieved
   */
  public static Integer getInfoCountBySource(final Class<? extends MockAppender> appender, final String source) {
    return EventLogger.getCountByAppenderBySourceByPriority(appender, source, Priority.INFO);
  }

  /**
   * This method returns the counts on warn events by source.
   *
   * @param source
   *     Source of the logs
   * @return Total count recieved
   */
  public static Integer getWarnCountBySource(final Class<? extends MockAppender> appender, final String source) {
    return EventLogger.getCountByAppenderBySourceByPriority(appender, source, Priority.WARN);
  }

  /**
   * This method returns the counts on error events by source.
   *
   * @param source
   *     Source of the logs
   * @return Total count recieved
   */
  public static Integer getErrorCountBySource(final Class<? extends MockAppender> appender, final String source) {
    return EventLogger.getCountByAppenderBySourceByPriority(appender, source, Priority.ERROR);
  }

  /**
   * This method returns the counts on fatal events by source.
   *
   * @param source
   *     Source of the logs
   * @return Total count recieved
   */
  public static Integer getFatalCountBySource(final Class<? extends MockAppender> appender, final String source) {
    return EventLogger.getCountByAppenderBySourceByPriority(appender, source, Priority.FATAL);
  }

  /**
   * This method returns all the counts by source.
   *
   * @param source
   *     Source of the logs
   * @return Total count recieved
   */
  public static Integer getCountBySource(final Class<? extends MockAppender> appender, final String source) {
    return EventLogger.getCountBySource(appender, source);
  }

  /**
   * This method returns the trace logged events for a given source.
   *
   * @param source
   *     Source of the logs.
   * @return List of logged events recieved.
   */
  public static List<LogEvent> getTraceEvents(final Class<? extends MockAppender> appender, final String source) {
    return EventLogger.getLoggedEventsByAppenderBySourceByPriority(appender, source, Priority.TRACE);
  }

  /**
   * This method returns the debug logged events for a given source.
   *
   * @param source
   *     Source of the logs.
   * @return List of logged events recieved.
   */
  public static List<LogEvent> getDebugEvents(final Class<? extends MockAppender> appender, final String source) {
    return EventLogger.getLoggedEventsByAppenderBySourceByPriority(appender, source, Priority.DEBUG);
  }

  /**
   * This method returns the info logged events for a given source.
   *
   * @param source
   *     Source of the logs.
   * @return List of logged events recieved.
   */
  public static List<LogEvent> getInfoEvents(final Class<? extends MockAppender> appender, final String source) {
    return EventLogger.getLoggedEventsByAppenderBySourceByPriority(appender, source, Priority.INFO);
  }

  /**
   * This method returns the warn logged events for a given source.
   *
   * @param source
   *     Source of the logs.
   * @return List of logged events recieved.
   */
  public static List<LogEvent> getWarnEvents(final Class<? extends MockAppender> appender, final String source) {
    return EventLogger.getLoggedEventsByAppenderBySourceByPriority(appender, source, Priority.WARN);
  }

  /**
   * This method returns the error logged events for a given source.
   *
   * @param source
   *     Source of the logs.
   * @return List of logged events recieved.
   */
  public static List<LogEvent> getErrorEvents(final Class<? extends MockAppender> appender, final String source) {
    return EventLogger.getLoggedEventsByAppenderBySourceByPriority(appender, source, Priority.ERROR);
  }

  /**
   * This method returns the fatal logged events for a given source.
   *
   * @param source
   *     Source of the logs.
   * @return List of logged events recieved.
   */
  public static List<LogEvent> getFatalEvents(final Class<? extends MockAppender> appender, final String source) {
    return EventLogger.getLoggedEventsByAppenderBySourceByPriority(appender, source, Priority.FATAL);
  }

  public static void initialize(final Class<?> mockAppender) {
    if (mockAppender.getName().equals(MockAppenderLog4J.class.getCanonicalName())) {
      initializeLog4J();
    }
    if (mockAppender.getName().equals(MockAppenderJavaLogger.class.getName())) {
      initializeJavaLogger();
    }
  }

  public static void resetLoggers() {
    java.util.logging.LogManager.getLogManager().reset();

    BasicConfigurator.resetConfiguration();
    Properties props = new Properties();
    try {
      props.load(LogHelper.class.getResourceAsStream("/log4j.properties"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    PropertyConfigurator.configure(props);
  }
}
