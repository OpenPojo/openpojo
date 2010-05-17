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

import com.openpojo.business.BusinessIdentity;
import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.impl.sampleclasses.AClassExtendingAnInterface;
import com.openpojo.reflection.impl.sampleclasses.AClassWithEquality;
import com.openpojo.reflection.impl.sampleclasses.AClassWithExceptionalConstructors;
import com.openpojo.reflection.impl.sampleclasses.AClassWithNestedClass;
import com.openpojo.reflection.impl.sampleclasses.AClassWithSixMethods;
import com.openpojo.reflection.impl.sampleclasses.AClassWithoutMethods;
import com.openpojo.reflection.impl.sampleclasses.AConcreteClass;
import com.openpojo.reflection.impl.sampleclasses.AFinalClass;
import com.openpojo.reflection.impl.sampleclasses.ANonFinalClass;
import com.openpojo.reflection.impl.sampleclasses.AnAbstractClass;
import com.openpojo.reflection.impl.sampleclasses.AnInterfaceClass;
import com.openpojo.reflection.impl.sampleclasses.MultiplePublicAndPrivateWithManyParamsConstructor;
import com.openpojo.reflection.impl.sampleclasses.NoDeclaredConstructor;
import com.openpojo.reflection.impl.sampleclasses.OnePrivateNoParamsConstructor;
import com.openpojo.reflection.impl.sampleclasses.OnePublicNoParamConstructor;
import com.openpojo.reflection.impl.sampleclasses.AClassWithNestedClass.NestedClass;
import com.openpojo.validation.affirm.Affirm;

public class PojoClassTest {

    // @Test
    public void testPojoClassImpl() {
        Affirm.fail("Not yet implemented");
    }

    @Test
    public void testIsInterface() {
        Class<?> anInterfaceClass = AnInterfaceClass.class;
        PojoClass pojoClass = getPojoClassImplForClass(anInterfaceClass);
        Affirm.affirmTrue(String.format(
                "IsInterface on interface=[%s] returned false for PojoClass implementation=[%s]!!", anInterfaceClass,
                pojoClass), pojoClass.isInterface());
    }

    @Test
    public void testIsAbstract() {
        Class<?> anAbstractClass = AnAbstractClass.class;
        PojoClass pojoClass = getPojoClassImplForClass(anAbstractClass);
        Affirm.affirmTrue(String.format(
                "IsAbstract on abstract=[%s] returned false for PojoClass implementation=[%s]!!", anAbstractClass,
                pojoClass), pojoClass.isAbstract());
    }

    @Test
    public void testIsFinalOnFinalClass() {
        Class<?> aFinalClass = AFinalClass.class;
        PojoClass pojoClass = getPojoClassImplForClass(aFinalClass);
        Affirm.affirmTrue(String.format("IsFinal on final=[%s] returned false for PojoClass implementation=[%s]!!",
                aFinalClass, pojoClass), pojoClass.isFinal());
    }

    @Test
    public void testIsFinalOnNonFinalClass() {
        Class<?> aNonFinalClass = ANonFinalClass.class;
        PojoClass pojoClass = getPojoClassImplForClass(aNonFinalClass);
        Affirm.affirmFalse(String.format("IsFinal on non-final=[%s] returned true for PojoClass implementation=[%s]!!",
                aNonFinalClass, pojoClass), pojoClass.isFinal());
    }

    @Test
    public void testIsConcreteOnNonConcrete() {
        Class<?>[] nonConcreteClasses = { AnInterfaceClass.class, AnAbstractClass.class };
        for (Class<?> nonConcreteClass : nonConcreteClasses) {
            PojoClass pojoClass = getPojoClassImplForClass(nonConcreteClass);
            Affirm.affirmFalse(String.format(
                    "IsConcrete on non-concrete=[%s] returned true for PojoClass implementation=[%s]!!",
                    nonConcreteClass, pojoClass), pojoClass.isConcrete());
        }
    }

    @Test
    public void testIsConcreteOnConcrete() {
        Class<?>[] concreteClasses = { AConcreteClass.class };
        for (Class<?> concreteClass : concreteClasses) {
            PojoClass pojoClass = getPojoClassImplForClass(concreteClass);
            Affirm.affirmTrue(String.format(
                    "IsConcrete on concrete=[%s] returned false for PojoClass implementation=[%s]!!", concreteClass,
                    pojoClass), pojoClass.isConcrete());
        }
    }

    // @Test
    public void testGetPojoFields() {
        Affirm.fail("Not yet implemented");
    }

    @Test
    public void testGetPojoMethods() {
        PojoClass pojoClass = getPojoClassImplForClass(AClassWithSixMethods.class);
        Affirm.affirmEquals(String.format("Methods added/removed from class=[%s]", pojoClass.getName()), 6, pojoClass
                .getPojoMethods().size());

        pojoClass = getPojoClassImplForClass(AClassWithoutMethods.class);
        Affirm.affirmEquals(String.format("Methods added/removed from class=[%s]", pojoClass.getName()), 0, pojoClass
                .getPojoMethods().size());
    }

    // @Test
    public void testGetName() {
        Affirm.fail("Not yet implemented");
    }

    @Test
    public void testExtendz() {
        Class<?> aClassExtendingAnInterfaceAndAbstract = AClassExtendingAnInterface.class;
        Class<?> anInterface = AnInterfaceClass.class;

        PojoClass pojoClass = getPojoClassImplForClass(aClassExtendingAnInterfaceAndAbstract);
        Affirm.affirmTrue(String.format("Failed to validate Class=[%s] extending an interface=[%s]"
                + " for PojoClass Implementation=[%s]", aClassExtendingAnInterfaceAndAbstract, anInterface, pojoClass),
                pojoClass.extendz(anInterface));
    }

    @Test(expected = ReflectionException.class)
    public void shouldFailToCreateInstanceOnInterface() {
        PojoClass pojoClass = getPojoClassImplForClass(AnInterfaceClass.class);
        pojoClass.newInstance();
    }

    @Test(expected = ReflectionException.class)
    public void shouldFailToCreateInstanceOnAbstract() {
        PojoClass pojoClass = getPojoClassImplForClass(AnAbstractClass.class);
        pojoClass.newInstance();
    }

    @Test(expected = ReflectionException.class)
    public void shouldFailToFindAppropriateConstructor() {
        PojoClass pojoClass = getPojoClassImplForClass(MultiplePublicAndPrivateWithManyParamsConstructor.class);
        pojoClass.newInstance(new Object[]{ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 });
    }

    @Test(expected = ReflectionException.class)
    public void shouldFailToConstructBasedOnExcpetionalConstructorWithNoParam() {
        PojoClass pojoClass = getPojoClassImplForClass(AClassWithExceptionalConstructors.class);
        pojoClass.newInstance();
    }

    @Test(expected = ReflectionException.class)
    public void shouldFailToConstructBasedOnExcpetionalConstructorWithParam() {
        PojoClass pojoClass = getPojoClassImplForClass(AClassWithExceptionalConstructors.class);
        pojoClass.newInstance("OneStringParam");
    }

    @Test
    public void shouldCreateInstanceUsingDeclaredPublicConstructor() {
        PojoClass pojoClass = getPojoClassImplForClass(OnePublicNoParamConstructor.class);
        Object instance = pojoClass.newInstance();
        Affirm.affirmNotNull(String.format(
                "Failed to create a new instance using publicly declared constructor for class=[%s]", pojoClass),
                instance);
    }

    @Test
    public void shouldCreateInstanceUsingDeclaredPrivateConstructor() {
        PojoClass pojoClass = getPojoClassImplForClass(OnePrivateNoParamsConstructor.class);
        Object instance = pojoClass.newInstance();
        Affirm.affirmNotNull(String.format(
                "Failed to create a new instance using privately declared constructor for class=[%s]", pojoClass),
                instance);
    }

    @Test
    public void shouldCreateInstanceUsingImplicitConstructor() {
        PojoClass pojoClass = getPojoClassImplForClass(NoDeclaredConstructor.class);
        Object instance = pojoClass.newInstance();
        Affirm.affirmNotNull(String.format(
                "Failed to create a new instance using compiler auto-generated constructor for class=[%s]", pojoClass),
                instance);
    }

    @Test
    public void shouldCreateInstanceOneParameterConstructor() {
        PojoClass pojoClass = getPojoClassImplForClass(MultiplePublicAndPrivateWithManyParamsConstructor.class);
        Object instance = pojoClass.newInstance(RandomFactory.getRandomValue(String.class));
        Affirm.affirmNotNull(String.format(
                "Failed to create a new instance using single parameter constructor for class=[%s]", pojoClass),
                instance);
    }

    @Test
    public void shouldCreateInstanceOneNullParameterConstructor() {
        PojoClass pojoClass = getPojoClassImplForClass(MultiplePublicAndPrivateWithManyParamsConstructor.class);
        Object instance = pojoClass.newInstance((Object[]) null);
        Affirm.affirmNotNull(String.format(
                "Failed to create a new instance using single parameter constructor for class=[%s]", pojoClass),
                instance);
    }

    @Test
    public void shouldCreateInstanceMultipleParameterConstructor() {
        PojoClass pojoClass = getPojoClassImplForClass(MultiplePublicAndPrivateWithManyParamsConstructor.class);
        Object instance = pojoClass.newInstance(RandomFactory.getRandomValue(String.class), RandomFactory
                .getRandomValue(Integer.class));
        Affirm.affirmNotNull(String.format(
                "Failed to create a new instance using multiple parameter constructor for class=[%s]", pojoClass),
                instance);
    }

    @Test
    public void shouldCreateInstanceMultipleParameterPrivateConstructor() {
        PojoClass pojoClass = getPojoClassImplForClass(MultiplePublicAndPrivateWithManyParamsConstructor.class);
        Object instance = pojoClass.newInstance(RandomFactory.getRandomValue(String.class), RandomFactory
                .getRandomValue(Integer.class), RandomFactory.getRandomValue(Character.class));
        Affirm.affirmNotNull(String.format(
                "Failed to create a new instance using multiple parameter private constructor for class=[%s]",
                pojoClass), instance);
    }

    @Test(expected = ReflectionException.class)
    public void shouldFailToCreateBecauseOfParameterCountConflictMultipleParameterConstructor() {
        PojoClass pojoClass = getPojoClassImplForClass(MultiplePublicAndPrivateWithManyParamsConstructor.class);
        pojoClass.newInstance(null, null, null, null);
        Affirm.fail("Shouldn't be able to create using constructor when two are candidate based on parameter count");
    }

    @Test
    public void testIsNestedClass() {
        Class<?> nonNestedClass = AClassWithNestedClass.class;
        PojoClass pojoClass = getPojoClassImplForClass(nonNestedClass);
        Affirm.affirmFalse(String.format(
                "Non-nested class=[%s] returned true for isNestedClass on PojoClass implementation=[%s]",
                nonNestedClass, pojoClass), pojoClass.isNestedClass());

        Class<?> nestedClass = NestedClass.class;
        pojoClass = getPojoClassImplForClass(nestedClass);
        Affirm.affirmTrue(String.format(
                "Nested class=[%s] returned false for isNestedClass on PojoClass implementation=[%s]", nestedClass,
                pojoClass), pojoClass.isNestedClass());
    }

    @Test
    public void testCopy() {
        AClassWithEquality first = new AClassWithEquality((String) RandomFactory.getRandomValue(String.class),
                (Integer) RandomFactory.getRandomValue(Integer.class));

        AClassWithEquality second = new AClassWithEquality();

        Affirm.affirmFalse(String.format("Class with data=[%s], evaluated equals to one without=[%s]!!",
                BusinessIdentity.toString(first), BusinessIdentity.toString(second)), first.equals(second));

        PojoClass pojoClass = getPojoClassImplForClass(first.getClass());
        pojoClass.copy(first, second);
        Affirm.affirmTrue(String.format("Class=[%s] copied to=[%s] and still equals returned false using PojoClass"
                + " implementation=[%s]!!", BusinessIdentity.toString(first), BusinessIdentity.toString(second),
                pojoClass), first.equals(second));
    }

    @Test
    public void testGetClazz() {
        Class<?> clazz = this.getClass();
        PojoClass pojoClass = getPojoClassImplForClass(clazz);
        Affirm.affirmTrue(String.format("PojoClass parsing for [%s] returned different class=[%s] in getClazz() call"
                + " for PojoClass implementation=[%s]", clazz, pojoClass.getClazz(), pojoClass), clazz.equals(pojoClass
                .getClazz()));
    }

    // @Test
    public void testToString() {
        Affirm.fail("Not yet implemented");
    }

    // @Test
    public void testToStringObject() {
        Affirm.fail("Not yet implemented");
    }

    private PojoClassImpl getPojoClassImplForClass(final Class<?> clazz) {
        return new PojoClassImpl(clazz, PojoFieldFactory.getPojoFields(clazz), PojoMethodFactory.getPojoMethods(clazz));
    }
}
