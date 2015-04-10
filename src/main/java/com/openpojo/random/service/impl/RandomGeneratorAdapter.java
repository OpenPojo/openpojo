/*
 * Copyright (c) 2010-2015 Osman Shoukry
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License or any
 *    later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
 *
 */
public final class RandomGeneratorAdapter implements RandomGenerator, ParameterizableRandomGenerator {

    private final Class<?> fromType;
    private final Class<?> toType;
    private final RandomGenerator adaptedRandomGenerator;
    private final static Logger LOGGER = LoggerFactory.getLogger(RandomGeneratorAdapter.class);

    public RandomGeneratorAdapter(final Class<?> fromType, final Class<?> toType,
            final RandomGenerator adaptedRandomGenerator) {
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
        return ((ParameterizableRandomGenerator)adaptedRandomGenerator).doGenerate(parameterizedType);
    }
}
