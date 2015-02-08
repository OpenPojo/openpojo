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

package com.openpojo.issues.issue34;

import com.openpojo.issues.issue34.sample.ClassWithBooleanFields;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.PojoValidator;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class IssueTest {

    @Test
    public void testBooleanVariations() {
        PojoClass pojoClass = PojoClassFactory.getPojoClass(ClassWithBooleanFields.class);

        Affirm.affirmEquals("Fields must be 4", 4, pojoClass.getPojoFields().size());

        int countOfbooleans = 0;
        int countOfBooleans = 0;

        for (PojoField pojoField : pojoClass.getPojoFields()) {
            if (pojoField.isPrimitive() && pojoField.getType() == boolean.class) {
                countOfbooleans++;
            }
            if (!pojoField.isPrimitive() && pojoField.getType() == Boolean.class) {
                countOfBooleans++;
            }
        }

        Affirm.affirmEquals("2 boolean fields must exist", 2, countOfbooleans);
        Affirm.affirmEquals("2 Boolean fields must exist", 2, countOfBooleans);

        PojoValidator pojoValidator = new PojoValidator();
        pojoValidator.addRule(new GetterMustExistRule());
        pojoValidator.addRule(new SetterMustExistRule());

        pojoValidator.addTester(new GetterTester());
        pojoValidator.addTester(new SetterTester());

        pojoValidator.runValidation(pojoClass);
    }
}
