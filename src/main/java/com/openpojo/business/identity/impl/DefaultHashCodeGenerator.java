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

package com.openpojo.business.identity.impl;

import com.openpojo.business.cache.BusinessKeyField;
import com.openpojo.business.exception.BusinessException;
import com.openpojo.business.identity.HashCodeGenerator;
import com.openpojo.business.utils.BusinessIdentityUtils;
import com.openpojo.business.utils.BusinessPojoHelper;

/**
 * This class is the default implementation for hash code generation.
 * The algorithm is simple and conforms to the java spec for hash code generation.
 * <br>
 * Please note that as per spec, two equal hash codes don't mean that objects are equal,
 * while two un-equal hash codes DO mean that the objects are not equal.
 * <br>
 *
 * @author oshoukry
 */

class DefaultHashCodeGenerator implements HashCodeGenerator {
  private static final HashCodeGenerator INSTANCE = new DefaultHashCodeGenerator();

  private DefaultHashCodeGenerator() {
  }

  public static HashCodeGenerator getInstance() {
    return INSTANCE;
  }


  public int doGenerate(final Object object) {
    if (object == null)
      throw BusinessException.getInstance("null parameter passed object=[null]");

    final int prime = 31;
    int result = 1;

    for (BusinessKeyField businessKeyField : BusinessPojoHelper.getBusinessKeyFields(object.getClass()))
      result = prime * result + BusinessIdentityUtils.getHashCode(businessKeyField, object, businessKeyField.isCaseSensitive());
    return result;
  }
}
