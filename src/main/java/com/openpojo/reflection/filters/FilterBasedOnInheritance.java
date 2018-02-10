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

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;

/**
 * This filter includes classes that extend/implement a certain type.
 * Note: This class filters out the extended/implemented class/interface.
 *
 * @author oshoukry
 */
public class FilterBasedOnInheritance implements PojoClassFilter {
  private final Class<?> type;

  /**
   * Constructor.
   *
   * @param type
   *     The type/class to use for inclusion (i.e. the "Class" extends/implements type).
   */
  public FilterBasedOnInheritance(final Class<?> type) {
    this.type = type;
  }

  public boolean include(final PojoClass pojoClass) {
    return !pojoClass.getName().equals(type.getName()) && pojoClass.extendz(type);
  }

  @Override
  public boolean equals(Object other) {
    if (this == other)
      return true;
    if (other == null || getClass() != other.getClass())
      return false;

    FilterBasedOnInheritance that = (FilterBasedOnInheritance) other;

    return !(type != null ? !type.equals(that.type) : that.type != null);

  }

  @Override
  public int hashCode() {
    return type != null ? type.hashCode() : 0;
  }
}
