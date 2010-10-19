package com.openpojo.utils.log;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Priority;
import org.apache.log4j.WriterAppender;
import org.apache.log4j.spi.LoggingEvent;

/**
 * @author oshoukry
 *
 */
public class MockAppenderLog4J extends WriterAppender {
    private static final List<LoggedEvent> EVENTS = new LinkedList<LoggedEvent>();

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
        LoggedEvent le = new LoggedEvent(event.getLoggerName(), event.getLevel(), event.getRenderedMessage());
        EVENTS.add(le);
    }

    /**
     * This method rests event counts so you can start another test. Call this before testing.
     */
    public static synchronized void restEvents() {
        EVENTS.clear();
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
    public static synchronized Integer getCountBySourceByPriority(final String source, final Priority priority) {
        int count = 0;
        for (LoggedEvent entry : EVENTS) {
            if (entry.source.equals(source) && entry.priority.equals(priority)) {
                count++;
            }
        }
        return count;
    }

    /**
     * This call is used for asserting on testing to see if you got the right counts regardless of categories.
     *
     * @param source
     *            The source of the logs
     * @return The total count on recieved events.
     */
    public static synchronized Integer getCountBySource(final String source) {
        int count = 0;
        for (LoggedEvent entry : EVENTS) {
            if (entry.source.equals(source)) {
                count++;
            }
        }
        return count;
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
    public static List<LoggedEvent> getLoggedEventsBySourceByPriority(final String source, final Priority priority) {
        List<LoggedEvent> loggedEvents = new LinkedList<LoggedEvent>();
        for (LoggedEvent entry : EVENTS) {
            if (entry.source.equals(source) && entry.priority.equals(priority)) {
                loggedEvents.add(entry);
            }
        }
        return loggedEvents;
    }
}
