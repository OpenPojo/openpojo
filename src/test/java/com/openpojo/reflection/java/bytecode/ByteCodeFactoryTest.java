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

package com.openpojo.reflection.java.bytecode;

import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.construct.InstanceFactory;
import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.reflection.java.bytecode.sample.*;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class ByteCodeFactoryTest {

  @Test(expected = ReflectionException.class)
  public void shouldNotBeAbleToCreateInstance() {
    ByteCodeFactory byteCodeFactory = (ByteCodeFactory) InstanceFactory.getInstance(getPojoClass(ByteCodeFactory.class));
  }

  @Test
  public void givenNullShouldReturnNull() {
    Assert.assertNull(getSubClass((Class<?>) null));
  }

  @Test
  public void givenAnInterfaceShouldReturnNull() {
    Assert.assertNull(getSubClass(AnInterface.class));
  }

  @Test
  public void givenAnEnumShouldReturnNull() {
    Assert.assertNull(getSubClass(AnEnum.class));
  }

  @Test
  public void givenPrimitiveShouldReturnNull() {
    Assert.assertNull(getSubClass(int.class));
    Assert.assertNull(getSubClass(char.class));
    Assert.assertNull(getSubClass(float.class));
    Assert.assertNull(getSubClass(long.class));
    Assert.assertNull(getSubClass(short.class));
    Assert.assertNull(getSubClass(byte.class));
    Assert.assertNull(getSubClass(boolean.class));
    Assert.assertNull(getSubClass(double.class));
  }

  @Test
  public void givenAnArrayShouldReturnNull() {
    Assert.assertNull(getSubClass(int[].class));
  }

  @Test
  public void givenAnEmptyAbstractClassShouldReturnImplementationClass() {
    Class<?> clazz = AnAbstractClassWithNoMethods.class;
    Class<?> subClassPojoClass = getSubClass(clazz);
    assertNotNull(subClassPojoClass);
    assertIsSubclass(clazz, subClassPojoClass);
  }

  @Test
  public void givenAFinalClassShouldReturnNull() {
    Assert.assertNull(getSubClass(AFinalClass.class));
  }

  @Test
  public void givenAnAbstractClassWithAnAbstractMethodShouldReturnAnInstance() {
    PojoClass pojoClass = PojoClassFactory.getPojoClass(AnAbstractClassWithOneAbstraceMethod.class);
    Assert.assertEquals("Should have 1 constructor and 1 abstract method", 2, pojoClass.getPojoMethods().size());

    Class<?> subclass = getSubClass(pojoClass.getClazz());
    assertNotNull(subclass);
    assertIsSubclass(pojoClass.getClazz(), subclass);

  }

  @Test
  public void givenAnAbstractClassWithConstructorShouldConstruct() {
    PojoClass pojoClass = PojoClassFactory.getPojoClass(AnAbstractClassWithConstructor.class);
    Class<?> subClass = getSubClass(pojoClass.getClazz());

    assertIsConcreteAndConstructable(subClass);
  }

  @Test
  public void givenAnAbstractClassWithGenericConstructorShouldConstruct() {
    PojoClass pojoClass = PojoClassFactory.getPojoClass(AnAbstractClassWithGenericConstructor.class);
    Class<?> subClass = getSubClass(pojoClass.getClazz());

    assertIsConcreteAndConstructable(subClass);
  }

  @Test
  public void givenTheSameClassShouldReturnSameGeneratedClass() {
    Class<?> clazz = AnAbstractClassWithNoMethods.class;

    Class<?> subClass1 = getSubClass(clazz);
    assertIsSubclass(clazz, subClass1);

    Class<?> subClass2 = getSubClass(clazz);
    assertIsSubclass(clazz, subClass2);

    Assert.assertEquals("Should generate the same subclass", subClass1, subClass2);
  }

  @Test
  public void endToEndTest() {
    PojoClass pojoClass = PojoClassFactory.getPojoClass(ACompleteAbstractClass.class);

    Validator pojoValidator = ValidatorBuilder.create()
        .with(new GetterMustExistRule())
        .with(new SetterMustExistRule())
        .with(new GetterTester())
        .with(new SetterTester())
        .build();

    pojoValidator.validate(pojoClass);
  }

  private void assertIsConcreteAndConstructable(Class<?> subClass) {
    PojoClass pojoClass = PojoClassFactory.getPojoClass(subClass);
    Assert.assertTrue("Should be a concrete class", pojoClass.isConcrete());
    Object instance = RandomFactory.getRandomValue(subClass);
    assertNotNull(instance);
  }

  private void assertIsSubclass(Class<?> expected, Class<?> subClass) {
    Assert.assertEquals("Should have returned a sub-class", expected, subClass.getSuperclass());
  }

  private void assertNotNull(Object instance) {
    Assert.assertNotNull("Should have returned an instance", instance);
  }

  private Class<?> getSubClass(Class<?> clazz) {
    return ByteCodeFactory.getSubClass(clazz);
  }

  private PojoClass getPojoClass(Class<?> clazz) {
    return PojoClassFactory.getPojoClass(clazz);
  }
}
