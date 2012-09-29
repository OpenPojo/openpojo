/*
 * Copyright (c) 2010-2012 Osman Shoukry
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation, either version 3 of the License or any
 *   later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.openpojo.utils.log;

import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.WriterAppender;
import org.apache.log4j.spi.LoggingEvent;

import com.openpojo.utils.log.LogEvent.Priority;

/**
 * @author oshoukry
 */
public class MockAppenderLog4J extends WriterAppender implements MockAppender {

    private Priority extractPriority(final LoggingEvent event) {
        if (event.getLevel().equals(Level.TRACE)) {
            return Priority.TRACE;
        }
        if (event.getLevel().equals(Level.DEBUG)) {
            return Priority.DEBUG;
        }
        if (event.getLevel().equals(Level.INFO)) {
            return Priority.INFO;
        }
        if (event.getLevel().equals(Level.WARN)) {
            return Priority.WARN;
        }
        if (event.getLevel().equals(Level.ERROR)) {
            return Priority.ERROR;
        }
        if (event.getLevel().equals(Level.FATAL)) {
            return Priority.FATAL;
        }
        throw new IllegalArgumentException("Unknown Logged Level" + event.getLevel());
    }

    @Override
    public final boolean requiresLayout() {
        return false;
    }

    @Override
    public final boolean getImmediateFlush() {
        return true;
    }

    @Override
    public final synchronized void append(final LoggingEvent event) {
        LogEvent le = new LogEvent(event.getLoggerName(), extractPriority(event), event.getRenderedMessage());
        EventLogger.registerEvent(this.getClass(), le);
    }

    /**
     * This call is used for asserting on testing to see if you got the right counts for each priority.
     *
     * @param source
     *            The source of the logs
     * @param priority
     *            The priority at which they were sent in as.
     * @return The total count on recieved events.
     */
    public synchronized Integer getCountBySourceByPriority(final String source, final Priority priority) {
        return EventLogger.getCountByAppenderBySourceByPriority(this.getClass(), source, priority);
    }

    /**
     * This call is used for asserting on testing to see if you got the right counts regardless of categories.
     *
     * @param source
     *            The source of the logs
     * @return The total count on recieved events.
     */
    public synchronized Integer getCountBySource(final String source) {
        return EventLogger.getCountBySource(this.getClass(), source);
    }

    /**
     * Get all logged events by priority for a particular source.
     *
     * @param source
     *            The source of the logs.
     * @param priority
     *            The priority at which they were sent in as.
     * @return List of logged events.
     */
    public synchronized List<LogEvent> getLoggedEventsBySourceByPriority(final String source, final Priority priority) {
        return EventLogger.getLoggedEventsByAppenderBySourceByPriority(this.getClass(), source, priority);
    }

    public void resetAppender() {
        EventLogger.resetEvents(this.getClass());
    }
}
