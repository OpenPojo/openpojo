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

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;

import com.openpojo.random.RandomFactory;
import com.openpojo.random.RandomGenerator;

/**
 * This RandomGenerator is responsible for generating random values for java.sql.Timestamp class.
 * 
 * @author oshoukry
 */
public final class TimestampRandomGenerator implements RandomGenerator {
    private static final Class<?>[] TYPES = new Class<?>[]{ Timestamp.class };

    public Object doGenerate(Class<?> type) {
        return new Timestamp((Long) RandomFactory.getRandomValue(Long.class));
    }

    public Collection<Class<?>> getTypes() {
        return Arrays.asList(TYPES);
    }
}
