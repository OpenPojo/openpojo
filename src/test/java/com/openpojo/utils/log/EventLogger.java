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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.openpojo.utils.log.LogEvent.Priority;

/**
 * @author oshoukry
 */
public final class EventLogger {
  private static final Map<Class<? extends MockAppender>, List<LogEvent>> logEventsMapByAppender = new HashMap<Class<?
      extends MockAppender>, List<LogEvent>>();

  public static void resetEvents(final Class<? extends MockAppender> appender) {
    List<LogEvent> logEvents = logEventsMapByAppender.get(appender);
    if (logEvents != null) {
      logEvents.clear();
    }
  }

  public static synchronized void registerEvent(final Class<? extends MockAppender> appender, final LogEvent logEvent) {
    List<LogEvent> logEvents = logEventsMapByAppender.remove(appender);
    if (logEvents == null) {
      logEvents = new LinkedList<LogEvent>();
    }
    logEvents.add(logEvent);
    logEventsMapByAppender.put(appender, logEvents);
  }

  public static synchronized Integer getCountByAppenderBySourceByPriority(final Class<? extends MockAppender> appender, final
  String source, final Priority priority) {
    List<LogEvent> logEvents = logEventsMapByAppender.get(appender);
    int count = 0;
    for (LogEvent entry : logEvents) {
      if (entry.getSource().equals(source) && entry.getPriority().equals(priority)) {
        count++;
      }
    }
    return count;
  }

  public static synchronized Integer getCountBySource(final Class<? extends MockAppender> appender, final String source) {
    List<LogEvent> logEvents = logEventsMapByAppender.get(appender);
    int count = 0;
    for (LogEvent entry : logEvents) {
      if (entry.getSource().equals(source)) {
        count++;
      }
    }
    return count;
  }

  /**
   * Get all logged events by priority for a particular source.
   *
   * @param source
   *     The source of the logs.
   * @param priority
   *     The priority at which they were sent in as.
   * @return List of logged events.
   */
  public static synchronized List<LogEvent> getLoggedEventsByAppenderBySourceByPriority(final Class<? extends
      MockAppender> appender, final String source, final Priority priority) {
    List<LogEvent> logEvents = logEventsMapByAppender.get(appender);
    List<LogEvent> result = new LinkedList<LogEvent>();
    for (LogEvent entry : logEvents) {
      if (entry.getSource().equals(source) && entry.getPriority().equals(priority)) {
        result.add(entry);
      }
    }
    return result;
  }

}
