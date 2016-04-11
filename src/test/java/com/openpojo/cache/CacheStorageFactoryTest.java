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

package com.openpojo.cache;

import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.affirm.Affirm;
import org.junit.Test;
import org.testng.Assert;

/**
 * @author oshoukry
 */
public class CacheStorageFactoryTest {

  @Test(expected = UnsupportedOperationException.class)
  public void shouldThrowExeptionIfConstructed() throws Throwable {
    PojoClass cacheStorageFactoryPojo = PojoClassFactory.getPojoClass(CacheStorageFactory.class);

    List<PojoMethod> pojoConstructors = cacheStorageFactoryPojo.getPojoConstructors();
    Affirm.affirmEquals("Should have only one constructor", 1, pojoConstructors.size());
    Affirm.affirmTrue("Constructor must be private", pojoConstructors.get(0).isPrivate());

    try {
      pojoConstructors.get(0).invoke(null, (Object[]) null);
    } catch (ReflectionException re) {
      throw re.getCause().getCause();
    }

  }

  @Test
  public void shouldReturnTemporalCache() {
    CacheStorage<String> keyValuePairCache = CacheStorageFactory.getTemporalCacheStorage();
    String expectedKey = "SomeKey";
    String expectedValue = "SomeValue";
    keyValuePairCache.add(expectedKey, expectedValue);
    Assert.assertEquals(expectedValue, keyValuePairCache.get(expectedKey));
    System.gc();
    Assert.assertNull(keyValuePairCache.get(expectedKey));
  }

  @Test
  public void shouldReturnPersistentCache() {
    CacheStorage<String> keyValuePairCache = CacheStorageFactory.getPersistentCacheStorage();
    String expectedKey = "SomeKey";
    String expectedValue = "SomeValue";
    keyValuePairCache.add(expectedKey, expectedValue);
    Assert.assertEquals(expectedValue, keyValuePairCache.get(expectedKey));
    System.gc();
    Assert.assertEquals(expectedValue, keyValuePairCache.get(expectedKey));
  }
}
