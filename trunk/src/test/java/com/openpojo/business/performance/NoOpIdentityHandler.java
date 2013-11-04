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

package com.openpojo.business.performance;

import com.openpojo.business.identity.IdentityHandler;

/**
 * @author oshoukry
 */
public class NoOpIdentityHandler implements IdentityHandler {

    public int generateHashCode(Object object) {
        return -1;
    }

    public boolean areEqual(Object first, Object second) {
        return false;
    }

    public void validate(Object object) {
    }

    public boolean handlerFor(Object object) {
        return false;
    }

}