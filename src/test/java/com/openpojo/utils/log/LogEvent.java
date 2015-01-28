/*
 * Copyright (c) 2010-2015 Osman Shoukry
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
