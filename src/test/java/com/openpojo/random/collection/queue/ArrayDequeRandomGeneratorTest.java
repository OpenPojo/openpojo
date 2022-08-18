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

package com.openpojo.random.collection.queue;

import java.util.Collection;

import com.openpojo.random.ParameterizableRandomGenerator;
import com.openpojo.random.collection.support.SimpleType;
import com.openpojo.random.collection.util.BaseCollectionRandomGeneratorTest;
import com.openpojo.reflection.java.load.ClassUtil;
import org.junit.Assume;
import org.junit.Before;

/**
 * @author oshoukry
 */
public class ArrayDequeRandomGeneratorTest extends BaseCollectionRandomGeneratorTest {
  private static final String EXPECTED_TYPE_CLASS_NAME = "java.util.ArrayDeque";

  @Before
  public void requirement() {
    Assume.assumeTrue(EXPECTED_TYPE_CLASS_NAME + " is not loaded, skipping test", ClassUtil.isClassLoaded(EXPECTED_TYPE_CLASS_NAME));
  }

  @Override
  protected ParameterizableRandomGenerator getInstance() {
    return ArrayDequeRandomGenerator.getInstance();
  }

  @Override
  protected Class<? extends ParameterizableRandomGenerator> getGeneratorClass() {
    return ArrayDequeRandomGenerator.class;
  }

  @Override
  @SuppressWarnings("unchecked")
  protected Class<? extends Collection> getExpectedTypeClass() {
    return (Class<? extends Collection>) ClassUtil.loadClass(EXPECTED_TYPE_CLASS_NAME);
  }

  @Override
  protected Class<? extends Collection> getGeneratedTypeClass() {
    return getExpectedTypeClass();
  }

  @Override
  protected Class<?> getGenericType() {
    return SimpleType.class;
  }
}
