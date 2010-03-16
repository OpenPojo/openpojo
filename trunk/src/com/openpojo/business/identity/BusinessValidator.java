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
package com.openpojo.business.identity;

/**
 * @author oshoukry
 *
 */
public interface BusinessValidator {
    /**
     * This method validates an object to comply with the BusinessKey annotation rules.
     * If an object fails the validation, a BusinessException will be thrown.
     * @param object
     *          The Business Object to be validated.
     * @return
     *          Returns True
     */
    public void validate(Object object);
}
