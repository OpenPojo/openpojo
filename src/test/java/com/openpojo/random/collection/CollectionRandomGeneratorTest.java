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

package com.openpojo.random.collection;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.openpojo.random.RandomFactory;
import com.openpojo.random.collection.sample.AClassWithExhaustiveCollection;
import com.openpojo.random.collection.support.ALeafChildClass;
import com.openpojo.random.exception.RandomGeneratorException;
import com.openpojo.reflection.Parameterizable;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class CollectionRandomGeneratorTest {

  @Test
  public void shouldBeAbleToCreate() {
    Assert.assertNotNull(CollectionRandomGenerator.getInstance());
    Assert.assertEquals(CollectionRandomGenerator.class, CollectionRandomGenerator.getInstance().getClass());
  }

  @Test
  public void shouldGenerateForCollectionOnly() {
    Collection<Class<?>> types = CollectionRandomGenerator.getInstance().getTypes();
    Assert.assertEquals(1, types.size());
    Assert.assertEquals(Collection.class, types.iterator().next());
  }

  @Test(expected = RandomGeneratorException.class)
  public void whenGenerateWithCollection_ThrowsException() {
    CollectionRandomGenerator.getInstance().doGenerate(ALeafChildClass.class);
  }

  @Test
  public void whenGenerateWithCollection_ReturnNonEmpty() {
    Collection collection = CollectionRandomGenerator.getInstance().doGenerate(Collection.class);
    Assert.assertNotNull("Should not be null", collection);
    Assert.assertTrue("Should not be empty", collection.size() > 0);
    Assert.assertTrue("Should be of type ArrayList", collection instanceof ArrayList);

  }

  @Test
  @SuppressWarnings("unchecked")
  public void whenGenerateWithGeneric_GenerateCorrectly() {
    Parameterizable parameterizable = new Parameterizable() {
      public Class<?> getType() {
        return Collection.class;
      }

      public boolean isParameterized() {
        return true;
      }

      public List<Type> getParameterTypes() {
        return Arrays.asList(new Type[] { String.class });
      }
    };

    Collection<String> aCollectionOfStrings;
    aCollectionOfStrings = (Collection<String>) CollectionRandomGenerator.getInstance().doGenerate(parameterizable);
    Assert.assertNotNull("Should not be null", aCollectionOfStrings);
    Assert.assertTrue("Should have entries", aCollectionOfStrings.size() > 0);
    for (Object s : aCollectionOfStrings) {
      Assert.assertNotNull(s);
      Assert.assertTrue("Should be String", s instanceof String);
    }
  }

  @Test
  public void testEndToEnd() {
    Collection collection = RandomFactory.getRandomValue(Collection.class);
    Assert.assertNotNull("Should not be null", collection);
    Assert.assertTrue("Should not be empty", collection.size() > 0);
    Assert.assertTrue("Should be an ArrayList", collection instanceof ArrayList);
    collection = RandomFactory.getRandomValue(Collection.class); // double check in case.
    Assert.assertTrue("Should be an ArrayList", collection instanceof ArrayList);
  }

  @Test
  public void exhaustiveTest() {
    PojoClass pojoClass = PojoClassFactory.getPojoClass(AClassWithExhaustiveCollection.class);
    Validator pojoValidator = ValidatorBuilder.create()
        .with(new SetterMustExistRule())
        .with(new SetterTester())
        .build();

    pojoValidator.validate(pojoClass);
  }
}
