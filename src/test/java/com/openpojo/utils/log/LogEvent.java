package com.openpojo.utils.log;


/**
 * For testing, this structure holds the logged events.
 *
 * @author oshoukry
 *
 */
public final class LogEvent {

    public enum Priority {
        TRACE, DEBUG, INFO, WARN, ERROR, FATAL
    }

    /**
     * The source of the event.
     */
    private final String source;

    /**
     * The priority of the event.
     */
    private final Priority priority;

    /**
     * The message of the event.
     */
    private final String message;

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
    public LogEvent(final String source, final Priority priority, final String message) {
        this.source = source;
        this.priority = priority;
        this.message = message;
    }

    /**
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * @return the priority
     */
    public Priority getPriority() {
        return priority;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

}
