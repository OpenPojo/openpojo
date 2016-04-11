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

package com.openpojo.issues.issue42;

import com.openpojo.issues.issue42.sample.AClassWithArrayField;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.construct.InstanceFactory;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.affirm.Affirmation;
import com.openpojo.validation.affirm.AffirmationFactory;
import com.openpojo.validation.affirm.JUnitAssertAffirmation;
import com.openpojo.validation.test.impl.GetterTester;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class IssueTest {

  @Test
  public void testByteArrayEquality() {

    byte[] first = new byte[] { (byte) 0xaa, (byte) 0xab, (byte) 0xac };
    byte[] second = first.clone();

    Affirm.affirmEquals("A clone failed to be seen as equal", first, second);
  }

  @Test
  public void testAClassWithArrayField() {
    Affirmation jUnitAffirmation = (Affirmation) InstanceFactory.getInstance(
        PojoClassFactory.getPojoClass(JUnitAssertAffirmation.class));

    AffirmationFactory.getInstance().setActiveAffirmation(jUnitAffirmation);
    PojoClass classWithArrayField = PojoClassFactory.getPojoClass(AClassWithArrayField.class);

    Validator pojoValidator = ValidatorBuilder.create()
        .with(new GetterTester())
        .build();

    pojoValidator.validate(classWithArrayField);
  }
}
