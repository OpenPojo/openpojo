/*
 * Copyright (c) 2010-2014 Osman Shoukry
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

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.rule.Rule;
import com.openpojo.validation.utils.ValidationHelper;

/**
 * This rule ensures that no fields declared with public visibility unless they
 * are static and final It is best to non-POJO classes (Interfaces, Enums,
 * ...etc.) to hold those instead of mixing responsibilities.
 *
 * @author oshoukry
 */
public final class NoPublicFieldsExceptStaticFinalRule implements Rule {
    public void evaluate(final PojoClass pojoClass) {
        for (PojoField fieldEntry : pojoClass.getPojoFields()) {
            if (fieldEntry.isPublic()
                    && !ValidationHelper.isStaticFinal(fieldEntry)) {
                Affirm.fail(String.format(
                        "Non 'static final' Public fields=[%s] not allowed",
                        fieldEntry));
            }
        }
    }

}
