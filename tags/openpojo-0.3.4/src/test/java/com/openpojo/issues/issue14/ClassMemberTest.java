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
package com.openpojo.issues.issue14;

import org.junit.Before;
import org.junit.Test;

import com.openpojo.issues.issue14.sampleclasses.SampleClass;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.PojoValidator;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.test.impl.SetterTester;
import com.openpojo.validation.test.impl.GetterTester;

/**
 * @author oshoukry
 */
public class ClassMemberTest {
    private PojoClass pojoClass;
    private PojoValidator pojoValidator;

    @Before
    public void setUp() {

        pojoClass = PojoClassFactory.getPojoClass(SampleClass.class);
        pojoValidator = new PojoValidator();

        // Add Testers to create a new instance on the private variable Class and trigger the problem.
        pojoValidator.addTester(new SetterTester());
        pojoValidator.addTester(new GetterTester());
    }

    @Test
    public void ensureSampleClassDefinitionIsCorrect() {
        String fieldName = "someMemberClass";
        Affirm.affirmEquals(String.format("Fields added/removed to [%s]?", pojoClass), 1, pojoClass.getPojoFields()
                .size());

        boolean validated = false;
        for (PojoField pojoField : pojoClass.getPojoFields()) {
            if (pojoField.getName() == fieldName) {
                Affirm.affirmEquals("Field type changed?", Class.class.getName(), pojoField.getType().getName());
                Affirm.affirmTrue(String.format("Getter/Setter removed from field[%s]", pojoField), pojoField
                        .hasGetter()
                        && pojoField.hasSetter());
                validated = true;
            }
        }
        if (!validated) {
            Affirm.fail(String.format("[%s] field not found on PojoClass [%s]", fieldName, pojoClass));
        }
    }

    @Test
    public void validatePojos() {
        pojoValidator.runValidation(pojoClass);
    }

}
