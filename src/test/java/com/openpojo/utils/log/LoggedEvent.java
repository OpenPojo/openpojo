package com.openpojo.utils.log;

import org.apache.log4j.Priority;

/**
 * For testing, this structure holds the logged events.
 *
 * @author oshoukry
 *
 */
public final class LoggedEvent {

    /**
     * The source of the event.
     */
    public final String source;

    /**
     * The priority of the event.
     */
    public final Priority priority;

    /**
     * The message of the event.
     */
    public final String message;

    /**
     * Constructor making this class immutable.
     *
     * @param source
     *            The source of the event.
     * @param priority
     *            The priority of the event.
     * @param message
     *            The message of the event.
     */
    public LoggedEvent(final String source, final Priority priority, final String message) {
        this.source = source;
        this.priority = priority;
        this.message = message;
    }

}
