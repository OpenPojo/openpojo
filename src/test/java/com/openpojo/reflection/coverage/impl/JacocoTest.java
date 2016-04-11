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

import com.openpojo.reflection.adapt.impl.JacocoPojoClassAdapter;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class JacocoTest {

  @Test
  public void shouldHaveJacocoPojoClassAdapter() {
    Assert.assertEquals(JacocoPojoClassAdapter.class, Jacoco.getInstance().getPojoClassAdapter().getClass());
  }

  @Test
  public void shouldHaveNoClassFilter() {
    Assert.assertNull(Jacoco.getInstance().getPojoClassFilter());
  }

  @Test
  public void nameIsJacoco() {
    Assert.assertEquals("Jacoco", Jacoco.getInstance().getName());
  }

  @Test
  public void coverageClassNameIs__orgDOTjacocoDOTagentDOTrtDOTIAgent() {
    Assert.assertEquals("org.jacoco.agent.rt.IAgent", Jacoco.getInstance().getCoverageClassName());
  }
}
