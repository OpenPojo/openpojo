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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.impl.sampleclasses.AClassWithGenericParameterConstructor;
import com.openpojo.reflection.impl.sampleclasses.AClassWithGenericParameterMethod;
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
