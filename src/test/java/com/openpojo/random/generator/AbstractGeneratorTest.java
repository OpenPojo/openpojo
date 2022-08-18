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

package com.openpojo.random.generator;

import java.util.Collection;
import java.util.List;

import com.openpojo.random.RandomFactory;
import com.openpojo.random.RandomGenerator;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.java.load.ClassUtil;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

/**
 * @author oshoukry
 */
public abstract class AbstractGeneratorTest {

  protected abstract PojoClass getPojoClass();

  protected abstract String getTypeName();

  protected abstract RandomGenerator getRandomGenerator();

  @Before
  public void before() {
    Assume.assumeTrue(getTypeName() != null);
  }

  @Test
  public void singlePrivateConstructor() {
    List<PojoMethod> constructors = getPojoClass().getPojoConstructors();
    Assert.assertEquals("Should have only one constructor", 1, constructors.size());
    Assert.assertTrue("Constructor should be private", constructors.get(0).isPrivate());
  }

  @Test
  public void canConstruct() {
    Assert.assertNotNull("Should be able to construct", getRandomGenerator());
  }

  private void assumeClassIsLoaded() {
    Assume.assumeTrue(ClassUtil.isClassLoaded(getTypeName()));
  }

  @Test
  public void whenGetTypesShouldReturnExpectedType() {
    assumeClassIsLoaded();

    Collection<Class<?>> types = getRandomGenerator().getTypes();
    Assert.assertEquals("Should only declare one type", 1, types.size());
    Assert.assertEquals(getTypeName(), types.iterator().next().getName());
  }

  @Test
  public void whenDoGenerateReturnsDifferentInstances() {
    assumeClassIsLoaded();

    Object first = getRandomGenerator().doGenerate(null);
    Assert.assertNotNull("First should not be null", first);

    Object second = getRandomGenerator().doGenerate(null);
    Assert.assertNotNull("Second should not be null", second);

    Class<?> expectedClass = ClassUtil.loadClass(getTypeName());
    Assert.assertTrue("Expected an instance assignable from [" + expectedClass + "] but was [" + first.getClass() + "]",
        expectedClass.isAssignableFrom(first.getClass()));
    Assert.assertTrue("Expected an instance assignable from [" + expectedClass + "] but was [" + second.getClass() + "]",
        expectedClass.isAssignableFrom(second.getClass()));

    if (first.equals(second)) // by chance same object, try one more time.
      second = getRandomGenerator().doGenerate(null);

    Assert.assertNotEquals(first, second);
  }

  @Test
  public void end2end() {
    assumeClassIsLoaded();

    Class<?> type = ClassUtil.loadClass(getTypeName());
    Object instance = RandomFactory.getRandomValue(type);
    Assert.assertNotNull("Should not generated null", instance);
    Assert.assertTrue("Should generate compatible type", type.isAssignableFrom(instance.getClass()));
  }
}
