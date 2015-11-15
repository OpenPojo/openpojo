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

package com.openpojo.reflection.construct;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.List;

import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.construct.sampleclasses.*;
import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.affirm.Affirm;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.Matchers.greaterThan;

/**
 * @author oshoukry
 */
public class InstanceFactoryTest {

  @Test
  public void shouldNotConstruct() {
    Constructor[] constructors = InstanceFactory.class.getDeclaredConstructors();
    Assert.assertEquals(1, constructors.length);
    Constructor constructor = constructors[0];
    Assert.assertEquals(0, constructor.getParameterTypes().length);
    Assert.assertTrue("Constructor must be private", Modifier.isPrivate(constructor.getModifiers()));

    constructor.setAccessible(true);
    try {
      constructor.newInstance();
      Assert.fail("Should not be created");
    } catch (Throwable re) {
      Assert.assertEquals("Should not be constructed", re.getCause().getMessage());
    }
  }

  @Test
  @SuppressWarnings("RedundantArrayCreation")
  public void shouldCreateUsingDefaultConstructor() {
    final Class<?> clazz = ClassWithNoDeclaredConstructor.class;
    final Object obj1 = getInstance(clazz, (Object[]) null);
    Affirm.affirmNotNull("Should have created an object", obj1);

    final Object obj2 = getInstance(clazz, new Object[] {});
    Affirm.affirmTrue("Should have created a different object", obj1 != obj2);
  }

  @Test
  public void shouldCreateUsingSingleParameterConstructor() {
    final Class<?> clazz = ClassWithVariousDeclaredContructorsAndMethods.class;
    final String stringParam = RandomFactory.getRandomValue(String.class);
    final ClassWithVariousDeclaredContructorsAndMethods obj1 =
        (ClassWithVariousDeclaredContructorsAndMethods) getInstance(clazz, stringParam);
    Affirm.affirmNotNull("Should have created using String constructor", obj1);
    Affirm.affirmEquals("Incorrect constructor used", stringParam, obj1.singleStringConstructor);
  }

  @Test
  public void shouldCreateUsingNullParameterDoubleConstructor() {
    final Class<?> clazz = ClassWithVariousDeclaredContructorsAndMethods.class;
    final String stringParam = RandomFactory.getRandomValue(String.class);
    final ClassWithVariousDeclaredContructorsAndMethods obj =
        (ClassWithVariousDeclaredContructorsAndMethods) getInstance(clazz, stringParam, null);

    Affirm.affirmNotNull("Should have created using two parameter constructor", obj);
    Affirm.affirmNull("Should have called using two parameter constructor", obj.doubleIntegerConstructor);
    Affirm.affirmEquals("Should have called using two parameter constructor", stringParam, obj.doubleStringConstructor);
  }

  @Test(expected = ReflectionException.class)
  public void shouldFailtoCreateParametersMissmatch() {
    final Class<?> clazz = ClassWithVariousDeclaredContructorsAndMethods.class;
    final String stringParam = RandomFactory.getRandomValue(String.class);
    getInstance(clazz, stringParam, stringParam);
    Affirm.fail("Should've failed to create");
  }

  @Test(expected = ReflectionException.class)
  public void shouldFailtoCreateUsingDefault() {
    final Class<?> clazz = ClassWithNoDeclaredConstructor.class;
    final String stringParam = RandomFactory.getRandomValue(String.class);
    getInstance(clazz, stringParam);
    Affirm.fail("Should've failed to create");
  }

  private Object getInstance(final Class<?> clazz, final Object... parameters) {
    final PojoClass pojoClass = PojoClassFactory.getPojoClass(clazz);
    return InstanceFactory.getInstance(pojoClass, parameters);
  }

  @Test
  public void shouldConstructUsingMinimalParameterCount() {
    final PojoClass pojoClass = PojoClassFactory.getPojoClass(ClassWithLessThanGreaterThanConstructors.class);
    final ClassWithLessThanGreaterThanConstructors instance =
        (ClassWithLessThanGreaterThanConstructors) InstanceFactory.getLeastCompleteInstance(pojoClass);
    Affirm.affirmEquals("Should've used constructor with single Parameter", 1, instance.getParameterCountUsedForConstruction());
  }

  @Test
  public void shouldConstructUsingMaximumParameterCount() {
    final PojoClass pojoClass = PojoClassFactory.getPojoClass(ClassWithLessThanGreaterThanConstructors.class);
    final ClassWithLessThanGreaterThanConstructors instance =
        (ClassWithLessThanGreaterThanConstructors) InstanceFactory.getMostCompleteInstance(pojoClass);
    Affirm.affirmEquals("Should've used constructor with single Parameter", 3, instance.getParameterCountUsedForConstruction());
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

  @Test
  public void shouldConstructBasedOnDerivedClass() {
    final PojoClass aClassWithInterfaceBasedConstructor = PojoClassFactory.getPojoClass(ClassWithInterfaceBasedConstructor.class);
    Assert.assertNotNull(InstanceFactory.getInstance(aClassWithInterfaceBasedConstructor, "SomeString"));
  }

  @Test
  public void shouldSkipSyntheticConstructor() {
    final PojoClass classWithStaticConstructorPojo = PojoClassFactory.getPojoClass(ClassWithSyntheticConstructor.class);
    Assert.assertNotNull(InstanceFactory.getMostCompleteInstance(classWithStaticConstructorPojo));
  }

  @Test
  public void shouldConstructAClassWithGenericConstructor() {
    final PojoClass pojoClass = PojoClassFactory.getPojoClass(AClassWithGenericConstructor.class);
    AClassWithGenericConstructor aClassWithGenericConstructor = (AClassWithGenericConstructor) InstanceFactory
        .getLeastCompleteInstance(pojoClass);
    Assert.assertNotNull(aClassWithGenericConstructor);

    List<AClassWithGenericConstructor.Child> children = aClassWithGenericConstructor.getMyChildren();

    Assert.assertThat(children.size(), greaterThan(0));

    for (AClassWithGenericConstructor.Child child : children) {
      Assert.assertNotNull(child);
      Assert.assertNotNull(child.getName());
    }
  }

  @Test
  public void shouldCreateObjectWithMultipleTypeVariableTypes() {
    final PojoClass pojoClass = PojoClassFactory.getPojoClass(AClassWithMultipleTypeVariablesGenericConstructor.class);
    AClassWithMultipleTypeVariablesGenericConstructor instance =
        (AClassWithMultipleTypeVariablesGenericConstructor) InstanceFactory.getMostCompleteInstance(pojoClass);

    Assert.assertNotNull(instance);

    Assert.assertNotNull(instance.getMyV());
    Assert.assertEquals(instance.getMyV().getClass(), Object.class);
    Assert.assertNotNull(instance.getMyK());
    Assert.assertEquals(instance.getMyK().getClass(), Object.class);

    Assert.assertNotNull(instance.getMyT());
    Assert.assertTrue(CharSequence.class.isAssignableFrom(instance.getMyT().getClass()));

  }
}
