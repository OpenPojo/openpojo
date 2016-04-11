/*
 * Copyright (c) 2010-2016 Osman Shoukry
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.openpojo.reflection.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;

import com.openpojo.business.BusinessIdentity;
import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.PojoPackage;
import com.openpojo.reflection.construct.InstanceFactory;
import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.impl.sample.annotation.AnotherAnnotation;
import com.openpojo.reflection.impl.sample.annotation.SomeAnnotation;
import com.openpojo.reflection.impl.sample.classes.*;
import com.openpojo.reflection.impl.sample.classes.AClassWithNestedClass.NestedClass;
import com.openpojo.reflection.java.Java;
import com.openpojo.validation.affirm.Affirm;
import org.junit.Assert;
import org.junit.Test;

/*
 * TODO: This test class needs to be re-worked, to focus on just the PojoClassImpl not across services, i.e.
 * PojoClassFactory, InstanceFactory, etc...
 */
public class PojoClassImplTest {
  private static final String SAMPLE_CLASSES_PKG = PojoClassImplTest.class.getPackage().getName() + ".sample.classes";

  @Test
  public void testIsInterfaceIsAbstractIsConcrete() {
    final String message = "Class type check failed on [%s], actual class returned [%s], PojoClass returned [%s]!!";
    for (final PojoClass pojoClass : PojoClassFactory.getPojoClassesRecursively(SAMPLE_CLASSES_PKG, null)) {
      final Class<?> actualClass = pojoClass.getClazz();
      Affirm.affirmTrue(String.format(message, actualClass.getName() + ".isInterface()", actualClass.isInterface(),
          pojoClass.isInterface()), pojoClass.isInterface() == actualClass.isInterface());
      Affirm.affirmTrue(String.format(message, actualClass.getName() + ".isAbstract()",
          Modifier.isAbstract(actualClass.getModifiers())
              && !Modifier.isInterface(actualClass.getModifiers()), pojoClass.isAbstract()),
          pojoClass.isAbstract() == (Modifier.isAbstract(actualClass.getModifiers())
              && !Modifier.isInterface(actualClass.getModifiers())));

      final boolean expectedValue = !(Modifier.isAbstract(actualClass.getModifiers())
          || actualClass.isInterface()
          || actualClass.isEnum());
      final boolean actualValue = pojoClass.isConcrete();
      Affirm.affirmTrue(String.format(message, actualClass.getName() + ".isConcrete()", expectedValue, actualValue),
          actualValue == expectedValue);
    }
  }

  @Test
  public void testIsFinalOnFinalClass() {
    final Class<?> aFinalClass = AFinalClass.class;
    final PojoClass pojoClass = getPojoClassImplForClass(aFinalClass);

    Affirm.affirmTrue(String.format("IsFinal on final=[%s] returned false for PojoClass implementation=[%s]!!", aFinalClass,
        pojoClass), pojoClass.isFinal());
  }

  @Test
  public void testIsFinalOnNonFinalClass() {
    final Class<?> aNonFinalClass = ANonFinalClass.class;
    final PojoClass pojoClass = getPojoClassImplForClass(aNonFinalClass);
    Affirm.affirmFalse(String.format("IsFinal on non-final=[%s] returned true for PojoClass implementation=[%s]!!",
        aNonFinalClass, pojoClass), pojoClass.isFinal());
  }

  @Test
  public void testIsSyntheticOnNonSyntheticClass() {
    PojoClass aClassWithSyntheticDoublePojoClass = getPojoClassImplForClass(AClassWithSythetics.class);
    Affirm.affirmFalse("Class isn't synthetic", aClassWithSyntheticDoublePojoClass.isSynthetic());
  }

  @Test
  public void testIsSyntheticOnSyntheticClass() {
    PojoClass syntheticPojoClass = getPojoClassImplForClass(AClassWithSythetics.class);
    Affirm.affirmEquals("Expected 2 constructors", 2, syntheticPojoClass.getPojoConstructors().size());

    PojoMethod constructor = null;

    for (PojoMethod constructorEntry : syntheticPojoClass.getPojoConstructors()) {
      if (constructorEntry.getParameterTypes().length > 0)
        constructor = constructorEntry;
    }

    Assert.assertNotNull(constructor);
    Affirm.affirmTrue("Failed to find synthetic constructor", constructor.isSynthetic());
    Affirm.affirmEquals("Synthetic Constructor should have just one parameter", 1, constructor.getParameterTypes().length);

    PojoClass aSyntheticClass = getPojoClassImplForClass(constructor.getParameterTypes()[0]);
    Affirm.affirmTrue("Parameter to synthetic constructor should be synthetic class", aSyntheticClass.isSynthetic());
  }

  @Test
  public void testGetPojoFieldsAnnotatedWith() {
    PojoClass pojoClass = getPojoClassImplForClass(AClassWithAnnotatedFields.class);
    Affirm.affirmEquals("Expected 4 fields", 4, pojoClass.getPojoFields().size());

    List<PojoField> annotatedPojoFields = pojoClass.getPojoFieldsAnnotatedWith(SomeAnnotation.class);
    Affirm.affirmEquals("Expected 2 annotated fields", 2, annotatedPojoFields.size());


  }

  @Test
  @SuppressWarnings("PointlessArithmeticExpression")
  public void testGetPojoMethods() {
    PojoClass pojoClass = getPojoClassImplForClass(AClassWithSixMethods.class);
    Affirm.affirmEquals(String.format("Methods added/removed from class=[%s] found methods=[%s]", pojoClass.getName(),
        pojoClass.getPojoMethods()), 6 + 1 /* constructor */, pojoClass.getPojoMethods().size());

    pojoClass = getPojoClassImplForClass(AClassWithoutMethods.class);
    Affirm.affirmEquals(String.format("Methods added/removed from class=[%s]", pojoClass.getName()), 0 + 1 /* constructor
    */, pojoClass.getPojoMethods().size());
  }

  @Test
  public void testGetPojoMethodsAnnotatedWith() {
    PojoClass pojoClass = getPojoClassImplForClass(AClassWithAnnotatedMethods.class);
    Affirm.affirmEquals("Expected 5 methods", 4 + 1 /* constructor */, pojoClass.getPojoMethods().size());

    List<PojoMethod> annotatedPojoFields = pojoClass.getPojoMethodsAnnotatedWith(SomeAnnotation.class);
    Affirm.affirmEquals("Expected 2 annotated methods", 2, annotatedPojoFields.size());

  }

  @Test
  public void testExtendz() {
    final Class<?> aClassExtendingAnInterfaceAndAbstract = AClassExtendingAnInterface.class;
    final Class<?> anInterface = AnInterfaceClass.class;

    final PojoClass pojoClass = getPojoClassImplForClass(aClassExtendingAnInterfaceAndAbstract);
    Affirm.affirmTrue(String.format("Failed to validate Class=[%s] extending an interface=[%s]" + " for PojoClass " +
        "Implementation=[%s]", aClassExtendingAnInterfaceAndAbstract, anInterface, pojoClass), pojoClass.extendz(anInterface));
  }

  @Test
  public void testGetAnnotations() {
    final Class<?> aClassWithAnnotations = AClassWithAnnotations.class;

    final PojoClass pojoClass = getPojoClassImplForClass(aClassWithAnnotations);
    Affirm.affirmEquals(String.format("Annotations added/removed from Class=[%s]", aClassWithAnnotations), 2, pojoClass
        .getAnnotations().size());
  }

  @Test
  public void multipleAnnotationsShouldBeReturned() {
    final Class<?> aClassWithAnnotations = AClassWithAnnotations.class;

    final PojoClass pojoClass = getPojoClassImplForClass(aClassWithAnnotations);
    Affirm.affirmEquals(String.format("Annotations added/removed from Class=[%s]", aClassWithAnnotations), 2, pojoClass
        .getAnnotations().size());

    final List<Class<?>> expectedAnnotations = new LinkedList<Class<?>>();
    expectedAnnotations.add(SomeAnnotation.class);
    expectedAnnotations.add(AnotherAnnotation.class);
    for (final Annotation annotation : pojoClass.getAnnotations()) {
      Affirm.affirmTrue(String.format("Expected annotations [%s] not found, instead found [%s]", expectedAnnotations,
          annotation.annotationType()), expectedAnnotations.contains(annotation.annotationType()));
    }
  }

  @Test(expected = ReflectionException.class)
  public void shouldFailToCreateInstanceOnInterface() {
    final PojoClass pojoClass = getPojoClassImplForClass(AnInterfaceClass.class);
    InstanceFactory.getInstance(pojoClass);
  }

  @Test
  public void shouldCreateInstanceOnAbstract() {
    final PojoClass pojoClass = getPojoClassImplForClass(AnAbstractClass.class);
    Affirm.affirmNotNull("Should have created instance", InstanceFactory.getInstance(pojoClass));
  }

  @Test(expected = ReflectionException.class)
  public void shouldFailToFindAppropriateConstructor() {
    final PojoClass pojoClass = getPojoClassImplForClass(MultiplePublicAndPrivateWithManyParamsConstructor.class);
    InstanceFactory.getInstance(pojoClass, (new Object[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }));
  }

  @Test(expected = ReflectionException.class)
  public void shouldFailToConstructBasedOnExcpetionalConstructorWithNoParam() {
    final PojoClass pojoClass = getPojoClassImplForClass(AClassWithExceptionalConstructors.class);
    InstanceFactory.getInstance(pojoClass);
  }

  @Test(expected = ReflectionException.class)
  public void shouldFailToConstructBasedOnExcpetionalConstructorWithParam() {
    final PojoClass pojoClass = getPojoClassImplForClass(AClassWithExceptionalConstructors.class);
    InstanceFactory.getInstance(pojoClass, ("OneStringParam"));
  }

  @Test
  public void shouldCreateInstanceUsingDeclaredPublicConstructor() {
    final PojoClass pojoClass = getPojoClassImplForClass(OnePublicNoParamConstructor.class);
    final Object instance = InstanceFactory.getInstance(pojoClass);
    Affirm.affirmNotNull(String.format("Failed to create a new instance using publicly declared constructor for " +
        "class=[%s]", pojoClass), instance);
  }

  @Test
  public void shouldCreateInstanceUsingDeclaredPrivateConstructor() {
    final PojoClass pojoClass = getPojoClassImplForClass(OnePrivateNoParamsConstructor.class);
    final Object instance = InstanceFactory.getInstance(pojoClass);
    Affirm.affirmNotNull(String.format("Failed to create a new instance using privately declared constructor for " +
        "class=[%s]", pojoClass), instance);
  }

  @Test
  public void shouldCreateInstanceUsingImplicitConstructor() {
    final PojoClass pojoClass = getPojoClassImplForClass(NoDeclaredConstructor.class);
    final Object instance = InstanceFactory.getInstance(pojoClass);

    Affirm.affirmNotNull(String.format("Failed to create a new instance using compiler auto-generated constructor for " +
        "class=[%s]", pojoClass), instance);
  }

  @Test
  public void shouldCreateInstanceOneParameterConstructor() {
    final PojoClass pojoClass = getPojoClassImplForClass(MultiplePublicAndPrivateWithManyParamsConstructor.class);
    final Object instance = InstanceFactory.getInstance(pojoClass, RandomFactory.getRandomValue(String.class));
    Affirm.affirmNotNull(String.format("Failed to create a new instance using single parameter constructor for " +
        "class=[%s]", pojoClass), instance);
  }

  @Test
  public void shouldCreateInstanceOneNullParameterConstructor() {
    final PojoClass pojoClass = getPojoClassImplForClass(MultiplePublicAndPrivateWithManyParamsConstructor.class);
    final Object instance = InstanceFactory.getInstance(pojoClass, (new Object[] { null }));
    Affirm.affirmNotNull(String.format("Failed to create a new instance using single parameter constructor for " +
        "class=[%s]", pojoClass), instance);
  }

  @Test
  public void shouldCreateInstanceMultipleParameterConstructor() {
    final PojoClass pojoClass = getPojoClassImplForClass(MultiplePublicAndPrivateWithManyParamsConstructor.class);
    final Object instance = InstanceFactory.getInstance(pojoClass, RandomFactory.getRandomValue(String.class), RandomFactory
        .getRandomValue(Integer.class));
    Affirm.affirmNotNull(String.format("Failed to create a new instance using multiple parameter constructor for " +
        "class=[%s]", pojoClass), instance);
  }

  @Test
  public void shouldCreateInstanceMultipleParameterPrivateConstructor() {
    final PojoClass pojoClass = getPojoClassImplForClass(MultiplePublicAndPrivateWithManyParamsConstructor.class);
    final Object instance = InstanceFactory.getInstance(pojoClass, RandomFactory.getRandomValue(String.class), RandomFactory
        .getRandomValue(Integer.class), RandomFactory.getRandomValue(Character.class));
    Affirm.affirmNotNull(String.format("Failed to create a new instance using multiple parameter private constructor for " +
        "class=[%s]", pojoClass), instance);
  }

  @Test
  public void testIsNestedClass() {
    final Class<?> nonNestedClass = AClassWithNestedClass.class;
    PojoClass pojoClass = getPojoClassImplForClass(nonNestedClass);
    Affirm.affirmFalse(String.format("Non-nested class=[%s] returned true for isNestedClass on PojoClass " +
        "implementation=[%s]", nonNestedClass, pojoClass), pojoClass.isNestedClass());

    final Class<?> nestedClass = NestedClass.class;
    pojoClass = getPojoClassImplForClass(nestedClass);
    Affirm.affirmTrue(String.format("Nested class=[%s] returned false for isNestedClass on PojoClass implementation=[%s]",
        nestedClass, pojoClass), pojoClass.isNestedClass());

    final Class<?> nestedStaticClass = AClassWithNestedClass.NestedStaticClass.class;
    pojoClass = getPojoClassImplForClass(nestedStaticClass);
    Affirm.affirmTrue(String.format("Nested class=[%s] returned false for isNestedClass on PojoClass implementation=[%s]",
        nestedClass, pojoClass), pojoClass.isNestedClass());
  }

  @Test
  public void testCopy() {
    final AClassWithEquality first = new AClassWithEquality(RandomFactory.getRandomValue(String.class), RandomFactory
        .getRandomValue(Integer.class));

    final AClassWithEquality second = new AClassWithEquality();

    Affirm.affirmFalse(String.format("Class with data=[%s], evaluated equals to one without=[%s]!!", BusinessIdentity.toString
        (first), BusinessIdentity.toString(second)), first.equals(second));

    final PojoClass pojoClass = getPojoClassImplForClass(first.getClass());
    pojoClass.copy(first, second);
    Affirm.affirmTrue(String.format("Class=[%s] copied to=[%s] and still equals returned false using PojoClass" + " " +
        "implementation=[%s]!!", BusinessIdentity.toString(first), BusinessIdentity.toString(second), pojoClass), first.equals
        (second));
  }

  @Test
  public void shouldGetEmptyListForGetInterfaces() {
    final PojoClass pojoClass = getPojoClassImplForClass(AClassWithoutInterfaces.class);
    Affirm.affirmNotNull(String.format("Expected empty list no null for getInterfaces() on [%s]?", pojoClass), pojoClass
        .getInterfaces());
    Affirm.affirmEquals(String.format("Interfaces added to [%s]?", pojoClass), 0, pojoClass.getInterfaces().size());
  }

  @Test
  public void shouldGetInterfaces() {
    final PojoClass pojoClass = getPojoClassImplForClass(AClassWithInterfaces.class);
    Affirm.affirmEquals(String.format("Interfaces added/removed from [%s]?", pojoClass), 2, pojoClass.getInterfaces().size());

    final List<Class<?>> expectedInterfaces = new LinkedList<Class<?>>();
    expectedInterfaces.add(FirstInterfaceForAClassWithInterfaces.class);
    expectedInterfaces.add(SecondInterfaceForAClassWithInterfaces.class);
    for (final PojoClass pojoInterface : pojoClass.getInterfaces()) {
      Affirm.affirmTrue(String.format("Expected interfaces [%s] not found, instead found [%s]", expectedInterfaces,
          pojoInterface.getClazz()), expectedInterfaces.contains(pojoInterface.getClazz()));
    }
  }

  @Test
  public void testGetClazz() {
    final Class<?> clazz = this.getClass();
    final PojoClass pojoClass = getPojoClassImplForClass(clazz);
    Affirm.affirmTrue(String.format("PojoClass parsing for [%s] returned different class=[%s] in getClazz() call" + " for " +
        "PojoClass implementation=[%s]", clazz, pojoClass.getClazz(), pojoClass), clazz.equals(pojoClass.getClazz()));
  }

  @Test
  public void testEqualityAndHashCodeBasedOnIdentityNotInstance() {
    final PojoClass first = getPojoClassImplForClass(this.getClass());
    final PojoClass second = getPojoClassImplForClass(this.getClass());
    Affirm.affirmEquals("PojoClassImpl equals is instance based!! Should be business equality based.", first, second);
    Affirm.affirmEquals("PojoClassImpl hashCode is instance based!! Should be business equality based.", first.hashCode(),
        second.hashCode());
  }

  @Test
  @SuppressWarnings("ObjectEqualsNull")
  public void testEqualsReturnsFalseWhenOtherIsNull() {
    final PojoClass pojoClass = getPojoClassImplForClass(this.getClass());
    Affirm.affirmFalse("equals(null) should return false", pojoClass.equals(null));
  }

  @Test
  public void testEqualsReturnsFalseWhenOtherIsDifferentClass() {
    final PojoClass pojoClass = getPojoClassImplForClass(this.getClass());
    Affirm.affirmFalse("equals(differentClass) should return false", pojoClass.equals(new Object()));
  }

  @Test
  public void testIsArray() {
    final Object[] objectArray = new Object[] { new Object() };
    final PojoClass objectArrayPojoClass = getPojoClassImplForClass(objectArray.getClass());
    Affirm.affirmTrue(String.format("PojoClassImpl isArray() failed on array[%s]", objectArray), objectArrayPojoClass.isArray());
    Affirm.affirmTrue(String.format("Array should return true on isAbstract for array [%s]! Did Java underlying " +
        "implementation change?", objectArray), objectArrayPojoClass.isAbstract());
  }

  @Test
  public void testIsStatic() {
    PojoClass pojoClass = getPojoClassImplForClass(AClassWithTwoChildClassesOneStaticAndOneNot.APublicNonStaticClass.class);
    Affirm.affirmTrue("Nested class not detected nested", pojoClass.isNestedClass());
    Affirm.affirmFalse("Nested non-static nested class detected as static", pojoClass.isStatic());

    pojoClass = getPojoClassImplForClass(AClassWithTwoChildClassesOneStaticAndOneNot.AStaticClass.class);
    Affirm.affirmTrue("Nested class not detected nested", pojoClass.isNestedClass());
    Affirm.affirmTrue("Nested static class not seen as static", pojoClass.isStatic());
  }

  @Test
  public void testGetPath() {
    PojoClass pojoClass = getPojoClassImplForClass(this.getClass());

    String sourcePath = pojoClass.getSourcePath();
    Affirm.affirmTrue("Should start with file:// [" + sourcePath + "]", sourcePath.startsWith("file://"));

    String thisClassEndingPath = this.getClass().getName().replace(Java.PACKAGE_DELIMITER, Java.PATH_DELIMITER) + Java
        .CLASS_EXTENSION;
    Affirm.affirmTrue("Should end with this class's package path [" + sourcePath + "]", sourcePath.endsWith(thisClassEndingPath));
  }

  @Test
  public void testGetPackage() {
    PojoClass pojoClass = getPojoClassImplForClass(this.getClass());

    PojoPackage pojoPackage = pojoClass.getPackage();
    Affirm.affirmNotNull("Null package received", pojoPackage);
    Affirm.affirmEquals("Invalid package retrieved", this.getClass().getPackage().getName(), pojoPackage.getName());
  }

  @Test
  public void isPublicClass() {
    PojoClass pojoclass = getClass(SAMPLE_CLASSES_PKG + ".AccessibilityClass$PublicClass");
    Affirm.affirmNotNull("class not found", pojoclass);

    Affirm.affirmTrue("isPublic() check on class=[" + pojoclass + "] returned false!!", pojoclass.isPublic());
    Affirm.affirmFalse("isProtected() check on class=[" + pojoclass + "] returned true!!", pojoclass.isProtected());
    Affirm.affirmFalse("isPrivate() check on class=[" + pojoclass + "] returned true!!", pojoclass.isPrivate());
    Affirm.affirmFalse("isPackagePrivate() check on class=[" + pojoclass + "] returned true!!", pojoclass.isPackagePrivate());
  }

  @Test
  public void isProtectedClass() {
    PojoClass pojoclass = getClass(SAMPLE_CLASSES_PKG + ".AccessibilityClass$ProtectedClass");
    Affirm.affirmNotNull("class not found", pojoclass);

    Affirm.affirmTrue("isProtected() check on class=[" + pojoclass + "] returned false!!", pojoclass.isProtected());
    Affirm.affirmFalse("isPrivate() check on class=[" + pojoclass + "] returned true!!", pojoclass.isPrivate());
    Affirm.affirmFalse("isPackagePrivate() check on class=[" + pojoclass + "] returned true!!", pojoclass.isPackagePrivate());
    Affirm.affirmFalse("isPublic() check on class=[" + pojoclass + "] returned true!!", pojoclass.isPublic());
  }

  @Test
  public void isPrivateClass() {
    PojoClass pojoclass = getClass(SAMPLE_CLASSES_PKG + ".AccessibilityClass$PrivateClass");
    Affirm.affirmNotNull("class not found", pojoclass);

    Affirm.affirmTrue("isPrivate() check on class=[" + pojoclass + "] returned true!!", pojoclass.isPrivate());
    Affirm.affirmFalse("isPackagePrivate() check on class=[" + pojoclass + "] returned true!!", pojoclass.isPackagePrivate());
    Affirm.affirmFalse("isProtected() check on class=[" + pojoclass + "] returned true!!", pojoclass.isProtected());
    Affirm.affirmFalse("isPublic() check on class=[" + pojoclass + "] returned true!!", pojoclass.isPublic());
  }

  @Test
  public void isPackagePrivateClass() {
    PojoClass pojoclass = getClass(SAMPLE_CLASSES_PKG + ".AccessibilityClass$PackagePrivateClass");
    Affirm.affirmNotNull("class not found", pojoclass);

    Affirm.affirmTrue("isPackagePrivate() check on class=[" + pojoclass + "] returned false!!", pojoclass.isPackagePrivate());
    Affirm.affirmFalse("isPrivate() check on class=[" + pojoclass + "] returned true!!", pojoclass.isPrivate());
    Affirm.affirmFalse("isProtected() check on class=[" + pojoclass + "] returned true!!", pojoclass.isProtected());
    Affirm.affirmFalse("isPublic() check on class=[" + pojoclass + "] returned true!!", pojoclass.isPublic());  }

  private PojoClass getClass(String name) {
    List<PojoClass> pojoClasses = PojoClassFactory.getPojoClasses(SAMPLE_CLASSES_PKG);
    for (PojoClass pojoClass : pojoClasses) {
      if (pojoClass.getName().equals(name))
        return pojoClass;
    }
    throw ReflectionException.getInstance("Request class not found! [" + name + "]");
  }


  private static PojoClass getPojoClassImplForClass(final Class<?> clazz) {
    return PojoClassFactory.getPojoClass(clazz);
  }
}
