package com.openpojo.log.utils;

import org.junit.Test;

import com.openpojo.log.Logger;
import com.openpojo.validation.affirm.Affirm;

public class ActiveLoggerTest {
    private final String[] supportedLoggers = { "com.openpojo.log.impl.SLF4JLogger",
            "com.openpojo.log.impl.Log4JLogger", "com.openpojo.log.impl.JavaLogger" };

    @Test
    public final void ensureSupportedLoggersAndOrder() {
        Affirm.affirmEquals("Supported loggers added/removed?", 3, ActiveLogger.SUPPORTED_LOGGERS.length);

        String message = "Changed supported loggers order? expected position[%s] to be [%s]";

        for (int position = 0; position < supportedLoggers.length; position++) {
            Affirm.affirmEquals(String.format(message, position, supportedLoggers[position]),
                    supportedLoggers[position], ActiveLogger.SUPPORTED_LOGGERS[position]);
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public final void shouldReturnSetLogger() throws ClassNotFoundException {
        for (String logger : supportedLoggers) {
            Class<Logger> loggerClass = (Class<Logger>) Class.forName(logger);
            ActiveLogger.setActiveLogger(loggerClass);
            Affirm.affirmTrue(String.format("Expected ActiveLogger to be set to [%s] but was [%s]", loggerClass,
                    ActiveLogger.getInstance(null)), ActiveLogger.getInstance(null).getClass().equals(loggerClass));
        }
    }
}

