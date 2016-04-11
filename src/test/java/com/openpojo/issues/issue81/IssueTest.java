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

package com.openpojo.issues.issue81;

import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.utils.samplejar.SampleJar;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class IssueTest {

  /**
   * This is the main issue experienced.
   */
  @Test
  public void shouldNotThrowNoClassDefFoundError() {
    List<PojoClass> pojoClasses = PojoClassFactory.getPojoClasses("org.testng");
    Assert.assertTrue("Should have found some classes", pojoClasses.size() > 0);
  }

  /**
   * Tests that tackle the area of failure.
   */
  @Test
  public void shouldReturnNullIfFieldTypeIsMissing() throws Exception {
    Class<?> clazz = SampleJar.getURLClassLoader().loadClass("com.failtoload.AClassWithFieldMissing");
    Assert.assertNotNull("expected class not found", clazz);
    Assert.assertNull("Should have failed to load", PojoClassFactory.getPojoClass(clazz));
  }

  @Test
  public void shouldReturnNullIfMethodReturnIsMissing() throws Exception {
    Class<?> clazz = SampleJar.getURLClassLoader().loadClass("com.failtoload.AClassWithMethodReturningMissing");
    Assert.assertNotNull("expected class not found", clazz);
    Assert.assertNull("Should have failed to load", PojoClassFactory.getPojoClass(clazz));
  }

  @Test
  public void shouldReturnNullIfMethodParameterIsMissing() throws Exception {
    Class<?> clazz = SampleJar.getURLClassLoader().loadClass("com.failtoload.AClassWithMethodParameterMissing");
    Assert.assertNotNull("expected class not found", clazz);
    Assert.assertNull("Should have failed to load", PojoClassFactory.getPojoClass(clazz));

  }
}

