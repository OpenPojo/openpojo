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
package com.openpojo.reflection.java;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;

/**
 * @author oshoukry
 */
public class JDKType {
    private final Type underlyingType;

    public JDKType(final Type underlyingType) {
        this.underlyingType = underlyingType;
    }

    public boolean isGeneric() {
        return (underlyingType instanceof ParameterizedType);
    }

    public boolean isWildType() {
        return (underlyingType instanceof WildcardType);
    }

    public boolean isPackage() {
        return (underlyingType instanceof Package);
    }

    public Class<?> getUnderlyingClass() {
        return underlyingType.getClass();
    }
}
