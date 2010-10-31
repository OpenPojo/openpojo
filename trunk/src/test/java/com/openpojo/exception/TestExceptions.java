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

import com.openpojo.log.utils.MessageFormatter;
import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.construct.InstanceFactory;
import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.affirm.Affirm;

/**
 * @author oshoukry
 */
public class TestExceptions {

    private List<PojoClass> pojoExceptionClasses;
    private static final int EXPECTED_EXCEPTION_COUNT = 10;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        pojoExceptionClasses = PojoClassFactory.enumerateClassesByExtendingType("com.openpojo", Throwable.class, null);
    }

    @Test
    public void checkCount() {
        Affirm.affirmEquals(String.format("Classes added/removed that implement Throwable found[%s]?",
                pojoExceptionClasses), EXPECTED_EXCEPTION_COUNT, pojoExceptionClasses.size());
    }

    @Test
    public void ValidateConstructors() {
        for (PojoClass pojoExceptionClass : pojoExceptionClasses) {
            Affirm.affirmEquals(String.format(
                    "Exception class [%s] must declare 2 constructors [(string), (string, cause)] found [%s]",
                    pojoExceptionClass, pojoExceptionClass.getPojoConstructors()), 2, pojoExceptionClass
                    .getPojoConstructors().size());

            String someMessage = (String) RandomFactory.getRandomValue(String.class);
            Throwable instance = null;
            try {
                instance = (Throwable) InstanceFactory.getInstance(pojoExceptionClass, someMessage);
            } catch (ReflectionException reflectionException) {
                Affirm.fail(String.format(
                        "Failed to create Exception[%s] using (String) constructor got ReflectionException[%s]",
                        pojoExceptionClass, MessageFormatter.format(reflectionException)));
            }
            Affirm.affirmNotNull(String.format("Failed to create instance of Exception [%s]", pojoExceptionClass),
                    instance);
            Affirm.affirmEquals(String.format("Message changed in Exception[%s] using (String) constructor?!",
                    pojoExceptionClass), someMessage, instance.getMessage());

            Throwable cause = new Throwable();
            try {
                instance = (Throwable) InstanceFactory.getInstance(pojoExceptionClass, someMessage, cause);
            } catch (ReflectionException reflectionException) {
                Affirm
                        .fail(String
                                .format(
                                        "Failed to create Exception[%s] using (String, Throwable) constructor got ReflectionException[%s]",
                                        pojoExceptionClass, MessageFormatter.format(reflectionException)));
            }

            Affirm.affirmNotNull(String.format("Failed to create instance of Exception [%s]", pojoExceptionClass),
                    instance);
            Affirm.affirmEquals(String.format(
                    "Message changed in Exception[%s] using (String, Throwable) constructor?!", pojoExceptionClass),
                    someMessage, instance.getMessage());

            Affirm.affirmEquals(String.format("Cause changed in Exception[%s] using (String, Throwable) constructor??",
                    pojoExceptionClass), cause, instance.getCause());
        }
    }
}
