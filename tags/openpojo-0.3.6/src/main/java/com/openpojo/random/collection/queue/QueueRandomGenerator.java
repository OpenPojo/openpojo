/**
 * Copyright (C) 2010 Osman Shoukry
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.openpojo.random.collection.queue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import com.openpojo.random.RandomFactory;
import com.openpojo.random.RandomGenerator;

/**
 * This is random generator is responsible for generating Queue collections <br>
 * If request is for Queue or Deque the concrete returned type is ArrayDeque. <br>
 *
 * @author oshoukry
 */
public final class QueueRandomGenerator implements RandomGenerator {

    private final Random random = new Random(new Date().getTime());
    private final List<Class<?>> queueImplementations = new LinkedList<Class<?>>();

    private QueueRandomGenerator() {
        queueImplementations.addAll(QueueConcreteRandomGenerator.getInstance().getTypes());

    }

    public static RandomGenerator getInstance() {
        return Instance.INSTANCE;
    }

    private static final Class<?>[] TYPES = new Class<?>[] { Queue.class };

    public Object doGenerate(final Class<?> type) {
        return RandomFactory.getRandomValue(queueImplementations.get(random.nextInt(queueImplementations.size())));
    }

    public Collection<Class<?>> getTypes() {
        return Arrays.asList(TYPES);
    }

    private static class Instance {
        private static final RandomGenerator INSTANCE = new QueueRandomGenerator();
    }
}
