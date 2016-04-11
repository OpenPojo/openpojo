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

package com.openpojo.random.map;

import java.util.Map;
import java.util.TreeMap;

import com.openpojo.random.ParameterizableRandomGenerator;
import com.openpojo.random.map.support.ComparableType1;
import com.openpojo.random.map.support.ComparableType2;
import com.openpojo.random.map.util.BaseMapRandomGeneratorTest;
import com.openpojo.reflection.java.load.ClassUtil;
import org.junit.Assume;
import org.junit.Before;

/**
 * @author oshoukry
 */
public class NavigableMapRandomGeneratorTest extends BaseMapRandomGeneratorTest {
  private static final String NAVIGABLEMAP_CLASS_NAME = "java.util.NavigableMap";

  @Before
  public void requirement() {
    Assume.assumeTrue(NAVIGABLEMAP_CLASS_NAME + " is not loaded, skipping test", ClassUtil.isClassLoaded(NAVIGABLEMAP_CLASS_NAME));
  }

  protected ParameterizableRandomGenerator getInstance() {
    return NavigableMapRandomGenerator.getInstance();
  }

  protected Class<? extends ParameterizableRandomGenerator> getGeneratorClass() {
    return NavigableMapRandomGenerator.class;
  }

  @SuppressWarnings("unchecked")
  protected Class<? extends Map> getExpectedTypeClass() {
    return (Class<? extends Map>) ClassUtil.loadClass(NAVIGABLEMAP_CLASS_NAME);
  }

  protected Class<? extends Map> getGeneratedTypeClass() {
    return TreeMap.class;
  }

  protected Class<?> getGenericType1() {
    return ComparableType1.class;
  }

  protected Class<?> getGenericType2() {
    return ComparableType2.class;
  }

  public void constructorShouldBePrivate() {
    super.constructorShouldBePrivate();
  }

}
