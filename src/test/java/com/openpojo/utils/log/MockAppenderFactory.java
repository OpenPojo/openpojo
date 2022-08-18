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
 * @author oshoukry
 */
public final class MockAppenderFactory {
  private static final MockAppender LOG4JAPPENDER = new MockAppenderLog4J();
  private static final MockAppender JAVALOGGERAPPENDER = new MockAppenderJavaLogger();

  public static MockAppender getMockAppender(final String name) {
    if (name.equals("Log4J")) {
      return LOG4JAPPENDER;
    }
    if (name.equals("Java")) {
      return JAVALOGGERAPPENDER;
    }
    throw new IllegalArgumentException(String.format("Unknown appender requested [%s]", name));
  }

}
