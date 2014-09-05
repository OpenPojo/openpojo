/*
 * Copyright (c) 2010-2014 Osman Shoukry
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License or any
 *    later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.openpojo.log.impl;

import com.openpojo.business.BusinessIdentity;
import com.openpojo.log.Logger;

/**
 * This class wraps the SLF4J underlying layer.
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
