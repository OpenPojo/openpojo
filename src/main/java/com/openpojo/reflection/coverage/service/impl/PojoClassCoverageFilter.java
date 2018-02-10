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

package com.openpojo.reflection.coverage.service.impl;

import java.util.HashSet;
import java.util.Set;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;

/**
 * @author oshoukry
 */
public class PojoClassCoverageFilter implements PojoClassFilter {
  private Set<PojoClassFilter> filters = new HashSet<PojoClassFilter>();

  public void add(PojoClassFilter pojoClassFilter) {
    if (pojoClassFilter != null) {
      filters.add(pojoClassFilter);
    }
  }

  public boolean include(PojoClass pojoClass) {
    for (PojoClassFilter filter : filters) {
      if (!filter.include(pojoClass))
        return false;
    }
    return true;
  }
}
