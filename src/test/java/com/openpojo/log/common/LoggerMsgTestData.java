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

import com.openpojo.log.utils.MessageFormatter;

/**
 * @author oshoukry
 */
public class LoggerMsgTestData {
  private final String expected;
  private final Object message;

  /**
   * Full constructor
   *
   * @param message
   *     The message to log.
   */
  public LoggerMsgTestData(final Object message) {
    this.message = message;
    expected = MessageFormatter.format(message);
  }

  /**
   * @return the expected
   */
  public String getExpected() {
    return expected;
  }

  /**
   * @return the message
   */
  public Object getMessage() {
    return message;
  }
}
