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

import com.openpojo.business.annotation.BusinessKey;
import com.openpojo.business.cache.BusinessKeyField;
import com.openpojo.business.exception.BusinessException;
import com.openpojo.business.identity.BusinessValidator;
import com.openpojo.business.utils.BusinessPojoHelper;

/**
 * This class is the default implementation for business validation.
 * Its primary responsibility is to validate that a business POJO has the following:<br>
 * 1. One or more {@link BusinessKey} fields defined.<br>
 * 2. All Required/Composite fields have values where required.
 *
 * @author oshoukry
 */
class DefaultBusinessValidator implements BusinessValidator {
  private static final BusinessValidator INSTANCE = new DefaultBusinessValidator();

  private DefaultBusinessValidator() {
  }

  public static BusinessValidator getInstance() {
    return INSTANCE;
  }

  public void validate(final Object object) {
    if (object == null) {
      return;
    }

    boolean compositeGroupPassed = false;
    boolean hasCompositeGroup = false;
    boolean hasBusinessKey = false;
    for (BusinessKeyField businessKeyField : BusinessPojoHelper.getBusinessKeyFields(object.getClass())) {
      hasBusinessKey = true;
      if (businessKeyField.isComposite()) {
        if (businessKeyField.get(object) != null) {
          compositeGroupPassed = true;
        }
        hasCompositeGroup = true;
      } else {
        if (businessKeyField.isRequired() && businessKeyField.get(object) == null) {
          throw BusinessException.getInstance((String.format("Field required and can't be null [%s]", businessKeyField)));
        }
      }
    }
    if (!hasBusinessKey) {
      throw BusinessException.getInstance(String.format("No business Keys defined on class=[%s]", object.getClass()));
    }

    if (!compositeGroupPassed && hasCompositeGroup) {
      throw BusinessException.getInstance(String.format("Non of the fields in the composite group were populated [%s]",
          object.getClass()));
    }
  }
}
