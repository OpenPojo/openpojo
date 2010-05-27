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
import com.openpojo.validation.test.impl.DefaultValuesNullTester;

/**
 * This Rule ensures that all Fields are initialized to null.<br>
 * This rule ignores fields that are marked as final or primitive types since neither can be initialized to null.<br>
 * <br>
 * Note:<br>
 * This logic belongs in the Tester side not the Rule side, so I am deprecating this one, and will be removing soon.<br>
 * Please switch to DefaultValuesNullTester Tester instead.<br>
 *
 * @deprecated Please use DefaultValuesNullTester instead.
 * @author oshoukry
 */
@Deprecated
public class DefaultValuesNullRule implements Rule {

    public void evaluate(final PojoClass pojoClass) {
        (new DefaultValuesNullTester()).run(pojoClass);
    }

}
