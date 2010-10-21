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

import com.openpojo.business.annotation.BusinessKey;

/**
 * This interface defines the business equality contract between two POJOs.
 * Two POJOs are defined to be the same if all their {@link BusinessKey}s are equal.
 *
 * @author oshoukry
 */
public interface IdentityEvaluator {

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
}
