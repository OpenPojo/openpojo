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

package com.openpojo.business.cache.impl;

import com.openpojo.business.annotation.BusinessKey;
import com.openpojo.business.cache.BusinessKeyField;
import com.openpojo.reflection.PojoField;

/**
 * @author oshoukry
 */
public class DefaultBusinessKeyField implements BusinessKeyField {

  private final boolean isComposite;
  private final boolean isCaseSensitive;
  private final boolean isRequired;
  private final PojoField pojoField;

  public DefaultBusinessKeyField(PojoField pojoField) {
    this.pojoField = pojoField;
    BusinessKey annotation = pojoField.getAnnotation(BusinessKey.class);
    isComposite = annotation.composite();
    isCaseSensitive = annotation.caseSensitive();
    isRequired = annotation.required();
  }

  public boolean isComposite() {
    return isComposite;
  }

  public boolean isCaseSensitive() {
    return isCaseSensitive;
  }

  public boolean isRequired() {
    return isRequired;
  }

  public Object get(Object instance) {
    return pojoField.get(instance);
  }

  public boolean isArray() {
    return pojoField.isArray();
  }

  public String toString() {
    return String.format("DefaultBusinessKeyField [isRequired=%s, isComposite=%s, isCaseSensitive=%s, pojoField=%s]",
        isRequired, isComposite, isCaseSensitive, pojoField);
  }
}