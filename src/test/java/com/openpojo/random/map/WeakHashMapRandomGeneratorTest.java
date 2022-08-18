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

package com.openpojo.random.map;

import java.util.Map;
import java.util.WeakHashMap;

import com.openpojo.random.ParameterizableRandomGenerator;
import com.openpojo.random.map.support.SimpleType1;
import com.openpojo.random.map.support.SimpleType2;
import com.openpojo.random.map.util.BaseMapRandomGeneratorTest;

/**
 * @author oshoukry
 */
public class WeakHashMapRandomGeneratorTest extends BaseMapRandomGeneratorTest {

  protected ParameterizableRandomGenerator getInstance() {
    return WeakHashMapRandomGenerator.getInstance();
  }

  protected Class<? extends ParameterizableRandomGenerator> getGeneratorClass() {
    return WeakHashMapRandomGenerator.class;
  }

  protected Class<? extends Map> getExpectedTypeClass() {
    return WeakHashMap.class;
  }

  protected Class<? extends Map> getGeneratedTypeClass() {
    return WeakHashMap.class;
  }

  protected Class<?> getGenericType1() {
    return SimpleType1.class;
  }

  protected Class<?> getGenericType2() {
    return SimpleType2.class;
  }
}
