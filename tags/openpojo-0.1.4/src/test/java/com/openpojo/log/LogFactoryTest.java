package com.openpojo.log;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.openpojo.log.impl.Log4JLogger;
import com.openpojo.log.utils.ActiveLogger;

/**
 * @author oshoukry
 */
public class LogFactoryTest {
    Class<? extends Logger> defaultLoggerClass = Log4JLogger.class;

    /**
     * This method does the test setup, currently initializes the loggers.
     */
    @Before
    public final void setUp() {
        ActiveLogger.setActiveLogger(defaultLoggerClass);
    }

    @Test
    public final void shouldReturnSLF4JLoggerByClass() {
        Logger log = LoggerFactory.getLogger(LogFactoryTest.class);
        Assert.assertNotNull(log);
        Assert.assertEquals(defaultLoggerClass.getName(), log.getClass().getName());
    }

    @Test
    public final void shouldReturnSLF4JLoggerByCategory() {
        Logger log = LoggerFactory.getLogger("TestLogger");
        Assert.assertNotNull(log);
        Assert.assertEquals(defaultLoggerClass.getName(), log.getClass().getName());
    }
}
