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
package com.openpojo.reflection.impl;

import org.junit.Test;

import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.impl.sampleclasses.MultiplePublicAndPrivateWithManyParamsConstructor;
import com.openpojo.reflection.impl.sampleclasses.NoDeclaredConstructor;
import com.openpojo.reflection.impl.sampleclasses.OnePrivateNoParamsConstructor;
import com.openpojo.reflection.impl.sampleclasses.OnePublicNoParamConstructor;
import com.openpojo.validation.affirm.Affirm;

public class PojoClassTest {

    // @Test
    public void testPojoClassImpl() {
        Affirm.fail("Not yet implemented");
    }

    // @Test
    public void testIsInterface() {
        Affirm.fail("Not yet implemented");
    }

    // @Test
    public void testIsAbstract() {
        Affirm.fail("Not yet implemented");
    }

    // @Test
    public void testGetPojoFields() {
        Affirm.fail("Not yet implemented");
    }

    // @Test
    public void testGetName() {
        Affirm.fail("Not yet implemented");
    }

    // @Test
    public void testExtendz() {
        Affirm.fail("Not yet implemented");
    }

    // @Test
    public void testValidateBeforeNewInstance() {
        Affirm.fail("Not yet implemented");
    }

    // @Test
    public void testNewInstanceObjectArray() {
        Affirm.fail("Not yet implemented");
    }

    @Test
    public void shouldCreateInstanceUsingDeclaredPublicConstructor() {
        PojoClass pojoClass = PojoClassFactory.getPojoClass(OnePublicNoParamConstructor.class);
        Object instance = pojoClass.newInstance();
        Affirm.affirmNotNull(String.format(
                "Failed to create a new instance using publicly declared constructor for class=[%s]", pojoClass),
                instance);
    }

    @Test
    public void shouldCreateInstanceUsingDeclaredPrivateConstructor() {
        PojoClass pojoClass = PojoClassFactory.getPojoClass(OnePrivateNoParamsConstructor.class);
        Object instance = pojoClass.newInstance();
        Affirm.affirmNotNull(String.format(
                "Failed to create a new instance using privately declared constructor for class=[%s]", pojoClass),
                instance);
    }

    @Test
    public void shouldCreateInstanceUsingImplicitConstructor() {
        PojoClass pojoClass = PojoClassFactory.getPojoClass(NoDeclaredConstructor.class);
        Object instance = pojoClass.newInstance();
        Affirm.affirmNotNull(String.format(
                "Failed to create a new instance using compiler auto-generated constructor for class=[%s]", pojoClass),
                instance);
    }

    @Test
    public void shouldCreateInstanceOneParameterConstructor() {
        PojoClass pojoClass = PojoClassFactory.getPojoClass(MultiplePublicAndPrivateWithManyParamsConstructor.class);
        Object instance = pojoClass.newInstance(RandomFactory.getRandomValue(String.class));
        Affirm.affirmNotNull(String.format(
                "Failed to create a new instance using single parameter constructor for class=[%s]", pojoClass),
                instance);
    }

    @Test
    public void shouldCreateInstanceOneNullParameterConstructor() {
        PojoClass pojoClass = PojoClassFactory.getPojoClass(MultiplePublicAndPrivateWithManyParamsConstructor.class);
        Object instance = pojoClass.newInstance((Object[]) null);
        Affirm.affirmNotNull(String.format(
                "Failed to create a new instance using single parameter constructor for class=[%s]", pojoClass),
                instance);
    }

    @Test
    public void shouldCreateInstanceMultipleParameterConstructor() {
        PojoClass pojoClass = PojoClassFactory.getPojoClass(MultiplePublicAndPrivateWithManyParamsConstructor.class);
        Object instance = pojoClass.newInstance(RandomFactory.getRandomValue(String.class), RandomFactory
                .getRandomValue(Integer.class));
        Affirm.affirmNotNull(String.format(
                "Failed to create a new instance using multiple parameter constructor for class=[%s]", pojoClass),
                instance);
    }

    @Test
    public void shouldCreateInstanceMultipleParameterPrivateConstructor() {
        PojoClass pojoClass = PojoClassFactory.getPojoClass(MultiplePublicAndPrivateWithManyParamsConstructor.class);
        Object instance = pojoClass.newInstance(RandomFactory.getRandomValue(String.class), RandomFactory
                .getRandomValue(Integer.class), RandomFactory.getRandomValue(Character.class));
        Affirm.affirmNotNull(String.format(
                "Failed to create a new instance using multiple parameter private constructor for class=[%s]", pojoClass),
                instance);
    }

    @Test(expected = ReflectionException.class)
    public void shouldFailToCreateBecauseOfParameterCountConflictMultipleParameterConstructor() {
        PojoClass pojoClass = PojoClassFactory.getPojoClass(MultiplePublicAndPrivateWithManyParamsConstructor.class);
        pojoClass.newInstance(null, null, null, null);
        Affirm.fail("Shouldn't be able to create using constructor when two are candidate based on parameter count");
    }

    // @Test
    public void testIsNestedClass() {
        Affirm.fail("Not yet implemented");
    }

    // @Test
    public void testCopy() {
        Affirm.fail("Not yet implemented");
    }

    // @Test
    public void testGetClazz() {
        Affirm.fail("Not yet implemented");
    }

    // @Test
    public void testToString() {
        Affirm.fail("Not yet implemented");
    }

    // @Test
    public void testToStringObject() {
        Affirm.fail("Not yet implemented");
    }

}
