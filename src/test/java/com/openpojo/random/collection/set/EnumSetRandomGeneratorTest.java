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

package com.openpojo.random.collection.set;

import java.util.Collection;
import java.util.EnumSet;

import com.openpojo.random.ParameterizableRandomGenerator;
import com.openpojo.random.collection.support.EnumType;
import com.openpojo.random.collection.util.BaseCollectionRandomGeneratorTest;
import com.openpojo.random.util.SomeEnum;
import com.openpojo.reflection.java.load.ClassUtil;

/**
 * @author oshoukry
 */
public class EnumSetRandomGeneratorTest extends BaseCollectionRandomGeneratorTest {

  @Override
  protected ParameterizableRandomGenerator getInstance() {
    return EnumSetRandomGenerator.getInstance();
  }

  @Override
  protected Class<? extends ParameterizableRandomGenerator> getGeneratorClass() {
    return EnumSetRandomGenerator.class;
  }

  @Override
  protected Class<? extends Collection> getExpectedTypeClass() {
    return EnumSet.class;
  }

  @Override
  @SuppressWarnings("unchecked")
  protected Class<? extends Collection> getGeneratedTypeClass() {
    return (Class<? extends Collection>) ClassUtil.loadClass("java.util.RegularEnumSet");
  }

  @Override
  protected Class<?> getDefaultType() {
    return SomeEnum.class;
  }

  @Override
  protected Class<?> getGenericType() {
    return EnumType.class;
  }
}
