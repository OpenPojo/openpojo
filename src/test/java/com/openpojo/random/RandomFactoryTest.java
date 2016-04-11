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

package com.openpojo.random;

import java.util.Arrays;
import java.util.Collection;

import com.openpojo.random.loop.Employee;
import com.openpojo.random.loop.RandomEmployee;
import com.openpojo.random.sampleclasses.AClassWithNoRegisteredRandomGenerator;
import com.openpojo.random.sampleclasses.NoRandomGeneratorPerson;
import com.openpojo.random.sampleclasses.hierarchy.ClassExtendingClassImplementingSomeInterface;
import com.openpojo.random.sampleclasses.hierarchy.ClassImplementingSomeInterface;
import com.openpojo.random.sampleclasses.hierarchy.SomeInterface;
import com.openpojo.random.sampleclasses.hierarchy.SomeInterfaceRandomGenerator;
import com.openpojo.validation.affirm.Affirm;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class RandomFactoryTest {
  private final String randomString = RandomFactory.getRandomValue(String.class);

  /**
   * Test method for {@link com.openpojo.random.RandomFactory#addRandomGenerator(com.openpojo.random.RandomGenerator)}
   * .
   */
  @Test
  public void testAddRandomGenerator() {
    RandomFactory.addRandomGenerator(new RandomGenerator() {

      public Object doGenerate(final Class<?> type) {
        return new RegisteredDummy(randomString);
      }

      public Collection<Class<?>> getTypes() {
        return Arrays.asList(new Class<?>[] { RegisteredDummy.class });
      }

    });

    Assert.assertEquals("RandomGenerator registration failed", randomString,
        RandomFactory.getRandomValue(RegisteredDummy.class).getValue());
  }

  /**
   * Test that the RandomFactory detects and breaks cyclic dependencies.
   */
  @Test
  public void testRandomLoop() {
    RandomFactory.addRandomGenerator(new RandomEmployee());
    Assert.assertNotNull(RandomFactory.getRandomValue(Employee.class));
    Assert.assertNotNull(RandomFactory.getRandomValue(Employee.class));
  }

  @Test
  public void shouldDetectCyclicLoopForNonRegisteredRandomGenerator() {
    RandomFactory.getRandomValue(NoRandomGeneratorPerson.class);
  }

  @Test
  public void shouldGenerateAbstract() {
    com.openpojo.random.sampleclasses.AnAbstractClass anAbstractClass =
        RandomFactory.getRandomValue(com.openpojo.random.sampleclasses.AnAbstractClass.class);
    Assert.assertNotNull(anAbstractClass);
  }

  @Test
  public void generateRandomWithNoRegisteredRandomGenerator() {
    final Class<?> clazz = AClassWithNoRegisteredRandomGenerator.class;

    final Object someInstance = RandomFactory.getRandomValue(clazz);

    Affirm.affirmNotNull(String.format("Null value returned for random instance of [%s]", clazz.getName()), someInstance);

    Affirm.affirmFalse(String.format("Non randomized instance returned (i.e. same object) for [%s]", clazz.getName()),
        someInstance.equals(RandomFactory.getRandomValue(clazz)));
  }

  @Test
  public void shouldRegisterHierarchyOfTypes() {
    RandomFactory.addRandomGenerator(SomeInterfaceRandomGenerator.getInstance());
    final Class<?> someInterface = SomeInterface.class;
    final Class<?> classImplementingSomeInterface = ClassImplementingSomeInterface.class;
    final Class<?> classExtendingClassImplmentingSomeInterface = ClassExtendingClassImplementingSomeInterface.class;
    Object instance = RandomFactory.getRandomValue(someInterface);

    Affirm.affirmNotNull(String.format("RandomFactory failed to retrieve random instance for interface [%s]", someInterface),
        instance);

    Affirm.affirmEquals("RandomFactory failed to lookup proper random generator from heirarchy",
        classExtendingClassImplmentingSomeInterface, instance.getClass());

    instance = RandomFactory.getRandomValue(classImplementingSomeInterface);

    Affirm.affirmEquals("RandomFactory failed to lookup proper random generator from heirarchy",
        classExtendingClassImplmentingSomeInterface, instance.getClass());
  }


  private class RegisteredDummy {
    private final String value;

    public RegisteredDummy(final String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }
  }
}
