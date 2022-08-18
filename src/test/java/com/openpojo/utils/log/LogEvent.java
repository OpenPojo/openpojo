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


/**
 * For testing, this structure holds the logged events.
 *
 * @author oshoukry
 */
public final class LogEvent {

  public enum Priority {
    TRACE, DEBUG, INFO, WARN, ERROR, FATAL
  }

  /**
   * The source of the event.
   */
  private final String source;

  /**
   * The priority of the event.
   */
  private final Priority priority;

  /**
   * The message of the event.
   */
  private final String message;

  /**
   * Constructor making this class immutable.
   *
   * @param source
   *     The source of the event.
   * @param priority
   *     The priority of the event.
   * @param message
   *     The message of the event.
   */
  public LogEvent(final String source, final Priority priority, final String message) {
    this.source = source;
    this.priority = priority;
    this.message = message;
  }

  /**
   * @return the source
   */
  public String getSource() {
    return source;
  }

  /**
   * @return the priority
   */
  public Priority getPriority() {
    return priority;
  }

  /**
   * @return the message
   */
  public String getMessage() {
    return message;
  }

}
