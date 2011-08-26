/**
 * Copyright (C) 2010 Osman Shoukry
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.openpojo.business.utils;

import java.util.LinkedList;
import java.util.List;

import com.openpojo.business.annotation.BusinessKey;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.reflection.impl.PojoClassFactory;

/**
 * This Utility helper class that holds the logic of extracting the {@link BusinessKey} fields from a Class.
 *
 * @author oshoukry
 */
public class BusinessPojoHelper {

    /**
     * Get all business keys declared on a class and the parent super classes.
     *
     * @param clazz
     *            The class to introspect.
     * @return
     *         The list of fields that are annotated with @BusinessKey, will return an empty list if none are found.
     */
    public static List<PojoField> getBusinessKeyFields(final Class<?> clazz) {
        List<PojoField> businessFields = new LinkedList<PojoField>();
        PojoClass pojoClass = PojoClassFactory.getPojoClass(clazz);

        List<PojoField> rawFields = new LinkedList<PojoField>();
        rawFields.addAll(pojoClass.getPojoFields());


        PojoClass parent = pojoClass.getSuperClass();
        while (parent != null) {
            rawFields.addAll(parent.getPojoFields());
            parent = parent.getSuperClass();
        }

        for (PojoField pojoField : rawFields) {
            if (pojoField.getAnnotation(BusinessKey.class) != null) {
                businessFields.add(pojoField);
            }
        }
        return businessFields;
    }

}
