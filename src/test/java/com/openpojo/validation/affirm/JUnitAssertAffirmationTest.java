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

package com.openpojo.validation.affirm;

import com.openpojo.reflection.construct.InstanceFactory;
import com.openpojo.reflection.impl.PojoClassFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class JUnitAssertAffirmationTest extends AbstractAffirmationTest {
  private final Affirmation jUnitAssertAffirmation = (Affirmation) InstanceFactory.getInstance(PojoClassFactory.getPojoClass
      (JUnitAssertAffirmation.class));

  @Override
  public Affirmation getAffirmation() {
    return jUnitAssertAffirmation;
  }

  @Test
  public void shouldTestToString() {
    Assert.assertTrue(String.format("[%s] signature changed? expected=[%s]!!",
        JUnitAssertAffirmation.class.getName(), "com.openpojo.validation.affirm.JUnitAssertAffirmation [@xxxxxx: ]"),
        jUnitAssertAffirmation.toString().startsWith("com.openpojo.validation.affirm.JUnitAssertAffirmation [@")
            && jUnitAssertAffirmation.toString().endsWith(":" + " ]"));
  }
}
