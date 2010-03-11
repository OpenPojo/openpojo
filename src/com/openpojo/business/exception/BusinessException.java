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
package com.openpojo.business.exception;

/**
 * This is the ONLY exception thrown out of the com.osmanshoukry.business package.
 * It will wrapp any other exceptions that may occur to turn them into RunTimeException.
 * @author oshoukry
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Business Exception with message constructor
     * @param message
     *          The description of the exception.
     */
    public BusinessException(final String message) {
        super(message);
    }
    
    /**
     * Business Exception with throwable constructor.
     * @param cause
     *          The root cause of this business exception.
     */
    public BusinessException(final Throwable cause) {
        super(cause);
    }

    /**
     * Business Exception with message & throwable constructor
     * @param message
     *          The description of the exception.
     * @param cause
     *          The root cause of this business exception.
     */
    public BusinessException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
