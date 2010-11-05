/**
 * 2010 Copyright Osman Shoukry.
 */
package com.openpojo.log.impl;

import com.openpojo.business.BusinessIdentity;
import com.openpojo.log.Logger;

/**
 * This class wrapps the SLF4J underlying layer.
 */
public final class SLF4JLogger extends Logger {

    private final org.slf4j.Logger logger;

    private SLF4JLogger(final String category) {
        logger = org.slf4j.LoggerFactory.getLogger(category);
    }

    @Override
    public boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

    @Override
    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    @Override
    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    @Override
    public boolean isErrorEnabled() {
        return logger.isErrorEnabled();
    }

    @Override
    public boolean isFatalEnabled() {
        return logger.isErrorEnabled();
    }

    @Override
    public boolean isWarnEnabled() {
        return logger.isWarnEnabled();
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
        // SLF4J has no fatal level, so we're marking this as error
        this.error(message);
    }

    @Override
    public String toString() {
        return BusinessIdentity.toString(this);
    }

}
