/**
 * Copyright (C) 2010 Osman Shoukry
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.openpojo.log;

import org.junit.Before;
import org.junit.Test;

import com.openpojo.log.common.AbstractLoggerBase;
import com.openpojo.log.common.LoggerMsgTestData;
import com.openpojo.log.common.LoggerVarArgsTestData;
import com.openpojo.log.impl.JavaLogger;
import com.openpojo.log.utils.ActiveLogger;
import com.openpojo.utils.log.LogHelper;
import com.openpojo.utils.log.MockAppender;
import com.openpojo.utils.log.MockAppenderJavaLogger;
import com.openpojo.validation.affirm.Affirm;

/**
 * @author oshoukry
 */
public class JavaLoggerTest extends AbstractLoggerBase {
    private static final String LOGCATEGORY = JavaLoggerTest.class.getName();
    private static final int LOGLEVELS = 5; // TRACE, DEBUG, INFO, WARN, ERROR, FATAL


    @Before
    public void setup() {
        LogHelper.initializeJavaLogger();
        ActiveLogger.setActiveLogger(JavaLogger.class);
    }

    /**
     * Our own JavaLogger adapter.<br>
     * The Mapping is done as follows:<br>
     * - Trace = Level.FINEST<br>
     * - Debug = Level.FINER<br>
     * - Info = Level.FINE<br>
     * - Warn = Level.WARNING<br>
     * - Error = Level.SEVERE<br>
     * - Fatal = Level.SEVERE<br>
     */

    @Override
    public Class<? extends MockAppender> getMockAppender() {
        return MockAppenderJavaLogger.class;
    }

    @Override
    public String getCategory() {
        return LOGCATEGORY;
    }

    @Override
    public int getLogLevelsCount() {
        return LOGLEVELS;
    }

    @Test
    public void shouldLogInVariousLevels() {
        testWithLogLevel(LogLevel.TRACE);
        testWithLogLevel(LogLevel.DEBUG);
        testWithLogLevel(LogLevel.WARN);
        testWithLogLevel(LogLevel.INFO);
        //ERROR goes to FATAL, needs seperate test case;
        //testWithLogLevel(LogLevel.ERROR);

        testWithLogLevel(LogLevel.FATAL);
    }

    /**
     * There is no Error level in JavaLogger, so all messages are routed to FATAL.
     */
    @Test
    public void shouldTestError() {
        init();
        for (LoggerVarArgsTestData variance : getVarArgsVariations()) {
            log.error(variance.getMessage(), variance.getParams());
            logEvents = LogHelper.getFatalEvents(getMockAppender(), getCategory());

            Affirm.affirmEquals(String.format("Lost [%s] message?", "ERROR"), count + 1, logEvents.size());
            Affirm.affirmEquals("Error wired?", 0, LogHelper.getErrorEvents(getMockAppender(), getCategory()).size());
            Affirm.affirmEquals("Message malformed", variance.getExpected(), logEvents.get(count).getMessage());

            count++;
        }

        for (LoggerMsgTestData variance : getMsgVariations()) {
            log.error(variance.getMessage());
            logEvents = LogHelper.getFatalEvents(getMockAppender(), getCategory());

            Affirm.affirmEquals(String.format("Lost [%s] message?", "ERROR"), count + 1, logEvents.size());
            Affirm.affirmEquals("Error wired?", 0, LogHelper.getErrorEvents(getMockAppender(), getCategory()).size());
            Affirm.affirmEquals("Message malformed", variance.getExpected(), logEvents.get(count).getMessage());

            count++;
        }

    }

    @Test
    public void testToString() {
        Logger log = LoggerFactory.getLogger(getCategory());
        Affirm.affirmTrue(String.format("toString() failed on [%s] got [%s]!", JavaLogger.class.getName(), log.toString()), log.toString()
                .startsWith("com.openpojo.log.impl.JavaLogger [@")
                && log.toString().contains(": logger=java.util.logging.Logger@")
                && log.toString().endsWith("]"));
    }
}
