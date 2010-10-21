/**
 * 2010 Copyright Osman Shoukry.
 */
package com.openpojo.log.impl;

import com.openpojo.business.BusinessIdentity;
import com.openpojo.log.Logger;

/**
 * This class wrapps the Log4J underlying layer.
 * It will also configure Log4J with the most basic configuration if no configuration has been specified already.
 */
public final class Log4JLogger extends Logger {

    private final org.apache.log4j.Logger logger;

    static {
        if (!configured()) {
            // Configure log4J with the basic configuration.
            org.apache.log4j.BasicConfigurator.configure();
        }
    }

    private static boolean configured() {
        return org.apache.log4j.Logger.getRootLogger().getAllAppenders().hasMoreElements();
    }

    private Log4JLogger(final String category) {
        logger = org.apache.log4j.Logger.getLogger(category);
    }

    @Override
    public boolean isTraceEnabled() {
        return logger.isEnabledFor(org.apache.log4j.Level.TRACE);
    }

    @Override
    public boolean isDebugEnabled() {
        return logger.isEnabledFor(org.apache.log4j.Level.DEBUG);
    }

    @Override
    public boolean isInfoEnabled() {
        return logger.isEnabledFor(org.apache.log4j.Level.INFO);
    }

    @Override
    public boolean isErrorEnabled() {
        return logger.isEnabledFor(org.apache.log4j.Level.ERROR);
    }

    @Override
    public boolean isFatalEnabled() {
        return logger.isEnabledFor(org.apache.log4j.Level.FATAL);
    }

    @Override
    public boolean isWarnEnabled() {
        return logger.isEnabledFor(org.apache.log4j.Level.WARN);
    }

    @Override
    public void trace(final Object message) {
        logger.trace(format(message));
    }

    @Override
    public void debug(final Object message) {
        logger.debug(format(message));
    }

    @Override
    public void info(final Object message) {
        logger.info(format(message));
    }

    @Override
    public void warn(final Object message) {
        logger.warn(format(message));
    }

    @Override
    public void error(final Object message) {
        logger.error(format(message));
    }

    @Override
    public void fatal(final Object message) {
            logger.fatal(format(message));
    }

    @Override
    public String toString() {
        return BusinessIdentity.toString(this);
    }

}
