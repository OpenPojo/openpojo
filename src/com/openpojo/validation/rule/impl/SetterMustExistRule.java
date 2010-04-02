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
package com.openpojo.validation.rule.impl;


import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.rule.Rule;
import com.openpojo.validation.utils.ValidationHelper;

/**
 * This rule ensures that all Fields have a setter associated with them.
 * Exception are fields defined static final since those are usually constants.
 * 
 * @author oshoukry
 */
public class SetterMustExistRule implements Rule {

    @Override
    public void evaluate(PojoClass pojoClass) {
        for (PojoField fieldEntry : pojoClass.getPojoFields()) {
            if (!ValidationHelper.isStaticFinal(fieldEntry) && !fieldEntry.hasSetter()) {
                Affirm.fail(fieldEntry + " is missing a setter");
            }
        }
    }
}
