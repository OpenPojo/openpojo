/*
 * Copyright (c) 2010-2018 Osman Shoukry
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

import java.util.LinkedList;
import java.util.List;

import com.openpojo.business.annotation.BusinessKey;
import com.openpojo.business.cache.BusinessKeyField;
import com.openpojo.business.cache.BusinessKeyFieldCache;
import com.openpojo.business.cache.impl.DefaultBusinessKeyField;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.reflection.impl.PojoClassFactory;

/**
 * This Utility helper class that holds the logic of extracting the {@link BusinessKey} fields from a Class.
 *
 * @author oshoukry
 */
public class BusinessPojoHelper {
  private static BusinessKeyFieldCache cache = new BusinessKeyFieldCache();

  /**
   * Get all business keys declared on a class and the parent super classes.
   *
   * @param clazz
   *     The class to introspect.
   * @return The list of fields that are annotated with @BusinessKey, will return an empty list if none are found.
   */
  public static List<BusinessKeyField> getBusinessKeyFields(final Class<?> clazz) {

    List<BusinessKeyField> businessKeyFields = cache.get(clazz.getName());
    if (businessKeyFields != null) {
      return businessKeyFields;
    }

    businessKeyFields = new LinkedList<BusinessKeyField>();

    PojoClass pojoClass = PojoClassFactory.getPojoClass(clazz);
    while (pojoClass != null) {
      for (PojoField pojoField : pojoClass.getPojoFieldsAnnotatedWith(BusinessKey.class)) {
        businessKeyFields.add(new DefaultBusinessKeyField(pojoField));
      }
      pojoClass = pojoClass.getSuperClass();
    }

    cache.add(clazz.getName(), businessKeyFields);
    return businessKeyFields;
  }

  private BusinessPojoHelper() {
    throw new UnsupportedOperationException(BusinessPojoHelper.class.getName() +  " should not be constructed!");
  }
}
