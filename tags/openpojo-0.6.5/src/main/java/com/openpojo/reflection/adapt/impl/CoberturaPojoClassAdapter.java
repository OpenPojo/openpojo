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

import java.util.ArrayList;
import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.adapt.PojoClassAdapter;
import com.openpojo.reflection.impl.PojoClassImpl;

/**
 * @author oshoukry
 */
public class CoberturaPojoClassAdapter implements PojoClassAdapter {

    private static final String COBERTURA_INJECTED = "__cobertura_";

    private CoberturaPojoClassAdapter() {

    }

    public PojoClass adapt(PojoClass pojoClass) {
        final List<PojoField> cleansedPojoFields = new ArrayList<PojoField>();
        for (final PojoField pojoField : pojoClass.getPojoFields()) {
            if (!pojoField.getName().startsWith(COBERTURA_INJECTED)) {
                cleansedPojoFields.add(pojoField);
            }
        }
        final List<PojoMethod> cleansedPojoMethods = new ArrayList<PojoMethod>();
        for (final PojoMethod pojoMethod : pojoClass.getPojoMethods()) {
            if (!pojoMethod.getName().startsWith(COBERTURA_INJECTED)) {
                cleansedPojoMethods.add(pojoMethod);
            }
        }
        return new PojoClassImpl(pojoClass.getClazz(), cleansedPojoFields, cleansedPojoMethods);
    }

    public static PojoClassAdapter getInstance() {
        return Instance.INSTANCE;
    }

    private static class Instance {
        private static final PojoClassAdapter INSTANCE = new CoberturaPojoClassAdapter();
    }

}
