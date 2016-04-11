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

package com.openpojo.reflection.filters;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class FilterChainTest extends IdentitiesAreEqual {

  @Test
  public void newFilterChain_hasNoFilters() {
    FilterChain filter = new FilterChain();
    Assert.assertEquals(0, filter.size());
  }

  @Test
  public void filterChain_shouldIgonreNullFilterArray() {
    FilterChain filter = new FilterChain((PojoClassFilter[]) null);
    Assert.assertEquals(0, filter.size());
  }

  @Test
  public void oneOneFilterAdded_FilterChainHasOneFilter() {
    PojoClassFilter dummyFilter = new DummyPojoClassFilter();
    FilterChain filter = new FilterChain(dummyFilter);
    Assert.assertEquals(1, filter.size());
    Assert.assertTrue(filter.getPojoClassFilters().contains(dummyFilter));
  }

  @Test
  public void addingArrayWithNullFilters_ignored() {
    PojoClassFilter dummyFilter = new DummyPojoClassFilter();
    FilterChain filter = new FilterChain(dummyFilter, null);
    Assert.assertEquals(1, filter.size());
    Assert.assertTrue(filter.getPojoClassFilters().contains(dummyFilter));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void retrivedFilterCollectionIsUnmodifiable() {
    FilterChain filter = new FilterChain();
    filter.getPojoClassFilters().add(new DummyPojoClassFilter());
  }

  @Test
  public void shouldBeIdentityEqual() {
    FilterChain instanceOne = new FilterChain();
    FilterChain instanceTwo = new FilterChain();

    checkEqualityAndHashCode(instanceOne, instanceTwo);
  }

  private class DummyPojoClassFilter implements PojoClassFilter {
    public boolean include(PojoClass pojoClass) {
      return false;
    }

  }
}
