/**
 * Copyright (C) 2010 Osman Shoukry
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.openpojo.reflection.exception;

/**
 * This exception will be thrown whenever a problem while introspecting occurs.
 *
 * @author oshoukry
 */
public class ReflectionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Reflection Exception with message constructor
     *
     * @param message
     *            The description of the exception.
     */
    public ReflectionException(final String message) {
        super(message);
    }

    /**
     * Reflection Exception with throwable constructor.
     *
     * @param cause
     *            The root cause of this reflection exception.
     */
    public ReflectionException(final Throwable cause) {
        super(cause);
    }

    /**
     * Reflection Exception with message & throwable constructor
     *
     * @param message
     *            The description of the exception.
     * @param cause
     *            The root cause of this reflection exception.
     */
    public ReflectionException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
