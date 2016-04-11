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
import java.util.LinkedList;
import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.impl.sample.annotation.AnotherAnnotation;
import com.openpojo.reflection.impl.sample.annotation.SomeAnnotation;
import com.openpojo.reflection.impl.sample.classes.AClassWithAbstractGetter;
import com.openpojo.reflection.impl.sample.classes.AClassWithAbstractSetter;
import com.openpojo.reflection.impl.sample.classes.AClassWithSyntheticMethod;
import com.openpojo.reflection.impl.sample.classes.ClassWithSyntheticConstructor;
import com.openpojo.reflection.impl.sample.classes.PojoMethodClass;
import com.openpojo.validation.affirm.Affirm;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class PojoMethodImplTest {
  private PojoClass pojoClass;
  private List<PojoMethod> pojoMethods;

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
    String prefix = "privateMethod";
    PojoMethod pojoMethod = getPojoMethodStartingWith(prefix);

    Affirm.affirmNotNull("method not found [" + prefix + "]", pojoMethod);
    Affirm.affirmTrue("isPrivate() check on method=[" + pojoMethod + "] returned false!!", pojoMethod.isPrivate());
    Affirm.affirmFalse("isPackagePrivate() check on method=[" + pojoMethod + "] returned true!!", pojoMethod.isPackagePrivate());
    Affirm.affirmFalse("isProtected() check on method=[" + pojoMethod + "] returned true!!", pojoMethod.isProtected());
    Affirm.affirmFalse("isPublic() check on method=[" + pojoMethod + "] returned true!!", pojoMethod.isPublic());
  }

  @Test
  public void isPackagePrivate() {
    String prefix = "packagePrivateMethod";
    PojoMethod pojoMethod = getPojoMethodStartingWith(prefix);

    Affirm.affirmNotNull("method not found [" + prefix + "]", pojoMethod);
    Affirm.affirmTrue("isPackagePrivate() check on method=[" + pojoMethod + "] returned false!!", pojoMethod.isPackagePrivate());
    Affirm.affirmFalse("isPrivate() check on method=[" + pojoMethod + "] returned true!!", pojoMethod.isPrivate());
    Affirm.affirmFalse("isProtected() check on method=[" + pojoMethod + "] returned true!!", pojoMethod.isProtected());
    Affirm.affirmFalse("isPublic() check on method=[" + pojoMethod + "] returned true!!", pojoMethod.isPublic());
  }

  @Test
  public void testIsProtected() {
    String prefix = "protectedMethod";
    PojoMethod pojoMethod = getPojoMethodStartingWith(prefix);

    Affirm.affirmNotNull("method not found [" + prefix + "]", pojoMethod);
    Affirm.affirmTrue("isProtected() check on method=[" + pojoMethod + "] returned false!!", pojoMethod.isProtected());
    Affirm.affirmFalse("isPrivate() check on method=[" + pojoMethod + "] returned true!!", pojoMethod.isPrivate());
    Affirm.affirmFalse("isPackagePrivate() check on method=[" + pojoMethod + "] returned true!!", pojoMethod.isPackagePrivate());
    Affirm.affirmFalse("isPublic() check on method=[" + pojoMethod + "] returned true!!", pojoMethod.isPublic());
  }

  @Test
  public void testIsPublic() {
    String prefix = "publicMethod";
    PojoMethod pojoMethod = getPojoMethodStartingWith(prefix);

    Affirm.affirmNotNull("method not found [" + prefix + "]", pojoMethod);
    Affirm.affirmTrue("isPublic() check on method=[" + pojoMethod + "] returned false!!", pojoMethod.isPublic());
    Affirm.affirmFalse("isPrivate() check on method=[" + pojoMethod + "] returned true!!", pojoMethod.isPrivate());
    Affirm.affirmFalse("isPackagePrivate() check on method=[" + pojoMethod + "] returned true!!", pojoMethod.isPackagePrivate());
    Affirm.affirmFalse("isProtected() check on method=[" + pojoMethod + "] returned true!!", pojoMethod.isProtected());
  }

  private PojoMethod getPojoMethodStartingWith(String prefix) {
    for (PojoMethod pojoMethod : pojoMethods)
      if (pojoMethod.getName().startsWith(prefix))
        return pojoMethod;
    throw ReflectionException.getInstance("PojoMethod with prefix [" + prefix + "] not found");
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
