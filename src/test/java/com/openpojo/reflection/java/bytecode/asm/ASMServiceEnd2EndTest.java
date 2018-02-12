/*
 * Copyright (c) 2010-2018 Osman Shoukry
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

package com.openpojo.reflection.java.bytecode.asm;

import java.util.List;

import com.openpojo.reflection.java.bytecode.asm.sample.AbstractClassWithAbstractNonInheritedToString;
import com.openpojo.reflection.java.bytecode.asm.sample.AbstractClassWithAbstractToString;
import com.openpojo.reflection.java.bytecode.asm.sample.AbstractClassWithConstructorArgs;
import com.openpojo.reflection.java.bytecode.asm.sample.AbstractClassWithPrivateConstructor;
import com.openpojo.reflection.java.bytecode.asm.sample.AbstractClassWithVariousAbstractMethods;
import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.PojoParameter;
import com.openpojo.reflection.impl.PojoClassFactory;
import org.junit.Test;

import static com.openpojo.random.RandomFactory.getRandomValue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author oshoukry
 */
public class ASMServiceEnd2EndTest {

  @Test
  public void implementAbstractToString() {
    final Class<AbstractClassWithAbstractToString> type = AbstractClassWithAbstractToString.class;
    PojoClass pojoClass = PojoClassFactory.getPojoClass(type);
    for (PojoMethod method : pojoClass.getPojoMethods())
      if (method.getName().equals("toString"))
        assertThat(method.isAbstract(), is(true));

    AbstractClassWithAbstractToString abstractToString = getRandomValue(type);
    assertThat(abstractToString, notNullValue());

    final String actual = abstractToString.toString();
    final String expected = type.getName() + SubClassDefinition.GENERATED_CLASS_POSTFIX + " [@";

    assertThat(actual, notNullValue());
    assertThat(actual, startsWith(expected));
  }

  @Test
  public void tryOutAbstractMethods() {
    AbstractClassWithVariousAbstractMethods instance = getRandomValue(AbstractClassWithVariousAbstractMethods.class);
    PojoClass pojoClass = PojoClassFactory.getPojoClass(AbstractClassWithVariousAbstractMethods.class);
    for (PojoMethod method : pojoClass.getPojoMethods())
      if (!method.isConstructor()) {

        assertThat("Method" + method, method.isAbstract(), is(true));

        final List<PojoParameter> pojoParameters = method.getPojoParameters();
        Object[] params = new Object[pojoParameters.size()];
        for (int idx = 0; idx < params.length; idx++) {
          params[idx] = RandomFactory.getRandomValue(pojoParameters.get(idx));
        }

        final Class<?> returnType = method.getReturnType();
        if (!returnType.equals(Void.class) && !returnType.equals(void.class))
          assertThat("Method " + method, method.invoke(instance, params), notNullValue());
        else
          assertThat("Method " + method, method.invoke(instance, params), nullValue());
      }
  }

  @Test
  public void shouldInvokeUsingConstructorArgs() {
    final Class<AbstractClassWithConstructorArgs> type = AbstractClassWithConstructorArgs.class;
    PojoClass pojoClass = PojoClassFactory.getPojoClass(type);

    assertThat(pojoClass.isAbstract(), is(true));
    assertThat(pojoClass.getPojoConstructors().size(), is(1));
    final List<PojoParameter> constructorParameters = pojoClass.getPojoConstructors().get(0).getPojoParameters();

    assertThat(constructorParameters .size(), is(12));
    int index = 0;

    assertType(constructorParameters.get(index++).getType(), String.class);
    assertType(constructorParameters.get(index++).getType(), boolean.class);
    assertType(constructorParameters.get(index++).getType(), byte.class);
    assertType(constructorParameters.get(index++).getType(), char.class);
    assertType(constructorParameters.get(index++).getType(), double.class);
    assertType(constructorParameters.get(index++).getType(), float.class);
    assertType(constructorParameters.get(index++).getType(), int.class);
    assertType(constructorParameters.get(index++).getType(), long.class);
    assertType(constructorParameters.get(index++).getType(), short.class);
    assertType(constructorParameters.get(index++).getType(), int[].class);
    assertType(constructorParameters.get(index++).getType(), String[].class);
    assertType(constructorParameters.get(index).getType(), List.class);

    AbstractClassWithConstructorArgs ap = getRandomValue(type);
    assertThat(ap, notNullValue());
  }

  private void assertType(Class actual, Class expected) {
    assertTrue("Expected [" + expected + "], but was [" + actual + "]", expected.equals(actual));
  }

  @Test
  public void shouldDefaultForToStringWithPrams() {
    final Class<AbstractClassWithAbstractNonInheritedToString> type = AbstractClassWithAbstractNonInheritedToString.class;
    PojoClass pojoClass = PojoClassFactory.getPojoClass(type);
    final AbstractClassWithAbstractNonInheritedToString randomValue = RandomFactory.getRandomValue(type);
    assertThat(randomValue, notNullValue());
    assertThat(randomValue.toString(),
        startsWith(type.getName() + DefaultSubClassDefinition.GENERATED_CLASS_POSTFIX + "@"));
  }

  @Test
  public void canGenerateSubClassForPrivateConstructor() {
    final Class<AbstractClassWithPrivateConstructor> type = AbstractClassWithPrivateConstructor.class;
    PojoClass pojoClass = PojoClassFactory.getPojoClass(type);
    AbstractClassWithPrivateConstructor randomInstance = RandomFactory.getRandomValue(type);
    assertThat(randomInstance, notNullValue());
    assertThat(randomInstance.toString(), notNullValue());
  }

}
