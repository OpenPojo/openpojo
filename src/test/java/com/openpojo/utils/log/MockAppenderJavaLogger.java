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

import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import com.openpojo.utils.log.LogEvent.Priority;

/**
 * @author oshoukry
 */

/**
 * Our own JavaLogger adapter.<br>
 * The Mapping is done as follows:<br>
 * - Trace = Level.FINEST<br>
 * - Debug = Level.FINER<br>
 * - Info = Level.FINE<br>
 * - Warn = Level.WARNING<br>
 * - Error = Level.WARNING<br>
 * - Fatal = Level.SEVERE<br>
 */
public class MockAppenderJavaLogger extends Handler implements MockAppender {

  private Priority extractPriority(final LogRecord event) {
    if (event.getLevel().equals(Level.FINEST)) {
      return Priority.TRACE;
    }
    if (event.getLevel().equals(Level.FINER)) {
      return Priority.DEBUG;
    }
    if (event.getLevel().equals(Level.FINE)) {
      return Priority.INFO;
    }
    if (event.getLevel().equals(Level.WARNING)) {
      return Priority.WARN;
    }
    if (event.getLevel().equals(Level.SEVERE)) {
      return Priority.FATAL;
    }
    throw new IllegalArgumentException("Unknown Logged Level" + event.getLevel());
  }

  @Override
  public void close() throws SecurityException {
  }

  @Override
  public void flush() {
  }

  @Override
  public void publish(final LogRecord record) {
    final LogEvent le = new LogEvent(record.getLoggerName(), extractPriority(record), record.getMessage());
    EventLogger.registerEvent(this.getClass(), le);
  }

  /**
   * This call is used for asserting on testing to see if you got the right counts for each priority.
   *
   * @param source
   *     The source of the logs
   * @param priority
   *     The priority at which they were sent in as.
   * @return The total count on recieved events.
   */
  public synchronized Integer getCountBySourceByPriority(final String source, final Priority priority) {
    return EventLogger.getCountByAppenderBySourceByPriority(this.getClass(), source, priority);
  }

  /**
   * This call is used for asserting on testing to see if you got the right counts regardless of categories.
   *
   * @param source
   *     The source of the logs
   * @return The total count on recieved events.
   */
  public synchronized Integer getCountBySource(final String source) {
    return EventLogger.getCountBySource(this.getClass(), source);
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
  public synchronized List<LogEvent> getLoggedEventsBySourceByPriority(final String source, final Priority priority) {
    return EventLogger.getLoggedEventsByAppenderBySourceByPriority(this.getClass(), source, priority);
  }

  public void resetAppender() {
    EventLogger.resetEvents(this.getClass());
  }
}
