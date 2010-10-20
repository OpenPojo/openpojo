/**
 * Copyright (C) 2010 Osman Shoukry
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.openpojo.random.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.openpojo.log.Logger;
import com.openpojo.log.LoggerFactory;
import com.openpojo.random.RandomFactory;
import com.openpojo.random.RandomGenerator;
import com.openpojo.random.exception.RandomGeneratorException;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;

/**
 * This random generator generates for EnumSet types.
 *
 * @author oshoukry
 */
public final class EnumSetRandomGenerator implements RandomGenerator {
    private static final Random RANDOM = new Random(new Date().getTime());
    private static final Class<?>[] TYPES = new Class<?>[]{ EnumSet.class };

    private static final Logger log = LoggerFactory.getLogger(EnumSetRandomGenerator.class);

    public static EnumSetRandomGenerator getInstance() {
        return Instance.INSTANCE;
    }

    public Object doGenerate(final Class<?> type) {
        log.info("Generating random value for [{0}]", type);
        PojoClass pojoClass = PojoClassFactory.getPojoClass(type);
        if (pojoClass.getClazz().getName() != EnumSet.class.getName()) {
            throw RandomGeneratorException.getInstance(String.format("[%s] is not an EnumSet!!", pojoClass));
        }

        List<RandomEnum> randomEnumValues = new LinkedList<RandomEnum>();
        for (int i = 0; i <= RANDOM.nextInt(RandomEnum.values().length); i++) {
            randomEnumValues.add((RandomEnum) RandomFactory.getRandomValue(RandomEnum.class));
        }
        return EnumSet.copyOf(randomEnumValues);
    }

    public Collection<Class<?>> getTypes() {
        return Arrays.asList(TYPES);
    }

    private static class Instance {
        private static final EnumSetRandomGenerator INSTANCE = new EnumSetRandomGenerator();
    }

    public enum RandomEnum {
        ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, ELEVEN, TWELVE, THIRTEEN, FOURTEEN, FIFTEEN, SIXTEEN, SEVENTEEN, EIGHTEEN, NINETEEN, TWENTY;
    }

}
