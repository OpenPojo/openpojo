package com.openpojo.log;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import utils.log.LogHelper;

import com.openpojo.log.impl.SLF4JLogger;

/**
 * @author oshoukry
 *
 */
public class LogFactoryTest {

    /**
     * This method does the test setup, currently initializes the loggers.
     */
    @Before
    public final void setUp() {
        LogHelper.initializeLoggers();
    }

    /**
     * Test method for {@link com.cobalt.dap.log.LogFactory#getLog(java.lang.Class)}.
     */
    @Test
    public final void shouldReturnSLF4JLoggerByClass() {
        Logger log = LoggerFactory.getLogger(LogFactoryTest.class);
        Assert.assertNotNull(log);
        Assert.assertEquals(SLF4JLogger.class.toString(), log.getClass().toString());
    }

    /**
     * Test method for {@link com.cobalt.dap.log.LogFactory#getLog(java.lang.String)}.
     */
    @Test
    public final void shouldReturnSLF4JLoggerByCategory() {
        Logger log = LoggerFactory.getLogger("TestLogger");
        Assert.assertNotNull(log);
        Assert.assertEquals(SLF4JLogger.class.toString(), log.getClass().toString());
    }
}
