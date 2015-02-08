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

package com.openpojo.log;

import org.junit.Before;
import org.junit.Test;

import com.openpojo.log.common.AbstractLoggerBase;
import com.openpojo.log.common.LoggerMsgTestData;
import com.openpojo.log.common.LoggerVarArgsTestData;
import com.openpojo.log.impl.SLF4JLogger;
import com.openpojo.utils.log.LogHelper;
import com.openpojo.utils.log.MockAppender;
import com.openpojo.utils.log.MockAppenderLog4J;
import com.openpojo.validation.affirm.Affirm;

/**
 * @author oshoukry
 */
public class SL4JLoggerTest extends AbstractLoggerBase {
    private static final String LOGCATEGORY = SL4JLoggerTest.class.getName();
    private static final int LOGLEVELS = 5; // TRACE, DEBUG, INFO, WARN, ERROR

    /**
     * This method does the test setup, currently initializes the loggers.
     */
    @Before
    public final void setUp() {
        LoggerFactory.setActiveLogger(SLF4JLogger.class);
    }

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

    @Test
    public void shouldLogInVariousLevels() {
        testWithLogLevel(LogLevel.TRACE);
        testWithLogLevel(LogLevel.DEBUG);
        testWithLogLevel(LogLevel.WARN);
        testWithLogLevel(LogLevel.INFO);
        testWithLogLevel(LogLevel.ERROR);
    }

    /**
     * There is no fatal level in SLF4J, so all messages are routed to ERROR.
     */
    @Test
    public void shouldTestFatal() {
        init();
        for (LoggerVarArgsTestData variance : getVarArgsVariations()) {
            log.fatal(variance.getMessage(), variance.getParams());
            logEvents = LogHelper.getErrorEvents(getMockAppender(), getCategory());

            Affirm.affirmEquals(String.format("Lost [%s] message?", "FATAL"), count + 1, logEvents.size());
            Affirm.affirmEquals("Fatal wired?", 0, LogHelper.getFatalEvents(getMockAppender(), getCategory()).size());
            Affirm.affirmEquals("Message malformed", variance.getExpected(), logEvents.get(count).getMessage());

            count++;
        }

        for (LoggerMsgTestData variance : getMsgVariations()) {
            log.fatal(variance.getMessage());
            logEvents = LogHelper.getErrorEvents(getMockAppender(), getCategory());

            Affirm.affirmEquals(String.format("Lost [%s] message?", "FATAL"), count + 1, logEvents.size());
            Affirm.affirmEquals("Fatal wired?", 0, LogHelper.getFatalEvents(getMockAppender(), getCategory()).size());
            Affirm.affirmEquals("Message malformed", variance.getExpected(), logEvents.get(count).getMessage());

            count++;
        }

    }

    @Test
    public void testToString() {
        Logger log = LoggerFactory.getLogger(getCategory());
        Affirm.affirmTrue(String.format("toString() failed on [%s]!", SLF4JLogger.class.getName()), log.toString()
                .startsWith("com.openpojo.log.impl.SLF4JLogger [@")
                && log.toString().endsWith(
                        ": logger=org.slf4j.impl.Log4jLoggerAdapter(com.openpojo.log.SL4JLoggerTest)]"));
    }
}
