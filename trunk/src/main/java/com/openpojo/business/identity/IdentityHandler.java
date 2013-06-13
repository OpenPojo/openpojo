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

package com.openpojo.business.identity;

/**
 * @author oshoukry
 */
public interface IdentityHandler {
    /**
     * This method will calculate hash code based on the BusinessKey.
     *
     * @param object
     *            Object to generate hashCode for.
     * @return
     *         generated hash code.
     */
    public int generateHashCode(Object object);

    /**
     * This method is responsible for evaluating two objects as equal using the identity.
     *
     * @param first
     *            First object in the equality.
     * @param second
     *            Second object in the equality.
     * @return
     *         True if both objects are equal.
     */
    public boolean areEqual(final Object first, final Object second);

    /**
     * This method validates an object to comply with the BusinessKey annotation rules.
     * If an object fails the validation, a BusinessException will be thrown.
     *
     * @param object
     *            The Business Object to be validated.
     */
    public void validate(Object object);

    /**
     * This method returns true or false depending on whether this IdentityHandler handles identity for this object
     *
     * @param object
     *            Object to handle.
     * @return
     *         true if all identity calls for this object should go through this IdentityHandler.
     */
    public boolean handlerFor(Object object);
}
