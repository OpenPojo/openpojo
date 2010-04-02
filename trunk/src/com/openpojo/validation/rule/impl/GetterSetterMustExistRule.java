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
import com.openpojo.validation.rule.Rule;

/**
 * This rule ensures that all Fields have a getter and setter associated with them.
 * Exception are fields defined static final since those are usually constants.
 * 
 * @author oshoukry
 * @deprecated Please use individual {@link GetterMustExistRule} & {@link SetterMustExistRule} instead.
 */
@Deprecated
public class GetterSetterMustExistRule implements Rule {

    @Override
    public void evaluate(PojoClass pojoClass) {
        new GetterMustExistRule().evaluate(pojoClass);
        new SetterMustExistRule().evaluate(pojoClass);
    }
}
