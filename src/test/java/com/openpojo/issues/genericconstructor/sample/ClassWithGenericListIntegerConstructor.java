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

package com.openpojo.issues.genericconstructor.sample;

import java.util.List;
import java.util.Map;


public class ClassWithGenericListIntegerConstructor {
  private final List<List<Integer>> integers;
  private final Map<String, Integer> mymap;
  private final String string;

  public ClassWithGenericListIntegerConstructor(final List<List<Integer>> integers, final Map<String, Integer> mymap, final
  String string) {
    this.integers = integers;
    this.mymap = mymap;
    this.string = string;
  }

  public List<List<Integer>> getIntegers() {
    return integers;
  }

  public Map<String, Integer> getMymap() {

    return mymap;
  }

  public String getString() {
    return string;
  }
}
