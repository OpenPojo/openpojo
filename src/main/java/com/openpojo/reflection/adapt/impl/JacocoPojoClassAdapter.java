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

package com.openpojo.reflection.adapt.impl;

import java.util.LinkedList;
import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.adapt.PojoClassAdapter;
import com.openpojo.reflection.impl.PojoClassImpl;

/**
 * This Adapter will strip out the fields and methods that related to jacoco.
 *
 * @author oshoukry
 */
public class JacocoPojoClassAdapter implements PojoClassAdapter {
    private static final String JACOCO_FIELD_NAME = "$jacocoData";
    private static final String JACOCO_METHOD_NAME = "$jacocoInit";

    private JacocoPojoClassAdapter() {
    }

    public static PojoClassAdapter getInstance() {
        return Instance.INSTANCE;
    }

    public PojoClass adapt(final PojoClass pojoClass) {
        final List<PojoField> cleansedPojoFields = new LinkedList<PojoField>();
        for (final PojoField pojoField : pojoClass.getPojoFields()) {
            if (!pojoField.getName().equals(JACOCO_FIELD_NAME)) {
                cleansedPojoFields.add(pojoField);
            }
        }
        final List<PojoMethod> cleansedPojoMethods = new LinkedList<PojoMethod>();
        for (final PojoMethod pojoMethod : pojoClass.getPojoMethods()) {
            if (!pojoMethod.getName().equals(JACOCO_METHOD_NAME)) {
                cleansedPojoMethods.add(pojoMethod);
            }
        }
        return new PojoClassImpl(pojoClass.getClazz(), cleansedPojoFields, cleansedPojoMethods);
    }

    private static class Instance {
        private static final PojoClassAdapter INSTANCE = new JacocoPojoClassAdapter();
    }
}
