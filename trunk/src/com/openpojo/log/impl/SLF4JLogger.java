/**
 * 2010 Copyright Osman Shoukry.
 */
package com.openpojo.log.impl;

import com.openpojo.business.BusinessIdentity;
import com.openpojo.log.Logger;
import com.openpojo.reflection.impl.PojoClassFactory;

/**
 * This class wrapps the Log4J underlying layer.
 * It will also configure Log4J with the most basic configuration if no configuration has been specified already.
 */
public final class SLF4JLogger extends Logger {

    private final org.slf4j.Logger logger;

    static {
        configureUnderlyingLayer();
    }

    private static void configureUnderlyingLayer() {
        try {
            PojoClassFactory.getPojoClass(Class.forName("com.openpojo.logger.impl.Log4JLogger")).newInstance(
                    SLF4JLogger.class.getName());
        } catch (Throwable ex) {
            //Not Log4J underlying perhaps.
        }

    }

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
        logger.trace(toString(message));
    }

    @Override
    public void debug(final Object message) {
        logger.debug(toString(message));
    }

    @Override
    public void info(final Object message) {
        logger.info(toString(message));
    }

    @Override
    public void warn(final Object message) {
        logger.warn(toString(message));
    }

    @Override
    public void error(final Object message) {
        logger.error(toString(message));
    }

    @Override
    public void fatal(final Object message) {
        // SLF4J has no fatal level, so we're marking this as error
        logger.error(toString(message));
    }

    private String toString(final Object message) {
        return message == null ? null : message.toString();
    }

    @Override
    public String toString() {
        return BusinessIdentity.toString(this);
    }

}
