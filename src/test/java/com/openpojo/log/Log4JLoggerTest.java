/*
 * Copyright (c) 2010-2016 Osman Shoukry
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

package com.openpojo.log;

import com.openpojo.log.common.AbstractLoggerBase;
import com.openpojo.log.impl.Log4JLogger;
import com.openpojo.log.impl.SLF4JLogger;
import com.openpojo.utils.log.MockAppender;
import com.openpojo.utils.log.MockAppenderLog4J;
import com.openpojo.validation.affirm.Affirm;
import org.junit.Before;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class Log4JLoggerTest extends AbstractLoggerBase {
  private static final String LOGCATEGORY = Log4JLoggerTest.class.getName();
  private static final int LOGLEVELS = 6; // TRACE, DEBUG, INFO, WARN, ERROR, FATAL

  @Override
  public String getCategory() {
    return LOGCATEGORY;
  }

  @Override
  public int getLogLevelsCount() {
    return LOGLEVELS;
  }

  @Override
  public Class<? extends MockAppender> getMockAppender() {
    return MockAppenderLog4J.class;
  }

  @Before
  public final void setUp() {
    LoggerFactory.setActiveLogger(Log4JLogger.class);
  }

  @Test
  public void shouldLogInVariousLevels() {
    testWithLogLevel(LogLevel.TRACE);
    testWithLogLevel(LogLevel.DEBUG);
    testWithLogLevel(LogLevel.WARN);
    testWithLogLevel(LogLevel.INFO);
    testWithLogLevel(LogLevel.ERROR);
    testWithLogLevel(LogLevel.FATAL);
  }

  @Test
  public void testToString() {
    Logger log = LoggerFactory.getLogger(getCategory());
    Affirm.affirmTrue(String.format("toString() failed on [%s]!", SLF4JLogger.class.getName()),
        log.toString().startsWith("com.openpojo.log.impl.Log4JLogger [@")
            && log.toString().contains(": logger=org.apache.log4j.Logger@")
            && log.toString().endsWith("]"));
  }
}
