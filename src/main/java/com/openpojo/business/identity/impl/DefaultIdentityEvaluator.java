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
import com.openpojo.business.identity.IdentityEvaluator;
import com.openpojo.business.utils.BusinessIdentityUtils;
import com.openpojo.business.utils.BusinessPojoHelper;

/**
 * This class is the default implementation for identity evaluation.
 * The primary function of this class is to evaluate two business entities' equality. <br>
 * <br>
 * Note:
 * if two business fields are defined as optional (aka required = false) and both are set to null,
 * they are considered equal.
 *
 * @author oshoukry
 */
class DefaultIdentityEvaluator implements IdentityEvaluator {
  private static final IdentityEvaluator INSTANCE = new DefaultIdentityEvaluator();

  private DefaultIdentityEvaluator() {
  }

  public static IdentityEvaluator getInstance() {
    return INSTANCE;
  }

  public boolean areEqual(final Object first, final Object second) {
    if (BusinessIdentityUtils.sameInstance(first, second))
      return true;

    boolean runningEquality = true;
    for (BusinessKeyField pojoField : BusinessPojoHelper.getBusinessKeyFields(first.getClass())) {
      runningEquality = runningEquality
          && BusinessIdentityUtils.areEqual(pojoField, first, second, pojoField.isCaseSensitive());
    }
    return runningEquality;
  }

}
