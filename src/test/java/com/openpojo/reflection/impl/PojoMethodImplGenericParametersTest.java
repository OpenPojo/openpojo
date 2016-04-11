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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.impl.sample.classes.AClassWithGenericParameterConstructor;
import com.openpojo.reflection.impl.sample.classes.AClassWithGenericParameterMethod;
import com.openpojo.validation.affirm.Affirm;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class PojoMethodImplGenericParametersTest {

  @Test
  public void shouldGetConstructorWithGenericParameter() {
    PojoClass pojoClass = PojoClassFactory.getPojoClass(AClassWithGenericParameterConstructor.class);
    List<PojoMethod> constructors = pojoClass.getPojoConstructors();
    Affirm.affirmEquals(pojoClass.getName() + " should have only one generic parameterized constructor", 1, constructors.size());

    shouldHaveOneParameterizedParameter(constructors, String.class);
  }

  private void shouldHaveOneParameterizedParameter(List<PojoMethod> methods, Class expectedParameter) {
    PojoMethod method = methods.get(0);
    Type types[] = method.getGenericParameterTypes();

    Affirm.affirmEquals(method.getName() + " should have only one parameter", 1, types.length);

    Type type = types[0];

    Affirm.affirmTrue(method.getName() + " parameter must be of type ParamaterizedType", type instanceof ParameterizedType);

    ParameterizedType parameterizedType = (ParameterizedType) type;

    Type actualParameterTypes[] = parameterizedType.getActualTypeArguments();

    Affirm.affirmEquals(method.getName() + " parameterizedType should have only one type binding",
        1,
        actualParameterTypes.length);

    Affirm.affirmEquals(parameterizedType.toString() + " must be parameterized with String", expectedParameter,
        parameterizedType.getActualTypeArguments()[0]);
  }

  @Test
  public void shouldGetMethodWithGenericParameter() {
    PojoClass pojoClass = PojoClassFactory.getPojoClass(AClassWithGenericParameterMethod.class);
    List<PojoMethod> allMethodsAndConstructors = pojoClass.getPojoMethods();

    List<PojoMethod> methods = new LinkedList<PojoMethod>();
    for (PojoMethod method : allMethodsAndConstructors) {
      if (method.isConstructor())
        continue;
      methods.add(method);
    }

    Affirm.affirmEquals(pojoClass.getName() + " should have only one generic parameterized method", 1, methods.size());

    shouldHaveOneParameterizedParameter(methods, Integer.class);

  }

}
