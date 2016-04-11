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

package com.openpojo.issues.issue22;

import com.openpojo.issues.issue22.sampleclasses.SomeBean;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import org.junit.Test;

public class GetterSetterTest {

  @Test
  public void shouldPassGetterSetterTest() {
    PojoClass pojoClass = PojoClassFactory.getPojoClass(SomeBean.class);
    Validator pojoValidator = ValidatorBuilder.create().with(new SetterMustExistRule()).with(new GetterMustExistRule()).build();
    pojoValidator.validate(pojoClass);
  }

}
