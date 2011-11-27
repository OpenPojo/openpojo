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
package com.openpojo.registry;

import com.openpojo.business.BusinessIdentity;
import com.openpojo.random.collection.CollectionRandomGenerator;
import com.openpojo.random.collection.list.AbstractListRandomGenerator;
import com.openpojo.random.collection.list.AbstractSequentialListRandomGenerator;
import com.openpojo.random.collection.list.ListConcreteRandomGenerator;
import com.openpojo.random.collection.list.ListRandomGenerator;
import com.openpojo.random.collection.queue.QueueConcreteRandomGenerator;
import com.openpojo.random.collection.queue.QueueRandomGenerator;
import com.openpojo.random.collection.set.NavigableSetRandomGenerator;
import com.openpojo.random.collection.set.SetConcreteRandomGenerator;
import com.openpojo.random.collection.set.SetRandomGenerator;
import com.openpojo.random.collection.set.SortedSetRandomGenerator;
import com.openpojo.random.impl.BasicRandomGenerator;
import com.openpojo.random.impl.ClassRandomGenerator;
import com.openpojo.random.impl.DefaultRandomGenerator;
import com.openpojo.random.impl.EnumSetRandomGenerator;
import com.openpojo.random.impl.ObjectRandomGenerator;
import com.openpojo.random.impl.TimestampRandomGenerator;
import com.openpojo.random.impl.VoidRandomGenerator;
import com.openpojo.random.map.AbstractMapRandomGenerator;
import com.openpojo.random.map.MapConcreteRandomGenerator;
import com.openpojo.random.map.MapRandomGenerator;
import com.openpojo.random.map.SortedMapRandomGenerator;
import com.openpojo.random.service.RandomGeneratorService;
import com.openpojo.random.service.impl.DefaultRandomGeneratorService;

/**
 * @author oshoukry
 */
public class ServiceRegistrar {
    private RandomGeneratorService randomGeneratorService;

    private ServiceRegistrar() {
        initRandomGeneratorService();
    }

    private void initRandomGeneratorService() {

        randomGeneratorService = new DefaultRandomGeneratorService();

        // Default Generator
        randomGeneratorService.setDefaultRandomGenerator(new DefaultRandomGenerator());

        // register basic types.
        randomGeneratorService.registerRandomGenerator(VoidRandomGenerator.getInstance());
        randomGeneratorService.registerRandomGenerator(ObjectRandomGenerator.getInstance());
        randomGeneratorService.registerRandomGenerator(BasicRandomGenerator.getInstance());
        randomGeneratorService.registerRandomGenerator(TimestampRandomGenerator.getInstance());
        randomGeneratorService.registerRandomGenerator(ClassRandomGenerator.getInstance());
        randomGeneratorService.registerRandomGenerator(EnumSetRandomGenerator.getInstance());

        // Collection
        randomGeneratorService.registerRandomGenerator(CollectionRandomGenerator.getInstance());

        // Lists
        randomGeneratorService.registerRandomGenerator(ListRandomGenerator.getInstance());
        randomGeneratorService.registerRandomGenerator(ListConcreteRandomGenerator.getInstance());
        randomGeneratorService.registerRandomGenerator(AbstractSequentialListRandomGenerator.getInstance());
        randomGeneratorService.registerRandomGenerator(AbstractListRandomGenerator.getInstance());

        // Sets
        randomGeneratorService.registerRandomGenerator(SetRandomGenerator.getInstance());
        randomGeneratorService.registerRandomGenerator(SetConcreteRandomGenerator.getInstance());
        randomGeneratorService.registerRandomGenerator(SortedSetRandomGenerator.getInstance());
        randomGeneratorService.registerRandomGenerator(NavigableSetRandomGenerator.getInstance());

        // Queue
        randomGeneratorService.registerRandomGenerator(QueueRandomGenerator.getInstance());
        randomGeneratorService.registerRandomGenerator(QueueConcreteRandomGenerator.getInstance());

        // Map
        randomGeneratorService.registerRandomGenerator(MapRandomGenerator.getInstance());
        randomGeneratorService.registerRandomGenerator(SortedMapRandomGenerator.getInstance());
        randomGeneratorService.registerRandomGenerator(MapConcreteRandomGenerator.getInstance());
        randomGeneratorService.registerRandomGenerator(AbstractMapRandomGenerator.getInstance());

    }

    public static ServiceRegistrar getInstance() {
        return Instance.INSTANCE;
    }

    public void setRandomGeneratorService(RandomGeneratorService randomGeneratorService) {
        this.randomGeneratorService = randomGeneratorService;
    }

    public RandomGeneratorService getRandomGeneratorService() {
        return randomGeneratorService;
    }

    @Override
    public String toString() {
        return BusinessIdentity.toString(this);
    }

    private static class Instance {
        private static final ServiceRegistrar INSTANCE = new ServiceRegistrar();
    }
}
