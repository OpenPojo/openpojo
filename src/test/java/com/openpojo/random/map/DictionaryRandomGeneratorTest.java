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

package com.openpojo.random.map;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.openpojo.random.RandomFactory;
import com.openpojo.random.map.support.SimpleType1;
import com.openpojo.random.map.support.SimpleType2;
import com.openpojo.reflection.Parameterizable;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class DictionaryRandomGeneratorTest {

  @Test
  public void canGenerateDictionary() {
    Assert.assertNotNull("Should not be null", RandomFactory.getRandomValue(Dictionary.class));
  }

  @Test
  public void dictionaryCreatedIsHashtable() {
    Object object = RandomFactory.getRandomValue(Dictionary.class);
    Assert.assertNotNull(object);
    Assert.assertEquals("Should be HashTable instance", Hashtable.class, object.getClass());
  }

  @Test
  public void shouldNotBeEmpty() {
    Hashtable hashtable = (Hashtable) RandomFactory.getRandomValue(Dictionary.class);
    Assert.assertNotNull(hashtable);
    Assert.assertTrue("Should not be empty", hashtable.size() > 0);
  }

  @Test
  public void canGenerateGenerics() {
    Hashtable<?, ?> hashtable = (Hashtable) RandomFactory.getRandomValue(new Parameterizable() {
      public Class<?> getType() {
        return Dictionary.class;
      }

      public boolean isParameterized() {
        return true;
      }

      public List<Type> getParameterTypes() {
        List<Type> parameterTypes = new ArrayList<Type>(2);
        parameterTypes.add(SimpleType1.class);
        parameterTypes.add(SimpleType2.class);
        return parameterTypes;
      }
    });
    Assert.assertNotNull("Should not be null", hashtable);
    Assert.assertTrue("Should not be empty", !hashtable.isEmpty());
    for (Map.Entry<?, ?> entry : hashtable.entrySet()) {
      Assert.assertNotNull("Should not be null entry", entry);
      Assert.assertNotNull("Should not be null entry.getKey()", entry.getKey());
      Assert.assertEquals("Should be of type SimpleType1.class", SimpleType1.class, entry.getKey().getClass());
      Assert.assertNotNull("Should not be null entry.getValue()", entry.getValue());
      Assert.assertEquals("Should be of type SimpleType2.class", SimpleType2.class, entry.getValue().getClass());
    }
  }
}
