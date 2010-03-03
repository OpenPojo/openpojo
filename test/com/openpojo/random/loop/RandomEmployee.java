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

package com.openpojo.random.loop;

import java.util.Arrays;
import java.util.Collection;

import com.openpojo.random.RandomFactory;
import com.openpojo.random.RandomGenerator;

/**
 * @author oshoukry
 */
public class RandomEmployee implements RandomGenerator {
    private static final Class<?>[] TYPES = {Employee.class};

    @Override
    public Object doGenerate(Class<?> type) {
        return new Employee("fullName-" + RandomFactory.getRandomValue(String.class), (Employee) RandomFactory
                .getRandomValue(Employee.class));
    }

    @Override
    public Collection<Class<?>> getTypes() {
        return Arrays.asList(TYPES);
    }

}
