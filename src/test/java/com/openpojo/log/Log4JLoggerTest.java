/*
 * Copyright (c) 2010-2014 Osman Shoukry
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

package com.openpojo.log;

import org.junit.Before;
import org.junit.Test;

import com.openpojo.log.common.AbstractLoggerBase;
import com.openpojo.log.impl.Log4JLogger;
import com.openpojo.log.impl.SLF4JLogger;
import com.openpojo.log.utils.ActiveLogger;
import com.openpojo.utils.log.MockAppender;
import com.openpojo.utils.log.MockAppenderLog4J;
import com.openpojo.validation.affirm.Affirm;

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
        ActiveLogger.setActiveLogger(Log4JLogger.class);
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
        Affirm.affirmTrue(String.format("toString() failed on [%s]!", SLF4JLogger.class.getName()), log.toString()
                .startsWith("com.openpojo.log.impl.Log4JLogger [@")
                && log.toString().contains(": logger=org.apache.log4j.Logger@") && log.toString().endsWith("]"));
    }
}
