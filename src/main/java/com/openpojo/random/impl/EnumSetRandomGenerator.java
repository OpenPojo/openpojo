/*
 * Copyright (c) 2010-2014 Osman Shoukry
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

package com.openpojo.random.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.openpojo.random.RandomFactory;
import com.openpojo.random.RandomGenerator;

/**
 * This random generator generates for EnumSet types.
 *
 * @author oshoukry
 */
public final class EnumSetRandomGenerator implements RandomGenerator {
    private static final Random RANDOM = new Random(new Date().getTime());
    private static final Class<?>[] TYPES = new Class<?>[] { EnumSet.class };

    public static EnumSetRandomGenerator getInstance() {
        return Instance.INSTANCE;
    }

    public Object doGenerate(final Class<?> type) {
        final List<SomeEnum> randomEnumValues = new LinkedList<SomeEnum>();
        for (int i = 0; i <= RANDOM.nextInt(SomeEnum.values().length); i++) {
            randomEnumValues.add(RandomFactory.getRandomValue(SomeEnum.class));
        }
        return EnumSet.copyOf(randomEnumValues);
    }

    public Collection<Class<?>> getTypes() {
        return Arrays.asList(TYPES);
    }

    private static class Instance {
        private static final EnumSetRandomGenerator INSTANCE = new EnumSetRandomGenerator();
    }

}
