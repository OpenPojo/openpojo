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

package com.openpojo.issues.issue29;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.adapt.PojoClassAdapter;
import com.openpojo.reflection.adapt.impl.JacocoPojoClassAdapter;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.affirm.Affirm;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class IssueTest {
  private static final String JACOCO_FIELD_NAME = "$jacocoData";
  private static final String JACOCO_METHOD_NAME = "$jacocoInit";

  @SuppressWarnings("unused")
  public static transient boolean[] $jacocoData;

  @SuppressWarnings("unused")
  public static Class<?> $jacocoInit() {
    return null;
  }


  @Test
  public void shouldHideJacocoFieldAndMethod() throws NoSuchFieldException, NoSuchMethodException {
    Field field = this.getClass().getDeclaredField(JACOCO_FIELD_NAME);
    Assert.assertNotNull("Should not be null", field);

    Method method = this.getClass().getDeclaredMethod(JACOCO_METHOD_NAME);
    Assert.assertNotNull("Should not be null", method);

    PojoClassAdapter jacocoPojoClassAdapter = JacocoPojoClassAdapter.getInstance();
    PojoClass cleansedPojoClass = jacocoPojoClassAdapter.adapt(PojoClassFactory.getPojoClass(this.getClass()));

    for (PojoField pojoField : cleansedPojoClass.getPojoFields()) {
      if (pojoField.getName().equals(JACOCO_FIELD_NAME)) {
        Affirm.fail(JACOCO_FIELD_NAME + " field is still visible!!");
      }
    }

    for (PojoMethod pojoMethod : cleansedPojoClass.getPojoMethods()) {
      if (pojoMethod.getName().equals(JACOCO_METHOD_NAME)) {
        Affirm.fail(JACOCO_METHOD_NAME + " method is still visible!!");
      }
    }

    Assert.assertNotNull(this.getClass().getDeclaredField("JACOCO_FIELD_NAME"));
    Assert.assertNotNull(this.getClass().getDeclaredField("JACOCO_METHOD_NAME"));
    Assert.assertNotNull(this.getClass().getDeclaredMethod("shouldHideJacocoFieldAndMethod"));

  }
}
