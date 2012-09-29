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

package com.openpojo.registry;

import com.openpojo.business.BusinessIdentity;
import com.openpojo.random.collection.list.ListConcreteRandomGenerator;
import com.openpojo.random.collection.queue.QueueConcreteRandomGenerator;
import com.openpojo.random.collection.set.SetConcreteRandomGenerator;
import com.openpojo.random.impl.BasicRandomGenerator;
import com.openpojo.random.impl.ClassRandomGenerator;
import com.openpojo.random.impl.DefaultRandomGenerator;
import com.openpojo.random.impl.EnumSetRandomGenerator;
import com.openpojo.random.impl.ObjectRandomGenerator;
import com.openpojo.random.impl.TimestampRandomGenerator;
import com.openpojo.random.impl.VoidRandomGenerator;
import com.openpojo.random.map.MapConcreteRandomGenerator;
import com.openpojo.random.service.RandomGeneratorService;
import com.openpojo.random.service.impl.DefaultRandomGeneratorService;

/**
 * @author oshoukry
 */
public class ServiceRegistrar {
    private RandomGeneratorService randomGeneratorService;

    private ServiceRegistrar() {
        initializeRandomGeneratorService();
    }

    public void initializeRandomGeneratorService() {

        final RandomGeneratorService newRandomGeneratorService = new DefaultRandomGeneratorService();

        // Default Generator
        newRandomGeneratorService.setDefaultRandomGenerator(new DefaultRandomGenerator());

        // register basic types.
        newRandomGeneratorService.registerRandomGenerator(VoidRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(ObjectRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(ClassRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(BasicRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(TimestampRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(EnumSetRandomGenerator.getInstance());

        // Lists
        newRandomGeneratorService.registerRandomGenerator(ListConcreteRandomGenerator.getInstance());

        // Sets
        newRandomGeneratorService.registerRandomGenerator(SetConcreteRandomGenerator.getInstance());

        // Queue
        newRandomGeneratorService.registerRandomGenerator(QueueConcreteRandomGenerator.getInstance());

        // Map
        newRandomGeneratorService.registerRandomGenerator(MapConcreteRandomGenerator.getInstance());

        setRandomGeneratorService(newRandomGeneratorService);

    }

    public static ServiceRegistrar getInstance() {
        return Instance.INSTANCE;
    }

    public void setRandomGeneratorService(final RandomGeneratorService randomGeneratorService) {
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
