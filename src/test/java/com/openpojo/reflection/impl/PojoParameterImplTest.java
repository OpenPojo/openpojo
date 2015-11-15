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

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.PojoParameter;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;

/**
 * @author oshoukry
 */
public class PojoParameterImplTest {
  @Test
  public void isParameterized() {
    PojoClass pojoClass = PojoClassFactory.getPojoClass(AClassWithParameterizedConstructors.class);

    Assert.assertTrue(pojoClass.isNestedClass());

    for (PojoMethod constructor : pojoClass.getPojoConstructors()) {
      if (!constructor.isSynthetic()) {
        List<PojoParameter> pojoParameters = constructor.getPojoParameters();
        Assert.assertThat(pojoParameters.size(), is(greaterThan(1)));
        for (int i = 1; i < pojoParameters.size(); i++) {
          PojoParameter parameter = pojoParameters.get(i);
          Assert.assertThat(parameter.isParameterized(), is(Matchers.equalTo(true)));
        }
      }
    }
  }

  @SuppressWarnings("unused")
  private class AClassWithParameterizedConstructors {
    public AClassWithParameterizedConstructors(List<? extends Collection> aWildType) {
    }

    public AClassWithParameterizedConstructors(Map<? super Collection, ?> aWildType) {
    }

    public AClassWithParameterizedConstructors(ParameterizedClass<List<String>> another) {
    }

    public AClassWithParameterizedConstructors(Queue<?> aMap) {
    }

    public AClassWithParameterizedConstructors(Class<String> aClass) {
    }
  }

  @SuppressWarnings("unused")
  private static class ParameterizedClass<T> {
    public ParameterizedClass(T someT) {
    }
  }

  @Test
  public void testConstructorWithAnnotatedParameter() {
    PojoClass aClassWithAnnotatedParameters = PojoClassFactory.getPojoClass(AClassWithAnnotatedParameters.class);
    List<PojoMethod> constructors = aClassWithAnnotatedParameters.getPojoConstructors();
    Assert.assertEquals(1, constructors.size());

    shouldHaveOneAnnotatedParameter(constructors);
  }

  private void shouldHaveOneAnnotatedParameter(List<PojoMethod> constructors) {
    PojoMethod constructor = constructors.get(0);
    List<PojoParameter> parameters = constructor.getPojoParameters();
    Assert.assertEquals(1, parameters.size());

    PojoParameter parameter = parameters.get(0);

    Assert.assertEquals(1, parameter.getAnnotations().size());

    Assert.assertTrue("Should've been annotatated with Annotated.class but was [" + parameter.getAnnotations().get(0) +
        "]", parameter.getAnnotations().get(0) instanceof Annotated);

    Assert.assertNull(parameter.getAnnotation(UnusedAnnotation.class));
    Assert.assertNotNull(parameter.getAnnotation(Annotated.class));
  }

  @SuppressWarnings("unused")
  private static class AClassWithAnnotatedParameters {
    public AClassWithAnnotatedParameters(@Annotated String someParameter) {
    }
  }

  @Test
  public void testMethodWithAnnotatedParameter() {
    PojoClass aClassWithMethodWithAnnotatedParemeters =
        PojoClassFactory.getPojoClass(AClassWithMethodWithAnnotatedParemeters.class);
    List<PojoMethod> allMethods = aClassWithMethodWithAnnotatedParemeters.getPojoMethods();
    List<PojoMethod> methods = new ArrayList<PojoMethod>();

    for (PojoMethod method : allMethods) {
      if (!method.isConstructor())
        methods.add(method);
    }

    Assert.assertEquals(1, methods.size());

    shouldHaveOneAnnotatedParameter(methods);
  }

  @SuppressWarnings("unused")
  private static class AClassWithMethodWithAnnotatedParemeters {
    public void someMethod(@Annotated String someParameter) {
    }
  }

  @Retention(RUNTIME)
  @Target(PARAMETER)
  @interface Annotated {
  }

  @Retention(RUNTIME)
  @Target(PARAMETER)
  @interface UnusedAnnotation {
  }
}
