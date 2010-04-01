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

package com.openpojo.random.exception;

/**
 * This Exception is thrown by the random package if an error occurs during random generation/handling.
 * 
 * @author oshoukry
 */
public class RandomGeneratorException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor using the message that describes the failure.
     * 
     * @param message
     *            String message of the error that occured.
     */
    public RandomGeneratorException(final String message) {
        super(message);
    }

}
