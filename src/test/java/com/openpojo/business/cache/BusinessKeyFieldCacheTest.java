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

package com.openpojo.business.cache;

import java.util.ArrayList;
import java.util.List;

import com.openpojo.validation.affirm.Affirm;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class BusinessKeyFieldCacheTest {
  private BusinessKeyFieldCache cache;

  @Before
  public void setup() {
    cache = new BusinessKeyFieldCache();
  }

  @Test
  public void whenCacheEmptyNullIsReturned() {
    Assert.assertNull(cache.get("SomePojo"));
  }

  @Test
  public void whenEntryAddedCanBeReturned() {
    List<BusinessKeyField> someList = new ArrayList<BusinessKeyField>();

    String anyCacheEntry = "SomeClass";
    cache.add(anyCacheEntry, someList);

    Affirm.affirmTrue("Should keep the same instance", someList == cache.get(anyCacheEntry));
  }
}
