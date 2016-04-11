/*
 * Copyright (c) 2010-2016 Osman Shoukry
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

import com.openpojo.reflection.adapt.impl.CloverPojoClassAdapter;
import com.openpojo.reflection.filters.FilterCloverClasses;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class Clover4Test {

  @Test
  public void shouldHaveCloverPojoClassAdapter() {
    Assert.assertEquals(CloverPojoClassAdapter.class, Clover4.getInstance().getPojoClassAdapter().getClass());
  }

  @Test
  public void shouldHaveFilterCloverClasses() {
    Assert.assertEquals(FilterCloverClasses.class, Clover4.getInstance().getPojoClassFilter().getClass());
  }

  @Test
  public void nameIsClover4() {
    Assert.assertEquals("Clover 4", Clover4.getInstance().getName());
  }

  @Test
  public void coverageClassNameIs__com_atlassian_cloverDOTTestNameSniffer() {
    Assert.assertEquals("com_atlassian_clover.TestNameSniffer", Clover4.getInstance().getCoverageClassName());
  }

}
