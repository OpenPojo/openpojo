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
package com.openpojo.exception;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.filters.FilterCloverClasses;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.affirm.Affirm;

/**
 * @author oshoukry
 */
public class TestExceptions {

    private List<PojoClass> pojoExceptionClasses;
    private static final int EXPECTED_EXCEPTION_COUNT = 10;

    @Before
    public void setUp() {
        pojoExceptionClasses = PojoClassFactory.enumerateClassesByExtendingType("com.openpojo", Throwable.class,
                                                                                new FilterCloverClasses());
    }

    @Test
    public void checkCount() {
        Affirm.affirmEquals(String.format("Classes added/removed that implement Throwable found[%s]?",
                                          pojoExceptionClasses), EXPECTED_EXCEPTION_COUNT, pojoExceptionClasses.size());
    }

    @Test
    public void ValidateConstructors() {
        for (PojoClass pojoExceptionClass : pojoExceptionClasses) {

            ensureConstructorsPrivate(pojoExceptionClass.getPojoConstructors());
            testGetInstance(pojoExceptionClass);
        }
    }

    public void ensureConstructorsPrivate(final List<PojoMethod> constructors) {

        for (PojoMethod constructor : constructors) {
            Affirm
                    .affirmTrue(String.format("Constructor must be private [%s]!!", constructor), constructor
                            .isPrivate());
        }
    }

    public void testGetInstance(final PojoClass pojoExceptionClass) {
        int getInstanceCount = 0;
        String someMessage = (String) RandomFactory.getRandomValue(String.class);
        Throwable cause = new Throwable();

        for (PojoMethod getInstance : pojoExceptionClass.getPojoMethods()) {
            if (getInstance.getName().equals("getInstance")) {
                if (getInstance.getParameterTypes().length == 1) {
                    Throwable instance = (Throwable) getInstance.invoke(null, someMessage);
                    Affirm.affirmEquals(String.format("Message changed in Exception[%s] using getInstance(String)?!",
                                                      pojoExceptionClass), someMessage, instance.getMessage());
                }

                if (getInstance.getParameterTypes().length == 2) {
                    Throwable instance = (Throwable) getInstance.invoke(null, someMessage, cause);
                    Affirm.affirmEquals(String
                            .format("Message changed in Exception[%s] using getInstance(String, Throwable)?!",
                                    pojoExceptionClass), someMessage, instance.getMessage());
                    Affirm.affirmEquals(String
                            .format("Cause changed in Exception[%s] using getInstance(String, Throwable)?!",
                                    pojoExceptionClass), cause, instance.getCause());
                }
                getInstanceCount++;
            }
        }
        Affirm.affirmEquals(String.format("getInstance methods not in line with constructors count for Exception [%s]",
                                          pojoExceptionClass), pojoExceptionClass.getPojoConstructors().size(),
                            getInstanceCount);
    }

}
