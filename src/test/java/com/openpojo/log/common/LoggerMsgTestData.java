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
