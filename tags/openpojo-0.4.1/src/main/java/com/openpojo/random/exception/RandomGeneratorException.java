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

package com.openpojo.random.exception;

/**
 * This Exception is thrown by the random package if an error occurs during random generation/handling.
 *
 * @author oshoukry
 */
public class RandomGeneratorException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Construct an Exception with message.
     *
     * @param message
     *            The description of the exception.
     * @return
     *         An instance of the Exception.
     */
    public static RandomGeneratorException getInstance(final String message) {
        return new RandomGeneratorException(message);
    }

    private RandomGeneratorException(final String message) {
        super(message);
    }

    /**
     * Construct an Exception with message and cause.
     *
     * @param message
     *            The description of the exception.
     * @param cause
     *            The cause of the exception.
     * @return
     *         An instance of the Exception.
     */
    public static RandomGeneratorException getInstance(final String message, final Throwable cause) {
        return new RandomGeneratorException(message, cause);
    }

    private RandomGeneratorException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
