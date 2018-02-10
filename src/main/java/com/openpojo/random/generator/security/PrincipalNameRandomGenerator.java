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

package com.openpojo.random.generator.security;

import java.util.Arrays;
import java.util.Collection;

import com.openpojo.random.RandomFactory;
import com.openpojo.random.RandomGenerator;
import com.openpojo.random.exception.RandomGeneratorException;
import sun.security.krb5.PrincipalName;

/**
 * @author oshoukry
 */
public class PrincipalNameRandomGenerator implements RandomGenerator {

  private static final Class<?>[] TYPES = new Class<?>[] { PrincipalName.class };
  private static final PrincipalNameRandomGenerator INSTANCE = new PrincipalNameRandomGenerator();

  private PrincipalNameRandomGenerator() {
  }

  public static RandomGenerator getInstance() {
    return INSTANCE;
  }

  public Collection<Class<?>> getTypes() {
    return Arrays.asList(TYPES);
  }

  public Object doGenerate(Class<?> type) {
    try {
      //noinspection ConstantConditions
      return new PrincipalName(RandomFactory.getRandomValue(String[].class), RandomFactory.getRandomValue(String.class));
    } catch (Exception e) {
      throw RandomGeneratorException.getInstance("Failed to generate " + TYPES[0].getName() + " instance.", e);
    }
  }
}