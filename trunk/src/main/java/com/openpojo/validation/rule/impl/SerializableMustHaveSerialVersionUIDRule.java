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

package com.openpojo.validation.rule.impl;

import java.io.Serializable;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.rule.Rule;

/**
 * This Rule ensures that all Serializable classes define "serialVersionUID".
 * @author oshoukry
 */
public class SerializableMustHaveSerialVersionUIDRule implements Rule {
    private static final String SERIAL_VERSION_UID = "serialVersionUID";

    public void evaluate(final PojoClass pojoClass) {
        if (pojoClass.extendz(Serializable.class)) {
            for (PojoField pojoField : pojoClass.getPojoFields()) {
                if (pojoField.getName().equalsIgnoreCase(SERIAL_VERSION_UID)) {
                    if (!pojoField.getName().equals(SERIAL_VERSION_UID)) {
                        Affirm.affirmEquals(String.format(
                                "Case miss-match on serialVersionUID field on Serializable class [%s]", pojoClass),
                                SERIAL_VERSION_UID, pojoField.getName());
                    }
                    if (!(pojoField.isStatic() && pojoField.isFinal())) {
                        Affirm.fail(String.format("[%s] must be defined as [static final] on Serializable class [%s]",
                                SERIAL_VERSION_UID, pojoClass));
                    }
                    if (pojoField.getType() != long.class) {
                        Affirm.fail(String.format("[%s] must be defined as [long] on Serializable class [%s]",
                                SERIAL_VERSION_UID, pojoClass));

                    }
                    return;
                }
            }
            Affirm.fail(String
                    .format("No [%s] field defined on Serializable class [%s]", SERIAL_VERSION_UID, pojoClass));
        }
    }

}
