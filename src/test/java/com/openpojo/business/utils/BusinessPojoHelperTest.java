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

package com.openpojo.business.utils;

import java.util.List;

import com.openpojo.business.annotation.BusinessKey;
import com.openpojo.business.cache.BusinessKeyField;
import com.openpojo.business.cache.BusinessKeyFieldCache;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.affirm.Affirm;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class BusinessPojoHelperTest {

  @Test(expected = UnsupportedOperationException.class)
  public void shouldThrowExeptionIfConstructed() throws Throwable {
    PojoClass businessPojoHelper = PojoClassFactory.getPojoClass(BusinessPojoHelper.class);

    List<PojoMethod> pojoConstructors = businessPojoHelper.getPojoConstructors();
    Affirm.affirmEquals("Should have only one constructor", 1, pojoConstructors.size());
    Affirm.affirmTrue("Constructor must be private", pojoConstructors.get(0).isPrivate());

    try {
      businessPojoHelper.getPojoConstructors().get(0).invoke(null, (Object[]) null);
    } catch (ReflectionException re) {
      throw re.getCause().getCause();
    }
  }

  @Test
  public void whenGetBusinessKeyFields_FieldsAreCached() {
    List<BusinessKeyField> businessFields = BusinessPojoHelper.getBusinessKeyFields(DummyBusinessPojo.class);

    PojoClass pojoClass = PojoClassFactory.getPojoClass(BusinessPojoHelper.class);

    BusinessKeyFieldCache businessPojoHelperCache = null;
    for (PojoField field : pojoClass.getPojoFields()) {
      if (field.getType() == BusinessKeyFieldCache.class) {
        businessPojoHelperCache = (BusinessKeyFieldCache) field.get(null);
      }
    }

    assert businessPojoHelperCache != null;
    businessPojoHelperCache.add("SomePojo", businessFields);
    Assert.assertEquals(businessFields, businessPojoHelperCache.get(DummyBusinessPojo.class.getName()));
  }

  private static class DummyBusinessPojo {
    @BusinessKey
    private String name;
  }


}