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

package com.openpojo.random.collection.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.management.Attribute;
import javax.management.AttributeList;

import com.openpojo.random.RandomFactory;
import com.openpojo.random.collection.support.ALeafChildClass;
import com.openpojo.random.exception.RandomGeneratorException;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.impl.PojoClassFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class AttributeListRandomGeneratorTest {
  private final AttributeListRandomGenerator randomGenerator = AttributeListRandomGenerator.getInstance();
  private final Class<AttributeList> expectedTypeClass = AttributeList.class;

  @Test
  public void constructorShouldBePrivate() {
    PojoClass randomGeneratorPojo = PojoClassFactory.getPojoClass(randomGenerator.getClass());

    List<PojoMethod> constructors = new ArrayList<PojoMethod>();

    for (PojoMethod constructor : randomGeneratorPojo.getPojoConstructors()) {
      if (!constructor.isSynthetic())
        constructors.add(constructor);
    }
    Assert.assertEquals("Should only have one constructor [" + randomGeneratorPojo.getPojoConstructors() + "]", 1, constructors.size());

    PojoMethod constructor = constructors.get(0);

    Assert.assertTrue(constructor.isPrivate());
  }

  @Test
  public void shouldBeAbleToCreate() {
    Assert.assertEquals(AttributeListRandomGenerator.class, randomGenerator.getClass());
  }

  @Test
  public void shouldOnlyReturnCollectionClassFromGetTypes() {
    Collection<Class<?>> types = randomGenerator.getTypes();
    Assert.assertNotNull("Should not be null", types);
    Assert.assertEquals("Should only have one type", 1, types.size());
    Assert.assertEquals("Should only be " + expectedTypeClass.getName(), expectedTypeClass, types.iterator().next());
  }

  @Test
  public void generatedTypeShouldBeAssignableToDeclaredType() {
    Class<?> declaredType = randomGenerator.getTypes().iterator().next();
    Object generatedInstance = randomGenerator.doGenerate(declaredType);
    Assert.assertTrue("[" + declaredType.getName() + " is not assignable to " + generatedInstance.getClass().getName() +
        "]", declaredType.isAssignableFrom(generatedInstance.getClass()));
  }

  @Test(expected = RandomGeneratorException.class)
  public void shouldThrowExceptionForDoGenerateForOtherThanCollectionClass() {
    randomGenerator.doGenerate(ALeafChildClass.class);
  }

  @Test
  public void shouldGenerateCorrectTypeCollectionForRequestedCollection() {
    Collection someObject = randomGenerator.doGenerate(expectedTypeClass);
    Assert.assertNotNull("Should not be null", someObject);
    Assert.assertEquals("Should be a " + expectedTypeClass.getName(), expectedTypeClass, someObject.getClass());
    Assert.assertTrue("Should not be Empty", someObject.size() > 0);
  }

  @Test
  public void endToEnd() {
    Collection<?> generatedCollection = RandomFactory.getRandomValue(expectedTypeClass);
    assertCollectionHasExpectedTypes(generatedCollection, Attribute.class);
  }

  protected void assertCollectionHasExpectedTypes(Collection<?> generatedCollection, Class<?> type) {
    Assert.assertNotNull("Should not be null", generatedCollection);
    Assert.assertEquals(expectedTypeClass, generatedCollection.getClass());
    Assert.assertTrue("Should not be empty", generatedCollection.size() > 0);
    for (Object entry : generatedCollection) {
      Assert.assertNotNull("Should not be null", entry);
      Assert.assertEquals("Entry should be " + type.getName(), type, entry.getClass());
    }
  }

}
