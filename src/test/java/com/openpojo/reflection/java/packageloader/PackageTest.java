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

package com.openpojo.reflection.java.packageloader;

import org.junit.Test;

import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.PojoValidator;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.rule.impl.BusinessKeyMustExistRule;
import com.openpojo.validation.test.impl.BusinessIdentityTester;

/**
 * @author oshoukry
 */
public class PackageTest {

    @Test
    public final void testIsValid() {
        Package javaPackage = new Package(RandomFactory.getRandomValue(String.class));
        Affirm.affirmFalse("Invalid package evaluated to as valid?!", javaPackage.isValid());

        javaPackage = new Package(this.getClass().getPackage().getName());
        Affirm.affirmTrue("Valid package evaluated to as invalid?!", javaPackage.isValid());
    }

    @Test
    public final void packageShouldDispatchEqualsAndHashCodeToBusinessIdentity() {
        final PojoValidator pojoValidator = new PojoValidator();
        pojoValidator.addTester(new BusinessIdentityTester());
        pojoValidator.addRule(new BusinessKeyMustExistRule());
        pojoValidator.runValidation(PojoClassFactory.getPojoClass(Package.class));
    }

}
