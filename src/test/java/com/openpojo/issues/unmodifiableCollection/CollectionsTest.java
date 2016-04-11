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

package com.openpojo.issues.unmodifiableCollection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.Test;

public class CollectionsTest {

  @Test
  public void testUnmodifiableCollectionsGetterReturned() {
    Validator pojoValidator = ValidatorBuilder.create()
        .with(new GetterTester())
        .with(new SetterTester())
        .build();

    PojoClass pojoClass = PojoClassFactory.getPojoClass(CollectionContainingClass.class);
    pojoValidator.validate(pojoClass);
  }

  private static class CollectionContainingClass {
    private List<String> values = new ArrayList<String>();

    @SuppressWarnings("unused")
    public void setValues(List<String> values) {
      this.values = values;
    }

    @SuppressWarnings("unused")
    public List<String> getValues() {
      return Collections.unmodifiableList(values);
    }

  }
}
