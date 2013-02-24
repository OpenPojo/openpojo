/*
 * Copyright (c) 2010-2012 Osman Shoukry
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation, either version 3 of the License or any
 *   later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.openpojo.issues.issue31;

import com.openpojo.issues.issue31.sample.DirtTruck;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.PojoValidator;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class DirtTruckTest {

    @Test
    public void validate() {
        PojoClass pojoClass = PojoClassFactory.getPojoClass(DirtTruck.class);
        PojoValidator pojoValidator = new PojoValidator();
        pojoValidator.addRule(new SetterMustExistRule());
        pojoValidator.addRule(new GetterMustExistRule());
        pojoValidator.addTester(new GetterTester());
        pojoValidator.addTester(new SetterTester());

        pojoValidator.runValidation(pojoClass);
    }
}
