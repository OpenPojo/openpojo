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

package com.openpojo.reflection.coverage.impl;

import com.openpojo.reflection.PojoClassFilter;
import com.openpojo.reflection.adapt.PojoClassAdapter;
import com.openpojo.reflection.adapt.impl.CloverPojoClassAdapter;
import com.openpojo.reflection.filters.FilterCloverClasses;

/**
 * @author oshoukry
 */
public class Clover4 extends AbstractCoverageDetector {
  private static final Clover4 INSTANCE = new Clover4();

  private Clover4() {
  }

  public static Clover4 getInstance() {
    return INSTANCE;
  }

  @Override
  public String getName() {
    return "Clover 4";
  }

  @Override
  public String getCoverageClassName() {
    return "com_atlassian_clover.TestNameSniffer";
  }

  @Override
  public PojoClassFilter getPojoClassFilter() {
    return FilterCloverClasses.getInstance();
  }

  @Override
  public PojoClassAdapter getPojoClassAdapter() {
    return CloverPojoClassAdapter.getInstance();
  }
}
