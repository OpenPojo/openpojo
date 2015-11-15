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

package com.openpojo.business.cache.impl;

import java.util.List;

import com.openpojo.business.annotation.BusinessKey;
import com.openpojo.business.cache.BusinessKeyField;
import com.openpojo.business.utils.BusinessPojoHelper;
import org.junit.Test;
import org.testng.Assert;

/**
 * @author oshoukry
 */
public class DefaultBusinessKeyFieldTest {

  @Test
  public void shouldFormatToString() {
    List<BusinessKeyField> businessKeyFieldList = BusinessPojoHelper.getBusinessKeyFields(AClassWithBusinessKeys.class);
    Assert.assertEquals(1, businessKeyFieldList.size());
    String expectedToString = "DefaultBusinessKeyField [isRequired=true, isComposite=false, isCaseSensitive=true, " +
        "pojoField=PojoFieldImpl [field=private java.lang.String com.openpojo.business.cache.impl" +
        ".DefaultBusinessKeyFieldTest$AClassWithBusinessKeys.name, fieldGetter=null, fieldSetter=null]]";
    Assert.assertEquals(expectedToString, businessKeyFieldList.get(0).toString());
  }

  private static class AClassWithBusinessKeys {
    @BusinessKey
    private String name;
  }
}
