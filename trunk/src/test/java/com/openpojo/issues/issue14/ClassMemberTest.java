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
package com.openpojo.issues.issue14;


import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.filters.FilterPackageInfo;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.PojoValidator;
import com.openpojo.validation.test.impl.SetterTester;

/**
 * @author oshoukry
 *
 */
public class ClassMemberTest {
    private static final String POJO_PACKAGE = ClassMemberTest.class.getPackage().getName() + ".sampleclasses";
    private List<PojoClass> pojoClasses;
    private PojoValidator pojoValidator;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {

        pojoClasses = PojoClassFactory.getPojoClasses(POJO_PACKAGE, new FilterPackageInfo());

        for(PojoClass pojoClass : pojoClasses) {
            System.out.println(pojoClass);
        }

        pojoValidator = new PojoValidator();

        // Create Rules to validate structure for POJO_PACKAGE
//        pojoValidator.addRule(new NoPublicFieldsRule());
//        pojoValidator.addRule(new NoPrimitivesRule());
//        pojoValidator.addRule(new NoStaticExceptFinalRule());
//        pojoValidator.addRule(new GetterMustExistRule());
//        pojoValidator.addRule(new SetterMustExistRule());
//        pojoValidator.addRule(new NoNestedClassRule());
//        pojoValidator.addRule(new BusinessKeyMustExistRule());

        // Create Testers to validate behaviour for POJO_PACKAGE
//        pojoValidator.addTester(new DefaultValuesNullTester());
        pojoValidator.addTester(new SetterTester());
//        pojoValidator.addTester(new GetterTester());
//        pojoValidator.addTester(new BusinessIdentityTester());
    }

    @Test
    public void validatePojos() {
        for (PojoClass pojoClass : pojoClasses) {
            pojoValidator.runValidation(pojoClass);
        }
    }

}
