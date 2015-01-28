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

package com.openpojo.random.dynamic;

import java.lang.reflect.Array;
import java.util.Date;
import java.util.Random;

import com.openpojo.random.RandomFactory;

/**
 * @author oshoukry
 *
 */
public class ArrayRandomGenerator {
    private static final Random RANDOM = new Random(new Date().getTime());
    private static final int MAX_RANDOM_ELEMENTS = 20;

    private ArrayRandomGenerator() {
    }

    public static ArrayRandomGenerator getInstance() {
        return Instance.INSTANCE;
    }

    public Object doGenerate(final Class<?> type) {
        final int count = RANDOM.nextInt(MAX_RANDOM_ELEMENTS + 1);
        final Object arrayReturn = Array.newInstance(type.getComponentType(), count);
        for (int i = 0; i < count; i++) {
            Array.set(arrayReturn, i, RandomFactory.getRandomValue(type.getComponentType()));
        }

        return arrayReturn;
    }

    private static class Instance {
        private static final ArrayRandomGenerator INSTANCE = new ArrayRandomGenerator();
    }

}
