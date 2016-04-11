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

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;

import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.java.type.impl.GenericArrayTypeResolver;
import com.openpojo.reflection.java.type.sample.AClassWithGenericArrayType;
import com.openpojo.reflection.java.type.sample.ArrayElementsClass;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class GenericArrayTypeResolverTest {
  private GenericArrayTypeResolver genericArrayTypeResolver = new GenericArrayTypeResolver();

  @Test
  public void canResolveIntArray() {
    Type type = genericArrayTypeResolver.resolveType(createGenericArrayTypeFor(int.class));
    Assert.assertTrue(int[].class == type);
  }

  @Test
  public void canResolveIntegerArray() {
    Type type = genericArrayTypeResolver.resolveType(createGenericArrayTypeFor(Object.class));
    Assert.assertTrue(Object[].class == type);
  }

  @Test
  public void throwsUnsupportedExceptionForGetEnclosingType() {
    try {
      genericArrayTypeResolver.getEnclosingType(createGenericArrayTypeFor(int.class));
      Assert.fail("Expected exception not thrown");
    } catch (UnsupportedOperationException uoe) {
      Assert.assertEquals("This operation is Not Supported, if you ran into this please report this issue @ http://openpojo.com",
          uoe.getMessage());
    }
  }

  @Test
  public void throwsUnsupportedExceptionForGetParameterTypes() {
    try {
      genericArrayTypeResolver.getParameterTypes(createGenericArrayTypeFor(int.class));
      Assert.fail("Expected exception not thrown");
    } catch (UnsupportedOperationException uoe) {
      Assert.assertEquals("This operation is Not Supported, if you ran into this please report this issue @ http://openpojo.com",
          uoe.getMessage());
    }
  }

  @Test
  public void end2endTest() {
    AClassWithGenericArrayType aClassWithGenericArray = RandomFactory.getRandomValue(AClassWithGenericArrayType.class);
    Assert.assertNotNull(aClassWithGenericArray);
    Assert.assertTrue(aClassWithGenericArray.getMySetOfArrayElements().size() > 0);
    Object[] objects = aClassWithGenericArray.getMySetOfArrayElements().toArray();
    Assert.assertTrue(objects.length > 0);
    Assert.assertTrue(objects[0] instanceof ArrayElementsClass);
  }

  private GenericArrayType createGenericArrayTypeFor(final Class<?> type) {
    return new GenericArrayType() {
      public Type getGenericComponentType() {
        return type;
      }
    };
  }
}
