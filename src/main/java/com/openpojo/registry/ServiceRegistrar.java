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

package com.openpojo.registry;

import com.openpojo.business.BusinessIdentity;
import com.openpojo.random.collection.AbstractCollectionRandomGenerator;
import com.openpojo.random.collection.CollectionRandomGenerator;
import com.openpojo.random.collection.list.AbstractListRandomGenerator;
import com.openpojo.random.collection.list.AbstractSequentialListRandomGenerator;
import com.openpojo.random.collection.list.ArrayListRandomGenerator;
import com.openpojo.random.collection.list.LinkedListRandomGenerator;
import com.openpojo.random.collection.list.ListRandomGenerator;
import com.openpojo.random.collection.list.StackRandomGenerator;
import com.openpojo.random.collection.list.VectorRandomGenerator;
import com.openpojo.random.collection.queue.*;
import com.openpojo.random.collection.set.AbstractSetRandomGenerator;
import com.openpojo.random.collection.set.ConcurrentSkipListSetRandomGenerator;
import com.openpojo.random.collection.set.CopyOnWriteArraySetRandomGenerator;
import com.openpojo.random.collection.set.NavigableSetRandomGenerator;
import com.openpojo.random.collection.set.SetConcreteRandomGenerator;
import com.openpojo.random.collection.set.SetRandomGenerator;
import com.openpojo.random.collection.set.SortedSetRandomGenerator;
import com.openpojo.random.impl.*;
import com.openpojo.random.map.*;
import com.openpojo.random.service.RandomGeneratorService;
import com.openpojo.random.service.impl.DefaultRandomGeneratorService;
import com.openpojo.reflection.coverage.service.PojoCoverageFilterService;
import com.openpojo.reflection.coverage.service.PojoCoverageFilterServiceFactory;
import com.openpojo.reflection.service.PojoClassLookupService;
import com.openpojo.reflection.service.impl.DefaultPojoClassLookupService;

/**
 * @author oshoukry
 */
public class ServiceRegistrar {
    private PojoCoverageFilterService pojoCoverageFilterService;
    private RandomGeneratorService randomGeneratorService;
    private PojoClassLookupService pojoClassLookupService;

    private ServiceRegistrar() {
        initializePojoCoverageFilterService();
        initializePojoClassLookupService();
        initializeRandomGeneratorService();
    }

    private void initializePojoCoverageFilterService() {
        setPojoCoverageFilterService(PojoCoverageFilterServiceFactory.configureAndGetPojoCoverageFilterService());
    }

    public void initializeRandomGeneratorService() {

        final RandomGeneratorService newRandomGeneratorService = new DefaultRandomGeneratorService();

        // TODO: This code needs to move out of the registrar.
        // Default Generator
        newRandomGeneratorService.setDefaultRandomGenerator(new DefaultRandomGenerator());

        // register basic types.
        newRandomGeneratorService.registerRandomGenerator(VoidRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(ObjectRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(ClassRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(BasicRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(TimestampRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(EnumRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(EnumSetRandomGenerator.getInstance());

        // Collection
        newRandomGeneratorService.registerRandomGenerator(AbstractCollectionRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(CollectionRandomGenerator.getInstance());

        // Lists
        newRandomGeneratorService.registerRandomGenerator(AbstractListRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(AbstractSequentialListRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(ArrayListRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(LinkedListRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(ListRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(StackRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(VectorRandomGenerator.getInstance());

        // Sets
        newRandomGeneratorService.registerRandomGenerator(AbstractSetRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(ConcurrentSkipListSetRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(CopyOnWriteArraySetRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(SetRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(SortedSetRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(NavigableSetRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(SetConcreteRandomGenerator.getInstance());

        // Queue
        newRandomGeneratorService.registerRandomGenerator(AbstractQueueRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(ArrayBlockingQueueRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(ArrayDequeRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(BlockingDequeRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(BlockingQueueRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(ConcurrentLinkedDequeRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(ConcurrentLinkedQueueRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(DelayQueueRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(DequeRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(LinkedBlockingDequeRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(LinkedBlockingQueueRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(LinkedTransferQueueRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(PriorityBlockingQueueRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(PriorityQueueRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(QueueRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(SynchronousQueueRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(TransferQueueRandomGenerator.getInstance());

        // Map
        newRandomGeneratorService.registerRandomGenerator(AbstractMapRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(ConcurrentHashMapRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(ConcurrentMapRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(EnumMapRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(HashMapRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(HashtableRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(IdentityHashMapRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(LinkedHashMapRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(MapRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(NavigableMapRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(SortedMapRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(TreeMapRandomGenerator.getInstance());
        newRandomGeneratorService.registerRandomGenerator(WeakHashMapRandomGenerator.getInstance());
        setRandomGeneratorService(newRandomGeneratorService);
    }

    public void initializePojoClassLookupService() {
        pojoClassLookupService = new DefaultPojoClassLookupService();
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

    public PojoClassLookupService getPojoClassLookupService() {
        return pojoClassLookupService;
    }

    public PojoCoverageFilterService getPojoCoverageFilterService() {
        return pojoCoverageFilterService;
    }

    public void setPojoCoverageFilterService(PojoCoverageFilterService pojoCoverageFilterService) {
        this.pojoCoverageFilterService = pojoCoverageFilterService;
    }

    @Override
    public String toString() {
        return BusinessIdentity.toString(this);
    }

    private static class Instance {
        private static final ServiceRegistrar INSTANCE = new ServiceRegistrar();
    }

}
