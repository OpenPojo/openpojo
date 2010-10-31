package com.openpojo.log;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.openpojo.log.impl.Log4JLogger;
import com.openpojo.log.utils.ActiveLogger;
import com.openpojo.validation.affirm.Affirm;

/**
 * @author oshoukry
 */
public class LoggerFactoryTest {
    Class<? extends Logger> defaultLoggerClass = Log4JLogger.class;

    /**
     * This method does the test setup, currently initializes the loggers.
     */
    @Before
    public final void setUp() {
        ActiveLogger.setActiveLogger(defaultLoggerClass);
    }

    @Test
    public final void shouldReturnDefaultLoggerClassByClass() {
        Logger log = LoggerFactory.getLogger(LoggerFactoryTest.class);
        Assert.assertNotNull(log);
        Assert.assertEquals(defaultLoggerClass.getName(), log.getClass().getName());
    }

    @Test
    public final void shouldReturnDefaultLoggerClassByCategory() {
        Logger log = LoggerFactory.getLogger("TestLogger");
        Assert.assertNotNull(log);
        Assert.assertEquals(defaultLoggerClass.getName(), log.getClass().getName());
    }

    /**
     * TODO: send some logs to underlying logging framework and ensure that they are captured on the other end.
     */
    @Test
    public final void shouldReturnDefaultCategoryByClass() {
        Logger log = LoggerFactory.getLogger((Class<?>) null);
        Affirm.affirmNotNull("Null logger returned when requested with null class", log);
        log = LoggerFactory.getLogger((String) null);
        Affirm.affirmNotNull("Null logger returned when requested with null category", log);
    }
}
