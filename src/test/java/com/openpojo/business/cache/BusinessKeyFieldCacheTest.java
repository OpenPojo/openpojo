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

import java.util.List;

import com.openpojo.business.annotation.BusinessKey;
import com.openpojo.business.utils.BusinessPojoHelper;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class BusinessKeyFieldCacheTest {

  @Test
  public void whenCacheEmptyNullIsReturned() {
    Assert.assertNull(BusinessKeyFieldCache.get("SomePojo"));
  }

  @Test
  public void whenGetBusinessKeyFields_FieldsAreCached() {
    List<BusinessKeyField> businessFields = BusinessPojoHelper.getBusinessKeyFields(DummyBusinessPojo.class);
    BusinessKeyFieldCache.add("SomePojo", businessFields);

    Assert.assertEquals(businessFields, BusinessKeyFieldCache.get(DummyBusinessPojo.class.getName()));
  }

  public static class DummyBusinessPojo {
    @BusinessKey
    private String name;
  }
}
