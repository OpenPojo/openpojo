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
public final class LoggerVarArgsTestData {
  private final String expected;
  private final String message;
  private final Object[] params;

  /**
   * Full constructor.
   *
   * @param message
   *     The message to log.
   * @param params
   *     The parameters to log.
   */
  public LoggerVarArgsTestData(final String message, final Object[] params) {
    this.message = message;
    this.params = params;
    expected = MessageFormatter.format(message, params);
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
  public String getMessage() {
    return message;
  }

  /**
   * @return the params
   */
  public Object[] getParams() {
    return params;
  }
}
