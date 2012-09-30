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

package com.openpojo.reflection.construct.utils;

/**
 * @author oshoukry
 *
 */
public abstract class ArrayLengthBasedComparator {
    public abstract boolean compare(final Class<?>[] leftHand, final Class<?>[] rightHand);

    protected int getLength(final Object[] array) {
        return array == null ? -1 : array.length;
    }
}
