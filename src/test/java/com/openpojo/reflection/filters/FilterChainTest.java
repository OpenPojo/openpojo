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
