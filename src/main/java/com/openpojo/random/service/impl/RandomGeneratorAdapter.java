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

package com.openpojo.random.service.impl;

import java.util.Collection;

import com.openpojo.log.Logger;
import com.openpojo.log.LoggerFactory;
import com.openpojo.log.utils.MessageFormatter;
import com.openpojo.random.ParameterizableRandomGenerator;
import com.openpojo.random.RandomGenerator;
import com.openpojo.random.exception.RandomGeneratorException;
import com.openpojo.reflection.Parameterizable;

/**
 * @author oshoukry
 */
public final class RandomGeneratorAdapter implements RandomGenerator, ParameterizableRandomGenerator {

  private final Class<?> fromType;
  private final Class<?> toType;
  private final RandomGenerator adaptedRandomGenerator;
  private final static Logger LOGGER = LoggerFactory.getLogger(RandomGeneratorAdapter.class);

  public RandomGeneratorAdapter(final Class<?> fromType, final Class<?> toType, final RandomGenerator adaptedRandomGenerator) {
    this.fromType = fromType;
    this.toType = toType;
    this.adaptedRandomGenerator = adaptedRandomGenerator;
    LOGGER.debug("Mapping [{0}] to [{1}] for generator [{2}]", fromType, toType, adaptedRandomGenerator);
  }

  public Collection<Class<?>> getTypes() {
    throw RandomGeneratorException.getInstance(MessageFormatter.format("Illegal use of RandomGeneratorAdapter([{0}] to [{1}]",
        fromType, toType));
  }

  public Object doGenerate(final Class<?> type) {
    if (type == fromType) {
      return adaptedRandomGenerator.doGenerate(toType);
    }
    throw RandomGeneratorException.getInstance(MessageFormatter.format("Unsupported type requested [{0}]", type));
  }

  public Object doGenerate(Parameterizable parameterizedType) {
    return ((ParameterizableRandomGenerator) adaptedRandomGenerator).doGenerate(parameterizedType);
  }
}
