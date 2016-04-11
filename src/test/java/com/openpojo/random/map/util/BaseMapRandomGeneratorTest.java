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

package com.openpojo.random.map.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.openpojo.random.ParameterizableRandomGenerator;
import com.openpojo.random.RandomFactory;
import com.openpojo.random.RandomGenerator;
import com.openpojo.random.exception.RandomGeneratorException;
import com.openpojo.random.map.support.ALeafChildClass;
import com.openpojo.random.map.support.SimpleType1;
import com.openpojo.random.map.support.SimpleType2;
import com.openpojo.random.util.SerializableComparableObject;
import com.openpojo.reflection.Parameterizable;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.impl.PojoClassFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author oshoukry
 */
public abstract class BaseMapRandomGeneratorTest {

  protected abstract ParameterizableRandomGenerator getInstance();

  protected abstract Class<? extends ParameterizableRandomGenerator> getGeneratorClass();

  protected abstract Class<? extends Map> getExpectedTypeClass();

  protected abstract Class<? extends Map> getGeneratedTypeClass();

  protected abstract Class<?> getGenericType1();

  protected abstract Class<?> getGenericType2();

  protected Class<?> getDefaultType1() {
    return SerializableComparableObject.class;
  }

  protected Class<?> getDefaultType2() {
    return SerializableComparableObject.class;
  }

  @Test
  public void constructorShouldBePrivate() {
    final Class<?> mapRandomGeneratorClass = getGeneratorClass();
    PojoClass mapRandomGeneratorPojo = PojoClassFactory.getPojoClass(mapRandomGeneratorClass);

    List<PojoMethod> constructors = new ArrayList<PojoMethod>();

    for (PojoMethod constructor : mapRandomGeneratorPojo.getPojoConstructors()) {
      if (!constructor.isSynthetic())
        constructors.add(constructor);
    }
    Assert.assertEquals("Should only have one constructor [" + mapRandomGeneratorPojo.getPojoConstructors() + "]", 1,
        constructors.size());

    PojoMethod constructor = constructors.get(0);

    Assert.assertTrue(constructor.isPrivate());
  }

  @Test
  public void shouldBeAbleToCreate() {
    final RandomGenerator instance = getInstance();
    Assert.assertNotNull(instance);
    Assert.assertEquals(getGeneratorClass(), instance.getClass());
  }

  @Test
  public void shouldOnlyReturnMapClassFromGetTypes() {
    Collection<Class<?>> types = getInstance().getTypes();
    Assert.assertNotNull("Should not be null", types);
    Assert.assertEquals("Should only have one type", 1, types.size());
    Assert.assertEquals("Should only be " + getExpectedTypeClass().getName(), getExpectedTypeClass(), types.iterator().next());
  }

  @Test
  public void generatedTypeShouldBeAssignableToDeclaredType() {
    Class<?> declaredType = getInstance().getTypes().iterator().next();
    Object generatedInstance = getInstance().doGenerate(declaredType);
    Assert.assertTrue("[" + declaredType.getName() + " is not assignable to " + generatedInstance.getClass().getName() +
        "]", declaredType.isAssignableFrom(generatedInstance.getClass()));
  }

  @Test(expected = RandomGeneratorException.class)
  public void shouldThrowExceptionForDoGenerateForOtherThanMapClass() {
    getInstance().doGenerate(ALeafChildClass.class);
  }

  @Test(expected = RandomGeneratorException.class)
  public void shouldThrowExceptionForDoGenerateForParameterizedOtherThanMapClass() {
    getInstance().doGenerate(new Parameterizable() {
      public Class<?> getType() {
        return ALeafChildClass.class;
      }

      public boolean isParameterized() {
        throw new IllegalStateException("Unimplemented!!");
      }

      public List<Type> getParameterTypes() {
        throw new IllegalStateException("Unimplemented!!");
      }
    });
  }

  @Test
  public void shouldGenerateCorrectTypeMapForRequestedMap() {
    Map someObject = (Map) getInstance().doGenerate(getExpectedTypeClass());
    Assert.assertNotNull("Should not be null", someObject);
    Assert.assertEquals("Should be a " + getGeneratedTypeClass().getName(), getGeneratedTypeClass(), someObject.getClass());
    Assert.assertTrue("Should not be Empty", someObject.size() > 0);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void shouldGenerateParametrizableCorrectMapForRequest() {
    Map<SimpleType1, SimpleType2> mapOfType1AndType2 = (Map) getInstance().doGenerate(getParameterizedType());

    Assert.assertNotNull("Should not be null", mapOfType1AndType2);
    Assert.assertTrue("Should not be empty", mapOfType1AndType2.size() > 0);
    for (Map.Entry<?, ?> entry : mapOfType1AndType2.entrySet()) {
      Assert.assertNotNull("Should not be null", entry);
      Assert.assertEquals("Key should be " + getGenericType1().getName(), getGenericType1(), entry.getKey().getClass());
      Assert.assertEquals("Value be " + getGenericType2().getName(), getGenericType2(), entry.getValue().getClass());
    }
  }

  @Test
  public void endToEnd() {
    Map<?, ?> generatedMap = RandomFactory.getRandomValue(getExpectedTypeClass());
    assertMapHasExpectedTypes(generatedMap, getDefaultType1(), getDefaultType2());
  }

  protected void assertMapHasExpectedTypes(Map<?, ?> generatedMap, Class<?> type1, Class<?> type2) {
    Assert.assertNotNull("Should not be null", generatedMap);
    Assert.assertEquals(getGeneratedTypeClass(), generatedMap.getClass());
    Assert.assertTrue("Should not be empty", generatedMap.size() > 0);
    for (Map.Entry<?, ?> entry : generatedMap.entrySet()) {
      Assert.assertNotNull("Should not be null", entry.getKey());
      Assert.assertEquals("Key should be " + type1.getName(), type1, entry.getKey().getClass());
      Assert.assertNotNull("Should not be null", entry.getValue());
      Assert.assertEquals("Key should be " + type2.getName(), type2, entry.getValue().getClass());
    }
  }

  @Test
  public void endToEndWithGenerics() {
    Map<?, ?> generatedMap = (Map) RandomFactory.getRandomValue(getParameterizedType());
    assertMapHasExpectedTypes(generatedMap, getGenericType1(), getGenericType2());
  }

  protected Parameterizable getParameterizedType() {
    return new Parameterizable() {
      private Type[] types = new Type[] { getGenericType1(), getGenericType2() };

      public Class<?> getType() {
        return getExpectedTypeClass();
      }

      public boolean isParameterized() {
        return true;
      }

      public List<Type> getParameterTypes() {
        return Arrays.asList(types);
      }
    };
  }
}
