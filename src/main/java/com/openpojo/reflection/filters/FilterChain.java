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

package com.openpojo.reflection.filters;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;

/**
 * @author oshoukry
 */
public class FilterChain implements PojoClassFilter {
  private final Set<PojoClassFilter> pojoClassFilters = new LinkedHashSet<PojoClassFilter>();

  public FilterChain(final PojoClassFilter... pojoClassFilters) {
    if (pojoClassFilters != null)
      for (PojoClassFilter pojoClassFilter : pojoClassFilters) {
        if (pojoClassFilter != null) {
          this.pojoClassFilters.add(pojoClassFilter);
        }
      }
  }

  public boolean include(final PojoClass pojoClass) {
    for (PojoClassFilter pojoClassFilter : pojoClassFilters) {
      if (!pojoClassFilter.include(pojoClass))
        return false;
    }
    return true;
  }

  public Collection<PojoClassFilter> getPojoClassFilters() {
    return Collections.unmodifiableSet(pojoClassFilters);
  }

  public int size() {
    return pojoClassFilters.size();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    FilterChain that = (FilterChain) o;

    return pojoClassFilters.equals(that.pojoClassFilters);
  }

  @Override
  public int hashCode() {
    return pojoClassFilters.hashCode();
  }
}
