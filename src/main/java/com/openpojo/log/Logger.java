/*
 * Copyright (c) 2010-2013 Osman Shoukry
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

package com.openpojo.log;

import com.openpojo.log.utils.MessageFormatter;

/**
 * The main logger interface that all code should use to communicate log messages. See java.text.MessageFormat <br>
 * For example:
 *
 * <pre>
 * Logger log = Logger.getLog(MyClass.class);
 * log.info(&quot;This is my {0}, message for {1}.&quot;, &quot;test&quot;, new Date());
 * </pre>
 *
 * Would log something like:
 *
 * <pre>
 *      MyClass: INFO - This is my test message for 12/01/2009.
 * </pre>
 */
public abstract class Logger {

    /**
     * Returns true if the underlying log API's trace is enabled.
     *
     * @return returns true if trace is enabled.
     */
    public abstract boolean isTraceEnabled();

    /**
     * Returns true if the underlying log API's debug is enabled.
     *
     * @return returns true if debug is enabled.
     */
    public abstract boolean isDebugEnabled();

    /**
     * Returns true if the underlying log API's info is enabled.
     *
     * @return returns true if info is enabled.
     */
    public abstract boolean isInfoEnabled();

    /**
     * Returns true if the underlying log API's warn is enabled.
     *
     * @return returns true if warn is enabled.
     */
    public abstract boolean isWarnEnabled();

    /**
     * Returns true if the underlying log API's error is enabled.
     *
     * @return returns true if error is enabled.
     */
    public abstract boolean isErrorEnabled();

    /**
     * Returns true if the underlying log API's fatal is enabled.
     *
     * @return returns true if fatal is enabled.
     */
    public abstract boolean isFatalEnabled();

    /**
     * Logs a message with trace priority.
     *
     * @param message
     *            The message to log.
     * @param args
     *            The arguments to use in text replacement.
     */
    public final void trace(final String message, final Object... args) {
        if (isTraceEnabled()) {
            trace(format(message, args));
        }
    }

    /**
     * Logs a message with debug priority.
     *
     * @param message
     *            The message to log.
     * @param args
     *            The arguments to use in text replacement.
     */
    public final void debug(final String message, final Object... args) {
        if (isDebugEnabled()) {
            debug(format(message, args));
        }
    }

    /**
     * Logs a message with info priority.
     *
     * @param message
     *            The message to log.
     * @param args
     *            The arguments to use in text replacement.
     */
    public final void info(final String message, final Object... args) {
        if (isInfoEnabled()) {
            info(format(message, args));
        }
    }

    /**
     * Logs a message with warn priority.
     *
     * @param message
     *            The message to log.
     * @param args
     *            The arguments to use in text replacement.
     */
    public final void warn(final String message, final Object... args) {
        if (isWarnEnabled()) {
            warn(format(message, args));
        }
    }

    /**
     * Logs a message with error priority.
     *
     * @param message
     *            The message to log.
     * @param args
     *            The arguments to use in text replacement.
     */
    public final void error(final String message, final Object... args) {
        if (isErrorEnabled()) {
            error(format(message, args));
        }
    }

    /**
     * Logs a message with fatal priority.
     *
     * @param message
     *            The message to log.
     * @param args
     *            The arguments to use in text replacement.
     */
    public final void fatal(final String message, final Object... args) {
        if (isFatalEnabled()) {
            fatal(format(message, args));
        }
    }

    /**
     * Logs the object's toString() with trace priority.
     *
     * @param arg
     *            The object to log.
     */
    public abstract void trace(final Object arg);

    /**
     * Logs the object's toString() with debug priority.
     *
     * @param message
     *            The object to log.
     */
    public abstract void debug(final Object message);

    /**
     * Logs the object's toString() with info priority.
     *
     * @param message
     *            The object to log.
     */
    public abstract void info(final Object message);

    /**
     * Logs the object's toString() with warn priority.
     *
     * @param message
     *            The object to log.
     */
    public abstract void warn(final Object message);

    /**
     * Logs the object's toString() with error priority.
     *
     * @param message
     *            The object to log.
     */
    public abstract void error(final Object message);

    /**
     * Logs the object's toString() with fatal priority.
     *
     * @param message
     *            The object to log.
     */
    public abstract void fatal(final Object message);

    /**
     * This is the Logger message formatter that utilizes external formatting string util. It will use a default message
     * if its ever called with "null" params.
     *
     * @param message
     * @param args
     * @return
     *          A string format for message.
     */
    private String format(final String message, final Object... args) {
        return MessageFormatter.format(message, args);
    }

    /**
     * Take a simple log message object and turn it into a string.
     *
     * @param message
     *          The message to log.
     * @return
     *          A string format for message.
     */
    protected String format(final Object message) {
        return MessageFormatter.format(message);
    }
}
