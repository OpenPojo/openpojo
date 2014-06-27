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

package com.openpojo.business.exception;

/**
 * This exception is thrown whenever a business exception occurs, i.e. an exception when performing
 * an operation in the business validation, and identity management domains. <br>
 * This is the ONLY exception thrown out of the com.openpojo.business package.
 * It will wrap any other exceptions that may occur to turn them into RuntimeException.
 *
 * @author oshoukry
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private BusinessException(final String message) {
        super(message);
    }

    private BusinessException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Construct an Exception with message.
     *
     * @param message
     *            The description of the exception.
     * @return
     *         An instance of the Exception.
     */
    public static BusinessException getInstance(final String message) {
        return new BusinessException(message);
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
    public static BusinessException getInstance(final String message, final Throwable cause) {
        return new BusinessException(message, cause);
    }
}
