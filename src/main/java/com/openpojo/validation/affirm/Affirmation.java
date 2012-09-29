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

package com.openpojo.validation.affirm;

/**
 * @author oshoukry
 *
 */
public interface Affirmation {
    public void fail(final String message);

    public void affirmTrue(final String message, final boolean condition);

    public void affirmFalse(final String message, final boolean condition);

    public void affirmNotNull(final String message, final Object object);

    public void affirmNull(final String message, final Object object);

    public void affirmEquals(final String message, final Object first, final Object second);
}
