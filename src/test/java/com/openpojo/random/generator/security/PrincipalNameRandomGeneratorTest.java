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

package com.openpojo.random.generator.security;

import java.util.Arrays;
import java.util.Collection;

import com.openpojo.random.RandomGenerator;
import com.openpojo.random.generator.AbstractGeneratorTest;
import com.openpojo.random.service.RandomGeneratorService;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.registry.ServiceRegistrar;
import org.junit.Assert;
import org.junit.Test;
import sun.security.krb5.PrincipalName;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author oshoukry
 */
public class PrincipalNameRandomGeneratorTest extends AbstractGeneratorTest {

  protected PojoClass getPojoClass() {
    return PojoClassFactory.getPojoClass(PrincipalNameRandomGenerator.class);
  }

  protected String getTypeName() {
    return PrincipalName.class.getName();
  }

  protected RandomGenerator getRandomGenerator() {
    return PrincipalNameRandomGenerator.getInstance();
  }

  @Test
  public void shouldCatchAndreThrowException() {
    RandomGeneratorService randomGeneratorService = ServiceRegistrar.getInstance().getRandomGeneratorService();
    RandomGenerator stringRandomGenerator = randomGeneratorService.getRandomGeneratorByType(String.class);

    ExceptionThrowingRandomGenerator exceptionThrowingRandomGenerator = new ExceptionThrowingRandomGenerator();
    try {
      randomGeneratorService.registerRandomGenerator(exceptionThrowingRandomGenerator);
      getRandomGenerator().doGenerate(null);
      Assert.fail("Expected exception not thrown");
    } catch (Exception e) {
      assertThat(e.getMessage(), equalTo("Failed to generate sun.security.krb5.PrincipalName instance."));
    } finally {
      randomGeneratorService.registerRandomGenerator(stringRandomGenerator);
    }
  }

  private class ExceptionThrowingRandomGenerator implements RandomGenerator {
    public Collection<Class<?>> getTypes() {
      return Arrays.asList(new Class<?>[] { String.class });
    }

    public Object doGenerate(Class<?> type) {
      throw new RuntimeException();
    }
  }
}