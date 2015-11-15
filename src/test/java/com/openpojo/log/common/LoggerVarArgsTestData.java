/*
 * Copyright (c) 2010-2015 Osman Shoukry
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License or any
 *    later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
