/*
 * Copyright (c) 2010-2012 Osman Shoukry
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation, either version 3 of the License or any
 *   later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.openpojo.random.impl;

import java.util.Arrays;
import java.util.Collection;

import com.openpojo.random.RandomGenerator;

/**
 * This Random generator is for facilitative purpose only so no extra logic on any app needs to be set to handle void
 *
 * @author oshoukry
 */
public class VoidRandomGenerator implements RandomGenerator {
    private static final Class<?>[] TYPES = new Class<?>[]{ void.class };

    private VoidRandomGenerator() {

    }

    public static RandomGenerator getInstance() {
        return Instance.INSTANCE;
    }

    public Object doGenerate(final Class<?> type) {
        return null;
    }

    public Collection<Class<?>> getTypes() {
        return Arrays.asList(TYPES);
    }

    private static class Instance {
        private static final RandomGenerator INSTANCE = new VoidRandomGenerator();
    }
}
