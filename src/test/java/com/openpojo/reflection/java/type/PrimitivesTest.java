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

package com.openpojo.reflection.java.type;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.affirm.Affirm;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class PrimitivesTest {

  @Test
  public void shouldHavePrivateConstructor() {
    PojoClass pojoClass = PojoClassFactory.getPojoClass(Primitives.class);
    Affirm.affirmEquals("Should only have one constructor", 1, pojoClass.getPojoConstructors().size());
    Affirm.affirmTrue("Constructor must be private", pojoClass.getPojoConstructors().get(0).isPrivate());
  }

  @Test
  public void shouldReturnSameInstanceWhenGetInstance() {
    Primitives first = Primitives.getInstance();
    Primitives second = Primitives.getInstance();
    Affirm.affirmNotNull("Should return an instance", first);
    Affirm.affirmNotNull("Should return an instance", second);
    Affirm.affirmTrue("Should have been the exact same instance", first == second);
  }

  @Test
  public void shouldReturnNullNotPrimitive() {
    Class<?> anyClass = Object.class;
    Affirm.affirmEquals("Should have been the same class", anyClass, Primitives.getInstance().autoBox(anyClass));
  }

  @Test
  public void shouldConvertPrimitiveToWrappedClass() {
    checkPrimitiveCorrectlyWrapped(Boolean.TYPE, Boolean.class);
    checkPrimitiveCorrectlyWrapped(Byte.TYPE, Byte.class);
    checkPrimitiveCorrectlyWrapped(Character.TYPE, Character.class);
    checkPrimitiveCorrectlyWrapped(Double.TYPE, Double.class);
    checkPrimitiveCorrectlyWrapped(Float.TYPE, Float.class);
    checkPrimitiveCorrectlyWrapped(Integer.TYPE, Integer.class);
    checkPrimitiveCorrectlyWrapped(Long.TYPE, Long.class);
    checkPrimitiveCorrectlyWrapped(Short.TYPE, Short.class);
    checkPrimitiveCorrectlyWrapped(Void.TYPE, Void.class);

  }

  private void checkPrimitiveCorrectlyWrapped(Class<?> primitive, Class<?> expected) {
    Primitives instance = Primitives.getInstance();
    Affirm.affirmEquals("Should wrap primitive", expected, instance.autoBox(primitive));
  }
}
