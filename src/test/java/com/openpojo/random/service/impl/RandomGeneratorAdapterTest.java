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

package com.openpojo.random.service.impl;

import com.openpojo.random.exception.RandomGeneratorException;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class RandomGeneratorAdapterTest {

  @Test(expected = RandomGeneratorException.class)
  public void getTypesShouldThrowException() {
    final RandomGeneratorAdapter randomGeneratorAdapter = new RandomGeneratorAdapter(null, null, null);
    randomGeneratorAdapter.getTypes();
  }


  @Test(expected = RandomGeneratorException.class)
  public void doGenerateOnNonRegisteredTypeShouldThrowException() {
    final RandomGeneratorAdapter randomGeneratorAdapter = new RandomGeneratorAdapter(null, null, null);
    randomGeneratorAdapter.doGenerate(anyClass());

  }

  private Class<?> anyClass() {
    return this.getClass();
  }
}
