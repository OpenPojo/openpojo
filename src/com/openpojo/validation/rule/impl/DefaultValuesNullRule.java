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

import org.junit.Assert;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.validation.rule.Rule;
import com.openpojo.validation.utils.ValidationHelper;

/**
 * This Rule ensures that all Fields are initialized to Null.
 * Exception to this rule are things defined "static final", or primitive types.
 * 
 * @author oshoukry
 */
public class DefaultValuesNullRule implements Rule {

    @Override
    public void evaluate(PojoClass pojoClass) {
        Object classInstance = null;

        classInstance = pojoClass.newInstance();
        for (PojoField fieldEntry : pojoClass.getPojoFields()) {
            if (!fieldEntry.isPrimitive() && !ValidationHelper.isStaticFinal(fieldEntry)) {
                Assert.assertNull("Expected null value for for field=[" + fieldEntry + "]", fieldEntry
                        .get(classInstance));
            }
        }
    }

}
