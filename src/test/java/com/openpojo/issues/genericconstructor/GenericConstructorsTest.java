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

package com.openpojo.issues.genericconstructor;

import com.openpojo.issues.genericconstructor.sample.ClassWithGenericListIntegerConstructor;
import com.openpojo.issues.genericconstructor.sample.ClassWithGenericSetEnumConstructor;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.construct.InstanceFactory;
import com.openpojo.reflection.impl.PojoClassFactory;
import org.junit.Test;
import org.testng.Assert;

/**
 * @author oshoukry
 */
public class GenericConstructorsTest {

  @Test
  public void shouldConstructClassWithGenericListIntegerConstructor() {
    PojoClass pojoClass = PojoClassFactory.getPojoClass(ClassWithGenericListIntegerConstructor.class);
    ClassWithGenericListIntegerConstructor instance =
        (ClassWithGenericListIntegerConstructor) InstanceFactory.getMostCompleteInstance(pojoClass);
    Assert.assertNotNull(instance);
    Assert.assertNotNull(instance.getIntegers());
    Assert.assertTrue(instance.getIntegers().size() > 0);

    Assert.assertNotNull(instance.getMymap());
    Assert.assertNotNull(instance.getString());
  }

  @Test
  public void shouldConstructClassWithGenericSetEnumConstructor() {
    PojoClass pojoClass = PojoClassFactory.getPojoClass(ClassWithGenericSetEnumConstructor.class);
    ClassWithGenericSetEnumConstructor instance =
        (ClassWithGenericSetEnumConstructor) InstanceFactory.getMostCompleteInstance(pojoClass);
    Assert.assertNotNull(instance);
    Assert.assertNotNull(instance.getDaysOfTheWeek());
  }

}
