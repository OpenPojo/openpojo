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
package com.openpojo.business.identity.impl;

import com.openpojo.business.identity.IdentityHandler;

/**
 * @author oshoukry
 */
public final class DefaultIdentityHandler implements IdentityHandler {
    private static final IdentityHandler INSTANCE = new DefaultIdentityHandler();

    private DefaultIdentityHandler() {

    }

    public static IdentityHandler getInstance() {
        return INSTANCE;
    }

    public boolean areEqual(final Object first, final Object second) {
        return DefaultIdentityEvaluator.getInstance().areEqual(first, second);
    }


    public int generateHashCode(final Object object) {
        return DefaultHashCodeGenerator.getInstance().doGenerate(object);
    }

    public void validate(final Object object) {
        DefaultBusinessValidator.getInstance().validate(object);
    }

    public boolean handlerFor(final Object object) {
        return true;
    }
}
