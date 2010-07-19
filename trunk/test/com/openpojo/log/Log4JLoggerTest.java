package com.openpojo.log;

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import utils.log.LogHelper;
import utils.log.LoggedEvent;

import com.openpojo.log.impl.Log4JLogger;
import com.openpojo.log.utils.ActiveLogger;
import com.openpojo.log.utils.MessageFormatter;

/**
 * @author oshoukry
 */
public class Log4JLoggerTest {
    private static final String LOGCATEGORY = Log4JLoggerTest.class.getName();
    private static final int LOGLEVELS = 6; //TRACE, DEBUG, INFO, WARN, ERROR, FATAL

    @BeforeClass
    public static final void switchLoggerToLog4J() {
        ActiveLogger.setActiveLogger(Log4JLogger.class);
    }

    /**
     * This method does the test setup, currently initializes the loggers.
     */
    @Before
    public final void setUp() {
        LogHelper.initializeLoggers();
    }

    /**
     * This method is the real one that has all the variations our logging supports.
     *
     * @return
     */
    private List<LoggerVarArgsTestData> getVarArgsVariations() {
        List<LoggerVarArgsTestData> variations = new LinkedList<LoggerVarArgsTestData>();

        variations.add(new LoggerVarArgsTestData("Exception=[{0}]", new Object[] { new Exception("This is an exception") }));
        variations.add(new LoggerVarArgsTestData("Nested Array unfolds [{0}, {1}]", new Object[] { new Integer[] { 1, 2, 3 },
                new String[] { "check", "me", "out" } }));
        variations.add(new LoggerVarArgsTestData(null, new Object[] { "just args, no message" }));
        return variations;
    }

    /**
     * This method returns the various variations for single argument logging.
     * @return
     *      a List of LoggerMsgTestData variations for testing.
     */
    private List<LoggerMsgTestData> getMsgVariations() {
        List<LoggerMsgTestData> variations = new LinkedList<LoggerMsgTestData>();

        variations.add(new LoggerMsgTestData("Simple message"));
        variations.add(new LoggerMsgTestData("old style message" + " some arg" + " some other arg"));
        variations.add(new LoggerMsgTestData((String)null));
        variations.add(new LoggerMsgTestData(new RuntimeException("uh oh, runtime exception occured")));
        return variations;
    }

    /**
     * This test method will log in all available levels, will check logged messages recieved for proper counts,
     * messages, and level.
     */
    @Test
    public final void shouldLogInVariousLevelsUsingVarArgs() {

        Logger log = LoggerFactory.getLogger(LOGCATEGORY);
        List<LoggedEvent> loggedEvents;

        int count = 0;
        LogHelper.initializeLoggers();
        for (LoggerVarArgsTestData variance : getVarArgsVariations()) {

            log.trace(variance.message, variance.params);
            loggedEvents = LogHelper.getTraceEvents(LOGCATEGORY);

            Assert.assertEquals(count + 1, loggedEvents.size());
            Assert.assertEquals(variance.expected, loggedEvents.get(count).message);

            log.debug(variance.message, variance.params);
            loggedEvents = LogHelper.getDebugEvents(LOGCATEGORY);

            Assert.assertEquals(count + 1, loggedEvents.size());
            Assert.assertEquals(variance.expected, loggedEvents.get(count).message);

            log.info(variance.message, variance.params);
            loggedEvents = LogHelper.getInfoEvents(LOGCATEGORY);

            Assert.assertEquals(count + 1, loggedEvents.size());
            Assert.assertEquals(variance.expected, loggedEvents.get(count).message);

            log.warn(variance.message, variance.params);
            loggedEvents = LogHelper.getWarnEvents(LOGCATEGORY);

            Assert.assertEquals(count + 1, loggedEvents.size());
            Assert.assertEquals(variance.expected, loggedEvents.get(count).message);

            log.error(variance.message, variance.params);
            loggedEvents = LogHelper.getErrorEvents(LOGCATEGORY);

            Assert.assertEquals(count + 1, loggedEvents.size());
            Assert.assertEquals(variance.expected, loggedEvents.get(count).message);


            log.fatal(variance.message, variance.params);
            loggedEvents = LogHelper.getFatalEvents(LOGCATEGORY);

            Assert.assertEquals(count + 1, loggedEvents.size());
            Assert.assertEquals(variance.expected, loggedEvents.get(count).message);

            Assert.assertEquals(LOGLEVELS + (LOGLEVELS * count) /* Levels we have */, LogHelper.getCountBySource(
                    LOGCATEGORY).intValue());
            count++;
        }
        Assert.assertEquals(count, LogHelper.getTraceCountBySource(LOGCATEGORY).intValue());
        Assert.assertEquals(count, LogHelper.getDebugCountBySource(LOGCATEGORY).intValue());
        Assert.assertEquals(count, LogHelper.getInfoCountBySource(LOGCATEGORY).intValue());
        Assert.assertEquals(count, LogHelper.getErrorCountBySource(LOGCATEGORY).intValue());
        Assert.assertEquals(count, LogHelper.getFatalCountBySource(LOGCATEGORY).intValue());
    }

    /**
     * This method tests the logger using simple format variations.
     */
    @Test
    public final void shouldLogInVariousLevelsUsingMsgArg() {
        Logger log = LoggerFactory.getLogger(LOGCATEGORY);
        List<LoggedEvent> loggedEvents;

        int count = 0;
        LogHelper.initializeLoggers();
        for (LoggerMsgTestData variance : getMsgVariations()) {

            log.trace(variance.message);
            loggedEvents = LogHelper.getTraceEvents(LOGCATEGORY);

            Assert.assertEquals(count + 1, loggedEvents.size());
            Assert.assertEquals(variance.expected, loggedEvents.get(count).message);

            log.debug(variance.message);
            loggedEvents = LogHelper.getDebugEvents(LOGCATEGORY);

            Assert.assertEquals(count + 1, loggedEvents.size());
            Assert.assertEquals(variance.expected, loggedEvents.get(count).message);

            log.info(variance.message);
            loggedEvents = LogHelper.getInfoEvents(LOGCATEGORY);

            Assert.assertEquals(count + 1, loggedEvents.size());
            Assert.assertEquals(variance.expected, loggedEvents.get(count).message);

            log.warn(variance.message);
            loggedEvents = LogHelper.getWarnEvents(LOGCATEGORY);

            Assert.assertEquals(count + 1, loggedEvents.size());
            Assert.assertEquals(variance.expected, loggedEvents.get(count).message);

            log.error(variance.message);
            loggedEvents = LogHelper.getErrorEvents(LOGCATEGORY);

            Assert.assertEquals(count + 1, loggedEvents.size());
            Assert.assertEquals(variance.expected, loggedEvents.get(count).message);

            log.fatal(variance.message);
            loggedEvents = LogHelper.getFatalEvents(LOGCATEGORY);

            Assert.assertEquals(count + 1, loggedEvents.size());
            Assert.assertEquals(variance.expected, loggedEvents.get(count).message);

            Assert.assertEquals(LOGLEVELS + (LOGLEVELS * count) /* Levels we have */, LogHelper.getCountBySource(
                    LOGCATEGORY).intValue());
            count++;
        }
        Assert.assertEquals(count, LogHelper.getTraceCountBySource(LOGCATEGORY).intValue());
        Assert.assertEquals(count, LogHelper.getDebugCountBySource(LOGCATEGORY).intValue());
        Assert.assertEquals(count, LogHelper.getInfoCountBySource(LOGCATEGORY).intValue());
        Assert.assertEquals(count, LogHelper.getErrorCountBySource(LOGCATEGORY).intValue());
        Assert.assertEquals(count, LogHelper.getFatalCountBySource(LOGCATEGORY).intValue());

    }

    /**
     * This class carries the expected final result as well as the test data input for Logger.
     *
     * @author oshoukry
     */
    private static final class LoggerVarArgsTestData {
        private final String expected;
        private final String message;
        private final Object[] params;

        /**
         * Full constructor.
         *
         * @param message
         *            The message to log.
         * @param params
         *            The parameters to log.
         */
        private LoggerVarArgsTestData(final String message, final Object[] params) {
            this.message = message;
            this.params = params;
            expected = MessageFormatter.format(message, params);
        }
    }

    private static final class LoggerMsgTestData {
        private final Object expected;
        private final Object message;

        /**
         * Full constructor
         *
         * @param message
         *          The message to log.
         */
        private LoggerMsgTestData(final String message) {
            this.message = message;
            expected = message;
        }
        private LoggerMsgTestData(final Throwable throwable) {
            message = throwable;
            expected = throwable.getMessage();
        }
    }
}
