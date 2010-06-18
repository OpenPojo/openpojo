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
package com.openpojo.validation.test.impl;

import com.openpojo.business.BusinessIdentity;
import com.openpojo.business.identity.BusinessValidator;
import com.openpojo.business.identity.HashCodeGenerator;
import com.openpojo.business.identity.IdentityEvaluator;
import com.openpojo.business.identity.IdentityFactory;
import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.test.Tester;
import com.openpojo.validation.utils.ValidationHelper;

/**
 * This rules ensures that object.equals(Object) and object.hashCode() are dispatching their calls to BusinessIdentity.
 * Use this tester to if you are using @BusinessKey and passing on your equals and hashCode calls to
 * {@link BusinessIdentity}.
 * This Tester is NOT thread safe.
 *
 * @author oshoukry
 */
public final class BusinessIdentityTester implements Tester {

    private Object firstPojoClassInstance;
    private Object secondPojoClassInstance;
    private final IdentityHandlerStub identityHandlerStub = new IdentityHandlerStub();

    public void run(final PojoClass pojoClass) {

        firstPojoClassInstance = ValidationHelper.getNewInstance(pojoClass);
        secondPojoClassInstance = ValidationHelper.getNewInstance(pojoClass);

        // check one way
        identityHandlerStub.areEqualReturn = (Boolean) RandomFactory.getRandomValue(Boolean.class);
        checkEquality();

        identityHandlerStub.doGenerateReturn = (Integer) RandomFactory.getRandomValue(Integer.class);
        checkHashCode();

        // reverse and check
        identityHandlerStub.doGenerateReturn = (Integer) RandomFactory.getRandomValue(Integer.class);
        checkEquality();

        identityHandlerStub.areEqualReturn = !identityHandlerStub.areEqualReturn;
        checkHashCode();

    }

    private void checkHashCode() {
        Affirm.affirmTrue(String.format("Class=[%s] not dispatching 'hashCode()' calls to BusinessIdentity",
                firstPojoClassInstance.getClass()), identityHandlerStub.doGenerateReturn == firstPojoClassInstance
                .hashCode());
    }

    private void checkEquality() {
        Affirm.affirmTrue(String.format("Class=[%s] not dispatching 'equals()' calls to BusinessIdentity",
                firstPojoClassInstance.getClass()), identityHandlerStub.areEqualReturn == firstPojoClassInstance
                .equals(secondPojoClassInstance));
    }

    private class IdentityHandlerStub implements IdentityEvaluator, HashCodeGenerator, BusinessValidator {
        private boolean areEqualReturn;
        private int doGenerateReturn;

        private IdentityEvaluator defaultIdentityEvaluator;
        private HashCodeGenerator defaultHashCodeGenerator;
        private BusinessValidator defaultBusinessValidator;

        private IdentityHandlerStub() {
            register();
        }

        public void register() {
            synchronized (IdentityFactory.class) {
                defaultIdentityEvaluator = IdentityFactory.getIdentityEvaluator();
                defaultHashCodeGenerator = IdentityFactory.getHashCodeGenerator();
                defaultBusinessValidator = IdentityFactory.getBusinessValidator();
                IdentityFactory.setBusinessValidator(this);
                IdentityFactory.setHashCodeGenerator(this);
                IdentityFactory.setIdentityEvaluator(this);
            }
        }

        public boolean areEqual(final Object first, final Object second) {
            if (alterBehaviour(first)) {
                return areEqualReturn;
            }
            return defaultIdentityEvaluator.areEqual(first, second);
        }

        public int doGenerate(final Object object) {
            if (alterBehaviour(object)) {
                return doGenerateReturn;
            }
            return defaultHashCodeGenerator.doGenerate(object);
        }

        public void validate(final Object object) {
            if (alterBehaviour(object)) {
                return;
            }
            defaultBusinessValidator.validate(object);
        }

        private boolean alterBehaviour(final Object object) {
            return object == firstPojoClassInstance || object == secondPojoClassInstance;
        }
    }

}
