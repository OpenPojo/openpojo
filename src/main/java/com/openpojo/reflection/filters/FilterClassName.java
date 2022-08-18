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

import java.util.regex.Pattern;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;

/**
 * Filter classes based on class name regular expression
 *
 * Example: ".+Domain$" to only include classes that end in Domain "^((?!Test$).)*$" to exclude classes that end with
 * Test
 *
 * This implementation was submitted on issue 20.
 *
 * @author oshoukry
 */
public class FilterClassName implements PojoClassFilter {
  //Since Pattern doesn't implement equals, relying on String for equals & hashCode instead.
  private final String regex;
  private final Pattern pattern;

  public FilterClassName(String regex) {
    this.regex = regex;
    this.pattern = Pattern.compile(regex);
  }

  public boolean include(PojoClass pojoClass) {
    return pattern.matcher(pojoClass.getName()).find();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    FilterClassName that = (FilterClassName) o;

    return regex.equals(that.regex);
  }

  @Override
  public int hashCode() {
    return regex.hashCode();
  }

}
