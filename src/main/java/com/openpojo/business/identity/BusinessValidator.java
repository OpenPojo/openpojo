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

package com.openpojo.business.identity;

/**
 * This Interface defines the contract for business validation on an object.
 * Business validation ensures that objects is a valid business object for use.
 *
 * @author oshoukry
 */
public interface BusinessValidator {
    /**
     * This method validates an object to comply with the BusinessKey annotation rules.
     * If an object fails the validation, a BusinessException will be thrown.
     *
     * @param object
     *          The Business Object to be validated.
     */
    public void validate(Object object);
}
