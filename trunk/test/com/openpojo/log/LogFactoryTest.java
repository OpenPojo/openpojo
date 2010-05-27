package com.openpojo.log;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import utils.log.LogHelper;

/**
 * @author oshoukry
 */
public class LogFactoryTest {

    /**
     * This method does the test setup, currently initializes the loggers.
     */
    @Before
    public final void setUp() {
        LogHelper.initializeLoggers();
    }

    @Test
    public final void shouldReturnSLF4JLoggerByClass() {
        Logger log = LoggerFactory.getLogger(LogFactoryTest.class);
        Assert.assertNotNull(log);
        Assert.assertEquals("blah", log.getClass().toString());
    }

    @Test
    public final void shouldReturnSLF4JLoggerByCategory() {
        Logger log = LoggerFactory.getLogger("TestLogger");
        Assert.assertNotNull(log);
        Assert.assertEquals("blah", log.getClass().toString());
    }
}
