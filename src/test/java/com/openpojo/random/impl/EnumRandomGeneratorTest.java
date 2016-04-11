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

package com.openpojo.random.impl;

import com.openpojo.random.RandomFactory;
import com.openpojo.random.RandomGenerator;
import com.openpojo.random.util.SomeEnum;
import com.openpojo.validation.affirm.Affirm;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class EnumRandomGeneratorTest {

  @Test
  public void shouldDeclareRandomTypeAsEnum() {
    Affirm.affirmEquals("New types added / removed?", 1, EnumRandomGenerator.getInstance().getTypes().size());
    Affirm.affirmContains("Declared type must be Enum.class", Enum.class, EnumRandomGenerator.getInstance().getTypes());
  }

  @Test
  @SuppressWarnings("ConstantConditions")
  public void shouldGenerateRandomEnum() {
    RandomGenerator randomGenerator = EnumRandomGenerator.getInstance();
    Enum someEnum = (Enum) randomGenerator.doGenerate(Enum.class);

    Affirm.affirmTrue("should never generate null", someEnum != null);

    Enum anotherEnum = (Enum) randomGenerator.doGenerate(Enum.class);

    try {
      Affirm.affirmFalse("Enum's should be different", someEnum.equals(anotherEnum));
    } catch (AssertionError error) {
      // on occasion they may be the same - 1% chance, try one more time.
      anotherEnum = (Enum) randomGenerator.doGenerate(Enum.class);
      Affirm.affirmFalse("Enum's should be different", someEnum.equals(anotherEnum));
    }
  }

  @Test
  @SuppressWarnings("ConstantConditions")
  public void endToEndTest() {
    Enum someEnum = RandomFactory.getRandomValue(Enum.class);
    Affirm.affirmNotNull("Should generate Enum", someEnum);
    Affirm.affirmTrue("Should use SomeEnum when generating", someEnum.getClass() == SomeEnum.class);
  }
}
