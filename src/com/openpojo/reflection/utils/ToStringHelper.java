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

package com.openpojo.reflection.utils;

import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;

/**
 * This class is a collection of commonly used functions used for various toString(Object) calls.
 * @author oshoukry
 */
public final class ToStringHelper {
    public static final String NAME_VALUE_TOKEN_FORMAT = "%s=%s";
    public static final String LIST_TOKEN_SEPERATOR = ", ";
    public static final String POJOCLASS_TOSTRING_FORMAT = "%s [%s]";

    public static String nameValuePair(Object name, Object value) {
        return String.format(NAME_VALUE_TOKEN_FORMAT, name, value);
    }

    public static String PojoClassToString(PojoClass pojoClass, Object instance) {
        return String.format(POJOCLASS_TOSTRING_FORMAT,
                                pojoClass.getClazz().getName(),
                                PojoFieldsToString(pojoClass.getPojoFields(), instance));
    }

    private static String PojoFieldsToString(List<PojoField> pojoFields, Object instance) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int index = 0; index < pojoFields.size(); index++) {
            stringBuilder.append(pojoFields.get(index).toString(instance));
            if (index < pojoFields.size() - 1) {
                stringBuilder.append(LIST_TOKEN_SEPERATOR);
            }
        }
        return stringBuilder.toString();
    }
}
