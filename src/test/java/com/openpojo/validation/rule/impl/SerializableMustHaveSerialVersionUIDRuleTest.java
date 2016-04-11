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

package com.openpojo.validation.rule.impl;

import com.openpojo.validation.rule.Rule;
import com.openpojo.validation.rule.impl.sampleclasses.SerializableMustHaveSerialVersionUIDDoesClass;
import com.openpojo.validation.rule.impl.sampleclasses.SerializableMustHaveSerialVersionUIDDoesntClass;
import com.openpojo.validation.rule.impl.sampleclasses.SerializableMustHaveSerialVersionUIDInvalidCaseClass;
import com.openpojo.validation.rule.impl.sampleclasses.SerializableMustHaveSerialVersionUIDNotFinalClass;
import com.openpojo.validation.rule.impl.sampleclasses.SerializableMustHaveSerialVersionUIDNotSerializableClass;
import com.openpojo.validation.rule.impl.sampleclasses.SerializableMustHaveSerialVersionUIDNotStaticClass;
import com.openpojo.validation.rule.impl.sampleclasses.SerializableMustHaveSerialVersionUIDNotlongClass;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class SerializableMustHaveSerialVersionUIDRuleTest {
  Class<?>[] failClasses = new Class<?>[] { SerializableMustHaveSerialVersionUIDDoesntClass.class,
      SerializableMustHaveSerialVersionUIDInvalidCaseClass.class, SerializableMustHaveSerialVersionUIDNotFinalClass.class,
      SerializableMustHaveSerialVersionUIDNotStaticClass.class, SerializableMustHaveSerialVersionUIDNotlongClass.class };
  Class<?>[] passClasses = new Class<?>[] { SerializableMustHaveSerialVersionUIDDoesClass.class,
      SerializableMustHaveSerialVersionUIDNotSerializableClass.class };
  Rule rule = new SerializableMustHaveSerialVersionUIDRule();

  @Test
  public void testEvaluate() {
    CommonCode.shouldPassRuleValidation(rule, passClasses);
    CommonCode.shouldFailRuleValidation(rule, failClasses);
  }

}
