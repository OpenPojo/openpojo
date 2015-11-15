/*
 * Copyright (c) 2010-2015 Osman Shoukry
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License or any
 *    later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.openpojo.reflection.impl;

import java.lang.annotation.Annotation;
import java.util.LinkedList;
import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.impl.sampleannotation.AnotherAnnotation;
import com.openpojo.reflection.impl.sampleannotation.SomeAnnotation;
import com.openpojo.reflection.impl.sampleclasses.AClassWithAbstractGetter;
import com.openpojo.reflection.impl.sampleclasses.AClassWithAbstractSetter;
import com.openpojo.reflection.impl.sampleclasses.AClassWithSyntheticMethod;
import com.openpojo.reflection.impl.sampleclasses.ClassWithSyntheticConstructor;
import com.openpojo.reflection.impl.sampleclasses.PojoMethodClass;
import com.openpojo.validation.affirm.Affirm;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class PojoMethodImplTest {
  PojoClass pojoClass;
  List<PojoMethod> pojoMethods;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    pojoClass = PojoClassFactory.getPojoClass(PojoMethodClass.class);
    pojoMethods = pojoClass.getPojoMethods();
  }

  /**
   * Test method for {@link com.openpojo.reflection.impl.PojoMethodImpl#getAnnotation(java.lang.Class)}.
   */
  @Test
  public void testGetAnnotation() {
    for (PojoMethod pojoMethod : pojoMethods) {
      if (pojoMethod.getName().equals("methodWithAnnotation")) {
        Affirm.affirmNotNull("removed SomeAnnotation annotation from methodWithAnnotation?",
            pojoMethod.getAnnotation(SomeAnnotation.class));
      }
      if (pojoMethod.getName().equals("methodWithoutAnnotation")) {
        Affirm.affirmNull("SomeAnnotation annotation added to methodWithoutAnnotation?",
            pojoMethod.getAnnotation(SomeAnnotation.class));
      }
    }
  }

  @Test
  public void multipleAnnotationsShouldBeReturned() {
    for (PojoMethod pojoMethod : pojoMethods) {
      if (pojoMethod.getName().equals("methodWithMultipleAnnotations")) {
        Affirm.affirmEquals(String.format("Annotations added/removed from method=[%s]", pojoMethod),
            2,
            pojoMethod.getAnnotations().size());
        List<Class<?>> expectedAnnotations = new LinkedList<Class<?>>();
        expectedAnnotations.add(SomeAnnotation.class);
        expectedAnnotations.add(AnotherAnnotation.class);
        for (Annotation annotation : pojoMethod.getAnnotations()) {
          Affirm.affirmTrue(String.format("Expected annotations [%s] not found, instead found [%s]",
              expectedAnnotations, annotation.annotationType()), expectedAnnotations.contains(annotation.annotationType()));
        }
        return;
      }
    }
    Affirm.fail(String.format("methodWithMultipleAnnotations renamed? expected in [%s]", pojoClass));
  }

  /**
   * Test method for {@link com.openpojo.reflection.impl.PojoMethodImpl#isFinal()}.
   */
  @Test
  public void testIsFinal() {
    for (PojoMethod pojoMethod : pojoMethods) {
      if (pojoMethod.getName().equals("finalMethod")) {
        Affirm.affirmTrue("Failed to check final", pojoMethod.isFinal());
        return;
      }
    }
    Affirm.fail("finalMethod missing!!");
  }

  @Test
  public void testIsNonFinal() {
    for (PojoMethod pojoMethod : pojoMethods) {
      if (pojoMethod.getName().equals("nonFinalMethod")) {
        Affirm.affirmTrue("Failed to check non final", !pojoMethod.isFinal());
        return;
      }
    }
    Affirm.fail("nonFinalMethod missing!!");
  }

  @Test
  public void testIsPrivate() {
    for (PojoMethod pojoMethod : pojoMethods) {
      if (pojoMethod.getName().equals("privateMethod")) {
        Affirm.affirmTrue("Failed to check private method", pojoMethod.isPrivate());
        return;
      }
    }
    Affirm.fail("privateMethod missing!!");
  }

  @Test
  public void testIsNotPrivate() {
    for (PojoMethod pojoMethod : pojoMethods) {
      if (pojoMethod.getName().equals("nonPrivateMethod")) {
        Affirm.affirmTrue("Failed to check non private method", !pojoMethod.isPrivate());
        return;
      }
    }
    Affirm.fail("nonPrivateMethod missing!!");
  }

  @Test
  public void testIsProtected() {
    for (PojoMethod pojoMethod : pojoMethods) {
      if (pojoMethod.getName().equals("protectedMethod")) {
        Affirm.affirmTrue("Failed to check protected method", pojoMethod.isProtected());
        return;
      }
    }
    Affirm.fail("protectedMethod missing!!");
  }

  @Test
  public void testIsNotProtected() {
    for (PojoMethod pojoMethod : pojoMethods) {
      if (pojoMethod.getName().equals("nonProtectedMethod")) {
        Affirm.affirmTrue("Failed to check non protected method", !pojoMethod.isProtected());
        return;
      }
    }
    Affirm.fail("nonProtectedMethod missing!!");
  }

  @Test
  public void testIsPublic() {
    for (PojoMethod pojoMethod : pojoMethods) {
      if (pojoMethod.getName().equals("publicMethod")) {
        Affirm.affirmTrue("Failed to check public method", pojoMethod.isPublic());
        return;
      }
    }
    Affirm.fail("publicMethod missing!!");
  }

  @Test
  public void testIsNotPublic() {
    for (PojoMethod pojoMethod : pojoMethods) {
      if (pojoMethod.getName().equals("nonPublicMethod")) {
        Affirm.affirmTrue("Failed to check non public method", !pojoMethod.isPublic());
        return;
      }
    }
    Affirm.fail("nonPublicMethod missing!!");
  }

  @Test
  public void testIsStatic() {
    for (PojoMethod pojoMethod : pojoMethods) {
      if (pojoMethod.getName().equals("staticMethod")) {
        Affirm.affirmTrue("Failed to check static method", pojoMethod.isStatic());
        return;
      }
    }
    Affirm.fail("staticMethod missing!!");
  }

  @Test
  public void testIsNotStatic() {
    for (PojoMethod pojoMethod : pojoMethods) {
      if (pojoMethod.getName().equals("nonStaticMethod")) {
        Affirm.affirmTrue("Failed to check non static method", !pojoMethod.isStatic());
        return;
      }
    }
    Affirm.fail("nonStaticMethod missing!!");
  }

  @Test
  public void testIsNotSynthetic() {
    for (PojoMethod pojoMethod : pojoMethods) {
      if (pojoMethod.getName().equals("isNotSyntheticMethod")) {
        Affirm.affirmTrue("Failed to check isNotSynthetic method", !pojoMethod.isSynthetic());
        return;
      }
    }
    Affirm.fail("isNotSyntheticMethod missing!!");
  }

  @Test
  public void testIsSynthetic() {
    PojoClass syntheticPojoClass = PojoClassFactory.getPojoClass(AClassWithSyntheticMethod.class);
    for (PojoMethod pojoMethod : syntheticPojoClass.getPojoMethods()) {
      if (!pojoMethod.getName().equals("doSomethingSneaky") && !pojoMethod.isConstructor()) {
        Affirm.affirmFalse("Failed to check synthetic method [" + pojoMethod + "]", !pojoMethod.isSynthetic());
        return;
      }
    }
    Affirm.fail("failed to find a synthetic method in class");
  }

  @Test
  public void classWithPrivateConstructorAndBuilder_hasSyntheticConstrutor() {
    Assert.assertNotNull(ClassWithSyntheticConstructor.Builder.getInstance());
    PojoClass pojoClass = PojoClassFactory.getPojoClass(ClassWithSyntheticConstructor.class);
    Assert.assertEquals(2, pojoClass.getPojoConstructors().size());
    for (PojoMethod constructor : pojoClass.getPojoMethods()) {
      if (constructor.getParameterTypes().length == 0)
        Assert.assertFalse("Synthatic constructor found!! [" + constructor + "]", constructor.isSynthetic());
      else
        Assert.assertTrue("None synthatic constructor found!! [" + constructor + "]", constructor.isSynthetic());
    }
  }

  @Test
  public void shouldNotIncludeAbstractGetterMethod() {
    PojoClass pojoClass = PojoClassFactory.getPojoClass(AClassWithAbstractGetter.class);
    boolean hasAbstractGetterMethod = false;
    PojoField pojoField = pojoClass.getPojoFields().get(0);

    String expectedGetterName = "get" + pojoField.getName().substring(0, 1).toUpperCase() + pojoField.getName().substring(1,
        pojoField.getName().length());
    for (PojoMethod pojoMethod : pojoClass.getPojoMethods()) {

      if (pojoMethod.getName().equals(expectedGetterName))
        hasAbstractGetterMethod = true;
    }
    Assert.assertTrue(hasAbstractGetterMethod);
    Assert.assertEquals(1, pojoClass.getPojoFields().size());
    Assert.assertFalse(pojoField.hasGetter());
  }

  @Test
  public void shouldNotIncludeAbstractSetterMethod() {
    PojoClass pojoClass = PojoClassFactory.getPojoClass(AClassWithAbstractSetter.class);
    boolean hasAbstractSetterMethod = false;
    PojoField pojoField = pojoClass.getPojoFields().get(0);

    String expectedSetterName = "set" + pojoField.getName().substring(0, 1).toUpperCase() + pojoField.getName().substring(1,
        pojoField.getName().length());
    for (PojoMethod pojoMethod : pojoClass.getPojoMethods()) {

      if (pojoMethod.getName().equals(expectedSetterName))
        hasAbstractSetterMethod = true;
    }
    Assert.assertTrue(hasAbstractSetterMethod);
    Assert.assertEquals(1, pojoClass.getPojoFields().size());
    Assert.assertFalse(pojoField.hasGetter());
  }
}
