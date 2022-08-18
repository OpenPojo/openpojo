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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Appender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.spi.ErrorHandler;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

/**
 * @author oshoukry
 */
@SuppressWarnings("ReturnOfNull")
public class SpyAppender implements Appender {
  private Map<String, Level> priorLevels = new HashMap<String, Level>();
  private Map<String, List<LoggingEvent>> eventMap = new LinkedHashMap<String, List<LoggingEvent>>();

  public List<LoggingEvent> getEventsForLogger(Class<?> clazz) {
    return getEventsForLogger(clazz.getName());
  }

  public List<LoggingEvent> getEventsForLogger(String loggerName) {
    List<LoggingEvent> events = new ArrayList<LoggingEvent>();
    final List<LoggingEvent> loggingEvents = eventMap.get(loggerName);
    if (loggingEvents != null)
     for (LoggingEvent event: loggingEvents)
         events.add(event);
    return events;
  }

  public void startCaptureForLogger(Class<?> clazz) {
    startCaptureForLogger(clazz.getName());
  }

  public void startCaptureForLogger(String loggerName) {
    priorLevels.put(loggerName, getLogLevel(loggerName));
    setLogLevel(loggerName, Level.ALL);
    LogManager.getLogger(loggerName).addAppender(this);
  }

  public void stopCaptureForLogger(Class<?> clazz) {
    stopCaptureForLogger(clazz.getName());
  }

  public void stopCaptureForLogger(String loggerName) {
    final Level priorLevel = priorLevels.get(loggerName);
    setLogLevel(loggerName, priorLevel);
    LogManager.getLogger(loggerName).removeAppender(this);
  }

  private Level getLogLevel(String loggerName) {
    return LogManager.getLogger(loggerName).getLevel();
  }

  private void setLogLevel(String loggerName, Level level) {
    LogManager.getLogger(loggerName).setLevel(level);
  }

  public void doAppend(LoggingEvent event) {
    synchronized (this) {
      String loggerName = event.getLoggerName();
      if (eventMap.get(loggerName) != null)
        eventMap.get(loggerName).add(event);
      else {
        List<LoggingEvent> events = new ArrayList<LoggingEvent>();
        events.add(event);
        eventMap.put(loggerName, events);
      }
    }
  }

  public void addFilter(Filter newFilter) {

  }

  public Filter getFilter() {
    return null;
  }

  public void clearFilters() {

  }

  public void close() {

  }

  public String getName() {
    return null;
  }

  public void setErrorHandler(ErrorHandler errorHandler) {

  }

  public ErrorHandler getErrorHandler() {
    return null;
  }

  public void setLayout(Layout layout) {

  }

  public Layout getLayout() {
    return null;
  }

  public void setName(String name) {
  }

  public boolean requiresLayout() {
    return false;
  }
}
