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

package com.openpojo.validation.utils;

import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.reflection.impl.PojoClassFactory;
import org.junit.Assert;
import org.junit.Test;

public class ValidationHelperTest {

  @Test
  public void testIsStaticFinal() {
    PojoClass pojoClass = PojoClassFactory.getPojoClass(StaticFinalData.class);
    List<PojoField> pojoFields = pojoClass.getPojoFields();
    Assert.assertEquals(4, pojoFields.size());
    for (PojoField fieldEntry : pojoFields) {
      if (fieldEntry.getName() == "staticAndNotFinal") {
        Assert.assertTrue("Static and not Final test failed!!",
            fieldEntry.isStatic()
                && !fieldEntry.isFinal()
                && !ValidationHelper.isStaticFinal(fieldEntry));
      }
      if (fieldEntry.getName() == "notStaticAndNotFinal") {
        Assert.assertTrue("Not static OR final test failed!!",
            !fieldEntry.isStatic()
                && !fieldEntry.isFinal()
                && !ValidationHelper.isStaticFinal(fieldEntry));
      }
      if (fieldEntry.getName() == "STATICANDFINAL") {
        Assert.assertTrue("Static AND Final test failed!!!",
            fieldEntry.isStatic()
                && fieldEntry.isFinal()
                && ValidationHelper.isStaticFinal(fieldEntry));
      }
      if (fieldEntry.getName() == "finalAndNotStatic") {
        Assert.assertTrue("Final and not Static test failed!!",
            !fieldEntry.isStatic()
                && fieldEntry.isFinal()
                && !ValidationHelper.isStaticFinal(fieldEntry));
      }
    }
  }

  private static class StaticFinalData {

    @SuppressWarnings("unused")
    private static int staticAndNotFinal = 0;

    @SuppressWarnings("unused")
    private int notStaticAndNotFinal;

    @SuppressWarnings("unused")
    private static final int STATICANDFINAL = 0;

    @SuppressWarnings("unused")
    private final int finalAndNotStatic = 0;
  }
}
