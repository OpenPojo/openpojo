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

package com.openpojo.random.collection.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.openpojo.random.ParameterizableRandomGenerator;
import com.openpojo.random.RandomFactory;
import com.openpojo.random.RandomGenerator;
import com.openpojo.random.collection.support.ALeafChildClass;
import com.openpojo.random.exception.RandomGeneratorException;
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
public abstract class BaseCollectionRandomGeneratorTest {

  protected abstract ParameterizableRandomGenerator getInstance();

  protected abstract Class<? extends ParameterizableRandomGenerator> getGeneratorClass();

  protected abstract Class<? extends Collection> getExpectedTypeClass();

  protected abstract Class<? extends Collection> getGeneratedTypeClass();

  protected abstract Class<?> getGenericType();

  protected Class<?> getDefaultType() {
    return SerializableComparableObject.class;
  }

  protected boolean validateCollectionContents() {
    return true;
  }

  @Test
  public void constructorShouldBePrivate() {
    final Class<?> randomGeneratorClass = getGeneratorClass();
    PojoClass randomGeneratorPojo = PojoClassFactory.getPojoClass(randomGeneratorClass);

    List<PojoMethod> constructors = new ArrayList<PojoMethod>();

    for (PojoMethod constructor : randomGeneratorPojo.getPojoConstructors()) {
      if (!constructor.isSynthetic())
        constructors.add(constructor);
    }
    Assert.assertEquals("Should only have one constructor [" + randomGeneratorPojo.getPojoConstructors() + "]", 1,
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
  public void shouldOnlyReturnCollectionClassFromGetTypes() {
    Collection<Class<?>> types = getInstance().getTypes();
    Assert.assertNotNull("Should not be null", types);
    Assert.assertEquals("Should only have one type", 1, types.size());
    Assert.assertEquals("Should only be " + getExpectedTypeClass().getName(), getExpectedTypeClass(), types.iterator().next());
  }

  @Test
  public void generatedTypeShouldBeAssignableToDeclaredType() {
    Class<?> declaredType = getInstance().getTypes().iterator().next();
    Object generatedInstance = getInstance().doGenerate(declaredType);
    Assert.assertTrue("[" + declaredType.getName() + " is not assignable to " + generatedInstance.getClass().getName() + "]",
        declaredType.isAssignableFrom(generatedInstance.getClass()));
  }

  @Test(expected = RandomGeneratorException.class)
  public void shouldThrowExceptionForDoGenerateForOtherThanCollectionClass() {
    getInstance().doGenerate(ALeafChildClass.class);
  }

  @Test(expected = RandomGeneratorException.class)
  public void shouldThrowExceptionForDoGenerateForParameterizedOtherThanCollectionClass() {
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
  public void shouldGenerateCorrectTypeCollectionForRequestedCollection() {
    Collection someObject = (Collection) getInstance().doGenerate(getExpectedTypeClass());
    Assert.assertNotNull("Should not be null", someObject);
    Assert.assertEquals("Should be a " + getGeneratedTypeClass().getName(), getGeneratedTypeClass(), someObject.getClass());
    if (validateCollectionContents())
      Assert.assertTrue("Should not be Empty", someObject.size() > 0);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void shouldGenerateParametrizableCorrectCollectionForRequest() {
    Collection<?> collectionOfType = (Collection) getInstance().doGenerate(getParameterizedType());

    Assert.assertNotNull("Should not be null", collectionOfType);
    if (validateCollectionContents())
      Assert.assertTrue("Should not be empty", collectionOfType.size() > 0);
    for (Object entry : collectionOfType) {
      Assert.assertNotNull("Should not be null", entry);
      Assert.assertEquals("Entry should be " + getGenericType().getName(), getGenericType(), entry.getClass());
    }
  }

  @Test
  public void endToEnd() {
    Collection<?> generatedCollection = RandomFactory.getRandomValue(getExpectedTypeClass());
    assertCollectionHasExpectedTypes(generatedCollection, getDefaultType());
  }

  protected void assertCollectionHasExpectedTypes(Collection<?> generatedCollection, Class<?> type) {
    Assert.assertNotNull("Should not be null", generatedCollection);
    Assert.assertEquals(getGeneratedTypeClass(), generatedCollection.getClass());
    if (validateCollectionContents())
      Assert.assertTrue("Should not be empty", generatedCollection.size() > 0);
    for (Object entry : generatedCollection) {
      Assert.assertNotNull("Should not be null", entry);
      Assert.assertEquals("Entry should be " + type.getName(), type, entry.getClass());
    }
  }

  @Test
  public void endToEndWithGenerics() {
    Collection<?> generatedCollection = (Collection) RandomFactory.getRandomValue(getParameterizedType());
    assertCollectionHasExpectedTypes(generatedCollection, getGenericType());
  }

  protected Parameterizable getParameterizedType() {
    return new Parameterizable() {
      private Type[] types = new Type[] { getGenericType() };

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
