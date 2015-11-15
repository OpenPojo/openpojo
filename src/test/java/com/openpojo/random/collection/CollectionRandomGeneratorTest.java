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
