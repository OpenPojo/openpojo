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

import java.util.EnumMap;
import java.util.Map;

import com.openpojo.random.ParameterizableRandomGenerator;
import com.openpojo.random.map.support.EnumType1;
import com.openpojo.random.map.support.SimpleType2;
import com.openpojo.random.map.util.BaseMapRandomGeneratorTest;
import com.openpojo.random.util.SomeEnum;

/**
 * @author oshoukry
 */
public class EnumMapRandomGeneratorTest extends BaseMapRandomGeneratorTest {

  protected EnumMapRandomGenerator getInstance() {
    return EnumMapRandomGenerator.getInstance();
  }

  protected Class<? extends ParameterizableRandomGenerator> getGeneratorClass() {
    return EnumMapRandomGenerator.class;
  }

  protected Class<? extends Map> getExpectedTypeClass() {
    return EnumMap.class;
  }

  protected Class<? extends Map> getGeneratedTypeClass() {
    return EnumMap.class;
  }

  protected Class<?> getDefaultType1() {
    return SomeEnum.class;
  }

  @Override
  protected Class<?> getGenericType1() {
    return EnumType1.class;
  }

  @Override
  protected Class<?> getGenericType2() {
    return SimpleType2.class;
  }

}
