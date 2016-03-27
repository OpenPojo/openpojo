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
