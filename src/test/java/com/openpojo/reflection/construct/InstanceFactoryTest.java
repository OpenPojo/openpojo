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
package com.openpojo.reflection.construct;

import org.junit.Test;

import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.construct.sampleclasses.ClassWithLessThanGreaterThanConstructors;
import com.openpojo.reflection.construct.sampleclasses.ClassWithNativeTypesConstructor;
import com.openpojo.reflection.construct.sampleclasses.ClassWithNoDeclaredConstructor;
import com.openpojo.reflection.construct.sampleclasses.ClassWithVariousDeclaredContructorsAndMethods;
import com.openpojo.reflection.construct.sampleclasses.SomeEnum;
import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.affirm.Affirm;

/**
 * @author oshoukry
 */
public class InstanceFactoryTest {

    @Test
    public void shouldCreateUsingDefaultConstructor() {
        Class<?> clazz = ClassWithNoDeclaredConstructor.class;
        Object obj1 = getInstance(clazz, (Object[]) null);
        Affirm.affirmNotNull("Should have created an object", obj1);

        Object obj2 = getInstance(clazz, new Object[]{ });
        Affirm.affirmTrue("Should have created a different object", obj1 != obj2);
    }

    @Test
    public void shouldCreateUsingSingleParameterConstructor() {
        Class<?> clazz = ClassWithVariousDeclaredContructorsAndMethods.class;
        String stringParam = (String) RandomFactory.getRandomValue(String.class);
        ClassWithVariousDeclaredContructorsAndMethods obj1 = (ClassWithVariousDeclaredContructorsAndMethods) getInstance(
                clazz, stringParam);
        Affirm.affirmNotNull("Should have created using String constructor", obj1);
        Affirm.affirmEquals("Incorrect constructor used", stringParam, obj1.singleStringConstructor);
    }

    @Test
    public void shouldCreateUsingNullParameterDoubleConstructor() {
        Class<?> clazz = ClassWithVariousDeclaredContructorsAndMethods.class;
        String stringParam = (String) RandomFactory.getRandomValue(String.class);
        ClassWithVariousDeclaredContructorsAndMethods obj = (ClassWithVariousDeclaredContructorsAndMethods) getInstance(
                clazz, new Object[]{ stringParam, null });

        Affirm.affirmNotNull("Should have created using two parameter constructor", obj);
        Affirm.affirmNull("Should have called using two parameter constructor", obj.doubleIntegerConstructor);
        Affirm.affirmEquals("Should have called using two parameter constructor", stringParam,
                obj.doubleStringConstructor);
    }

    @Test(expected = ReflectionException.class)
    public void shouldFailtoCreateParametersMissmatch() {
        Class<?> clazz = ClassWithVariousDeclaredContructorsAndMethods.class;
        String stringParam = (String) RandomFactory.getRandomValue(String.class);
        getInstance(clazz, new Object[]{ stringParam, stringParam });
        Affirm.fail("Should've failed to create");
    }

    @Test(expected = ReflectionException.class)
    public void shouldFailtoCreateUsingDefault() {
        Class<?> clazz = ClassWithNoDeclaredConstructor.class;
        String stringParam = (String) RandomFactory.getRandomValue(String.class);
        getInstance(clazz, new Object[]{ stringParam });
        Affirm.fail("Should've failed to create");
    }

    private Object getInstance(final Class<?> clazz, final Object... parameters) {
        PojoClass pojoClass = PojoClassFactory.getPojoClass(clazz);
        return InstanceFactory.getInstance(pojoClass, parameters);
    }

    @Test
    public void shouldConstructUsingMinimalParameterCount() {
        PojoClass pojoClass = PojoClassFactory.getPojoClass(ClassWithLessThanGreaterThanConstructors.class);
        ClassWithLessThanGreaterThanConstructors instance = (ClassWithLessThanGreaterThanConstructors) InstanceFactory
                .getLeastCompleteInstance(pojoClass);
        Affirm.affirmEquals("Should've used constructor with single Parameter", 1, instance
                .getParameterCountUsedForConstruction());
    }

    @Test
    public void shouldConstructUsingMaximumParameterCount() {
        PojoClass pojoClass = PojoClassFactory.getPojoClass(ClassWithLessThanGreaterThanConstructors.class);
        ClassWithLessThanGreaterThanConstructors instance = (ClassWithLessThanGreaterThanConstructors) InstanceFactory
                .getMostCompleteInstance(pojoClass);
        Affirm.affirmEquals("Should've used constructor with single Parameter", 3, instance
                .getParameterCountUsedForConstruction());
    }

    @Test
    public void shouldConstructUsingNativeParams() {
        InstanceFactory.getMostCompleteInstance(PojoClassFactory.getPojoClass(ClassWithNativeTypesConstructor.class));
    }

    @Test(expected = ReflectionException.class)
    public void shouldFailToConstruct() {
        InstanceFactory.getInstance(PojoClassFactory.getPojoClass(SomeEnum.class));
    }

    @Test(expected = ReflectionException.class)
    public void shouldFailToConstructUsingLeastCompleteInstance() {
        InstanceFactory.getLeastCompleteInstance(PojoClassFactory.getPojoClass(SomeEnum.class));
    }

    @Test(expected = ReflectionException.class)
    public void shouldFailToConstructUsingMostCompleteInstance() {
        InstanceFactory.getMostCompleteInstance(PojoClassFactory.getPojoClass(SomeEnum.class));
    }

}
