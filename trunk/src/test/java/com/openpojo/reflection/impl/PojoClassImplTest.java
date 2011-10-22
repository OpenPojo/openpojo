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
package com.openpojo.reflection.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.openpojo.business.BusinessIdentity;
import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.construct.InstanceFactory;
import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.impl.sampleannotation.AnotherAnnotation;
import com.openpojo.reflection.impl.sampleannotation.SomeAnnotation;
import com.openpojo.reflection.impl.sampleclasses.AClassExtendingAnInterface;
import com.openpojo.reflection.impl.sampleclasses.AClassWithAnnotations;
import com.openpojo.reflection.impl.sampleclasses.AClassWithEquality;
import com.openpojo.reflection.impl.sampleclasses.AClassWithExceptionalConstructors;
import com.openpojo.reflection.impl.sampleclasses.AClassWithInterfaces;
import com.openpojo.reflection.impl.sampleclasses.AClassWithNestedClass;
import com.openpojo.reflection.impl.sampleclasses.AClassWithNestedClass.NestedClass;
import com.openpojo.reflection.impl.sampleclasses.AClassWithSixMethods;
import com.openpojo.reflection.impl.sampleclasses.AClassWithoutInterfaces;
import com.openpojo.reflection.impl.sampleclasses.AClassWithoutMethods;
import com.openpojo.reflection.impl.sampleclasses.AFinalClass;
import com.openpojo.reflection.impl.sampleclasses.ANonFinalClass;
import com.openpojo.reflection.impl.sampleclasses.AnAbstractClass;
import com.openpojo.reflection.impl.sampleclasses.AnInterfaceClass;
import com.openpojo.reflection.impl.sampleclasses.FirstInterfaceForAClassWithInterfaces;
import com.openpojo.reflection.impl.sampleclasses.MultiplePublicAndPrivateWithManyParamsConstructor;
import com.openpojo.reflection.impl.sampleclasses.NoDeclaredConstructor;
import com.openpojo.reflection.impl.sampleclasses.OnePrivateNoParamsConstructor;
import com.openpojo.reflection.impl.sampleclasses.OnePublicNoParamConstructor;
import com.openpojo.reflection.impl.sampleclasses.SecondInterfaceForAClassWithInterfaces;
import com.openpojo.validation.affirm.Affirm;

public class PojoClassImplTest {
    private static final String SAMPLE_CLASSES_PKG = PojoClassImplTest.class.getPackage().getName() + ".sampleclasses";

    @Test
    public void testIsInterfaceIsAbstractIsConcrete() {
        String message = "Class type check failed on [%s], actual class returned [%s], PojoClass returned [%s]!!";
        for (PojoClass pojoClass : PojoClassFactory.getPojoClassesRecursively(SAMPLE_CLASSES_PKG, null)) {
            Class<?> actualClass = pojoClass.getClazz();
            Affirm.affirmTrue(String.format(message, actualClass.getName() + ".isInterface()", actualClass
                    .isInterface(), pojoClass.isInterface()), pojoClass.isInterface() == actualClass.isInterface());
            Affirm.affirmTrue(String.format(message, actualClass.getName() + ".isAbstract()", Modifier
                    .isAbstract(actualClass.getModifiers()), pojoClass.isAbstract()),
                              pojoClass.isAbstract() == Modifier.isAbstract(actualClass.getModifiers()));

            boolean expectedValue = !(Modifier.isAbstract(actualClass.getModifiers()) || actualClass.isInterface() || actualClass
                    .isEnum());
            boolean actualValue = pojoClass.isConcrete();
            Affirm.affirmTrue(String.format(message, actualClass.getName() + ".isConcrete()", expectedValue,
                                            actualValue), actualValue == expectedValue);
        }
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
    public void testGetPojoMethods() {
        PojoClass pojoClass = getPojoClassImplForClass(AClassWithSixMethods.class);
        Affirm.affirmEquals(String.format("Methods added/removed from class=[%s]", pojoClass.getName()),
                            6 + 1 /* constructor */, pojoClass.getPojoMethods().size());

        pojoClass = getPojoClassImplForClass(AClassWithoutMethods.class);
        Affirm.affirmEquals(String.format("Methods added/removed from class=[%s]", pojoClass.getName()),
                            0 + 1 /* constructor */, pojoClass.getPojoMethods().size());
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

    @Test
    public void testGetAnnotations() {
        Class<?> aClassWithAnnotations = AClassWithAnnotations.class;

        PojoClass pojoClass = getPojoClassImplForClass(aClassWithAnnotations);
        Affirm.affirmEquals(String.format("Annotations added/removed from Class=[%s]", aClassWithAnnotations), 2,
                            pojoClass.getAnnotations().size());
    }

    @Test
    public void multipleAnnotationsShouldBeReturned() {
        Class<?> aClassWithAnnotations = AClassWithAnnotations.class;

        PojoClass pojoClass = getPojoClassImplForClass(aClassWithAnnotations);
        Affirm.affirmEquals(String.format("Annotations added/removed from Class=[%s]", aClassWithAnnotations), 2,
                            pojoClass.getAnnotations().size());

        List<Class<?>> expectedAnnotations = new LinkedList<Class<?>>();
        expectedAnnotations.add(SomeAnnotation.class);
        expectedAnnotations.add(AnotherAnnotation.class);
        for (Annotation annotation : pojoClass.getAnnotations()) {
            Affirm.affirmTrue(String.format("Expected annotations [%s] not found, instead found [%s]",
                                            expectedAnnotations, annotation.annotationType()), expectedAnnotations
                    .contains(annotation.annotationType()));
        }
    }

    @Test(expected = ReflectionException.class)
    public void shouldFailToCreateInstanceOnInterface() {
        PojoClass pojoClass = getPojoClassImplForClass(AnInterfaceClass.class);
        InstanceFactory.getInstance(pojoClass);
    }

    @Test(expected = ReflectionException.class)
    public void shouldFailToCreateInstanceOnAbstract() {
        PojoClass pojoClass = getPojoClassImplForClass(AnAbstractClass.class);
        InstanceFactory.getInstance(pojoClass);
    }

    @Test(expected = ReflectionException.class)
    public void shouldFailToFindAppropriateConstructor() {
        PojoClass pojoClass = getPojoClassImplForClass(MultiplePublicAndPrivateWithManyParamsConstructor.class);
        InstanceFactory.getInstance(pojoClass, (new Object[]{ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }));
    }

    @Test(expected = ReflectionException.class)
    public void shouldFailToConstructBasedOnExcpetionalConstructorWithNoParam() {
        PojoClass pojoClass = getPojoClassImplForClass(AClassWithExceptionalConstructors.class);
        InstanceFactory.getInstance(pojoClass);
    }

    @Test(expected = ReflectionException.class)
    public void shouldFailToConstructBasedOnExcpetionalConstructorWithParam() {
        PojoClass pojoClass = getPojoClassImplForClass(AClassWithExceptionalConstructors.class);
        InstanceFactory.getInstance(pojoClass, ("OneStringParam"));
    }

    @Test
    public void shouldCreateInstanceUsingDeclaredPublicConstructor() {
        PojoClass pojoClass = getPojoClassImplForClass(OnePublicNoParamConstructor.class);
        Object instance = InstanceFactory.getInstance(pojoClass);
        Affirm
                .affirmNotNull(String
                        .format("Failed to create a new instance using publicly declared constructor for class=[%s]",
                                pojoClass), instance);
    }

    @Test
    public void shouldCreateInstanceUsingDeclaredPrivateConstructor() {
        PojoClass pojoClass = getPojoClassImplForClass(OnePrivateNoParamsConstructor.class);
        Object instance = InstanceFactory.getInstance(pojoClass);
        Affirm.affirmNotNull(String
                .format("Failed to create a new instance using privately declared constructor for class=[%s]",
                        pojoClass), instance);
    }

    @Test
    public void shouldCreateInstanceUsingImplicitConstructor() {
        PojoClass pojoClass = getPojoClassImplForClass(NoDeclaredConstructor.class);
        Object instance = InstanceFactory.getInstance(pojoClass);

        Affirm.affirmNotNull(String
                .format("Failed to create a new instance using compiler auto-generated constructor for class=[%s]",
                        pojoClass), instance);
    }

    @Test
    public void shouldCreateInstanceOneParameterConstructor() {
        PojoClass pojoClass = getPojoClassImplForClass(MultiplePublicAndPrivateWithManyParamsConstructor.class);
        Object instance = InstanceFactory.getInstance(pojoClass, RandomFactory.getRandomValue(String.class));
        Affirm
                .affirmNotNull(String
                        .format("Failed to create a new instance using single parameter constructor for class=[%s]",
                                pojoClass), instance);
    }

    @Test
    public void shouldCreateInstanceOneNullParameterConstructor() {
        PojoClass pojoClass = getPojoClassImplForClass(MultiplePublicAndPrivateWithManyParamsConstructor.class);
        Object instance = InstanceFactory.getInstance(pojoClass, (new Object[]{ null }));
        Affirm
                .affirmNotNull(String
                        .format("Failed to create a new instance using single parameter constructor for class=[%s]",
                                pojoClass), instance);
    }

    @Test
    public void shouldCreateInstanceMultipleParameterConstructor() {
        PojoClass pojoClass = getPojoClassImplForClass(MultiplePublicAndPrivateWithManyParamsConstructor.class);
        Object instance = InstanceFactory.getInstance(pojoClass, RandomFactory.getRandomValue(String.class),
                                                      RandomFactory.getRandomValue(Integer.class));
        Affirm.affirmNotNull(String
                .format("Failed to create a new instance using multiple parameter constructor for class=[%s]",
                        pojoClass), instance);
    }

    @Test
    public void shouldCreateInstanceMultipleParameterPrivateConstructor() {
        PojoClass pojoClass = getPojoClassImplForClass(MultiplePublicAndPrivateWithManyParamsConstructor.class);
        Object instance = InstanceFactory.getInstance(pojoClass, RandomFactory.getRandomValue(String.class),
                                                      RandomFactory.getRandomValue(Integer.class), RandomFactory
                                                              .getRandomValue(Character.class));
        Affirm.affirmNotNull(String
                .format("Failed to create a new instance using multiple parameter private constructor for class=[%s]",
                        pojoClass), instance);
    }

    @Test
    public void testIsNestedClass() {
        Class<?> nonNestedClass = AClassWithNestedClass.class;
        PojoClass pojoClass = getPojoClassImplForClass(nonNestedClass);
        Affirm.affirmFalse(String
                .format("Non-nested class=[%s] returned true for isNestedClass on PojoClass implementation=[%s]",
                        nonNestedClass, pojoClass), pojoClass.isNestedClass());

        Class<?> nestedClass = NestedClass.class;
        pojoClass = getPojoClassImplForClass(nestedClass);
        Affirm.affirmTrue(String
                .format("Nested class=[%s] returned false for isNestedClass on PojoClass implementation=[%s]",
                        nestedClass, pojoClass), pojoClass.isNestedClass());
    }

    @Test
    public void testCopy() {
        AClassWithEquality first = new AClassWithEquality((String) RandomFactory.getRandomValue(String.class),
                (Integer) RandomFactory.getRandomValue(Integer.class));

        AClassWithEquality second = new AClassWithEquality();

        Affirm.affirmFalse(String.format("Class with data=[%s], evaluated equals to one without=[%s]!!",
                                         BusinessIdentity.toString(first), BusinessIdentity.toString(second)), first
                .equals(second));

        PojoClass pojoClass = getPojoClassImplForClass(first.getClass());
        pojoClass.copy(first, second);
        Affirm.affirmTrue(String.format("Class=[%s] copied to=[%s] and still equals returned false using PojoClass"
                + " implementation=[%s]!!", BusinessIdentity.toString(first), BusinessIdentity.toString(second),
                                        pojoClass), first.equals(second));
    }

    @Test
    public void shouldGetEmptyListForGetInterfaces() {
        PojoClass pojoClass = getPojoClassImplForClass(AClassWithoutInterfaces.class);
        Affirm.affirmNotNull(String.format("Expected empty list no null for getInterfaces() on [%s]?", pojoClass),
                             pojoClass.getInterfaces());
        Affirm.affirmEquals(String.format("Interfaces added to [%s]?", pojoClass), 0, pojoClass.getInterfaces().size());
    }

    @Test
    public void shouldGetInterfaces() {
        PojoClass pojoClass = getPojoClassImplForClass(AClassWithInterfaces.class);
        Affirm.affirmEquals(String.format("Interfaces added/removed from [%s]?", pojoClass), 2, pojoClass
                .getInterfaces().size());

        List<Class<?>> expectedInterfaces = new LinkedList<Class<?>>();
        expectedInterfaces.add(FirstInterfaceForAClassWithInterfaces.class);
        expectedInterfaces.add(SecondInterfaceForAClassWithInterfaces.class);
        for (PojoClass pojoInterface : pojoClass.getInterfaces()) {
            Affirm.affirmTrue(String.format("Expected interfaces [%s] not found, instead found [%s]",
                                            expectedInterfaces, pojoInterface.getClazz()), expectedInterfaces
                    .contains(pojoInterface.getClazz()));
        }
    }

    @Test
    public void testGetClazz() {
        Class<?> clazz = this.getClass();
        PojoClass pojoClass = getPojoClassImplForClass(clazz);
        Affirm.affirmTrue(String.format("PojoClass parsing for [%s] returned different class=[%s] in getClazz() call"
                + " for PojoClass implementation=[%s]", clazz, pojoClass.getClazz(), pojoClass), clazz.equals(pojoClass
                .getClazz()));
    }

    @Test
    public void testEqualityAndHashCodeBasedOnIdentityNotInstance() {
        PojoClass first = getPojoClassImplForClass(this.getClass());
        PojoClass second = getPojoClassImplForClass(this.getClass());
        Affirm.affirmEquals("PojoClassImpl equals is instance based!! Should be business equality based.", first, second);
        Affirm.affirmEquals("PojoClassImpl hashCode is instance based!! Should be business equality based.", first.hashCode(), second.hashCode());
    }

    private PojoClassImpl getPojoClassImplForClass(final Class<?> clazz) {
        return new PojoClassImpl(clazz, PojoFieldFactory.getPojoFields(clazz), PojoMethodFactory.getPojoMethods(clazz));
    }
}
