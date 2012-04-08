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
package com.openpojo.validation.rule.impl;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.rule.Rule;
import com.openpojo.validation.utils.ValidationHelper;

/**
 * This Rule ensures that you aren't using any primitive type fields.
 * This is a best practice, ideally, you want to know the difference between "set/unset" and value.
 *
 * @author oshoukry
 */
public class NoPrimitivesRule implements Rule {

    public void evaluate(final PojoClass pojoClass) {
        for (PojoField fieldEntry : pojoClass.getPojoFields()) {
            if (fieldEntry.isPrimitive() && !ValidationHelper.isStaticFinal(fieldEntry)) {
                Affirm.fail(String.format(
                        "Primitive fields (byte, short, int, long, float, double, boolean, char) not allowed [%s]",
                        fieldEntry));
            }
        }
    }

}
