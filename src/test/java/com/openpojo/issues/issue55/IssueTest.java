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

package com.openpojo.issues.issue55;

import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.affirm.Affirm;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class IssueTest {

  @Test
  public void ensureEnumStaysIntact() {
    PojoClass pojoClass = PojoClassFactory.getPojoClass(SomeEnumWithValuesMethod.class);
    boolean valuesMethodExists = false;
    for (PojoMethod pojoMethod : pojoClass.getPojoMethods()) {
      if (pojoMethod.getName().equals("values") && pojoMethod.getPojoParameters().size() > 0 && pojoMethod.isStatic())
        valuesMethodExists = true;
    }
    Affirm.affirmTrue("values method must exist, Enum Class Changed?!", valuesMethodExists);
  }

  @Test
  public void shouldCallEnumValuesWithNoParameters() {
    SomeEnumWithValuesMethod randomEntry = RandomFactory.getRandomValue(SomeEnumWithValuesMethod.class);
    Affirm.affirmTrue("Should have generated using proper values method", SomeEnumWithValuesMethod.VALUE1.equals(randomEntry)
        || SomeEnumWithValuesMethod.VALUE2.equals(randomEntry));
  }
}
