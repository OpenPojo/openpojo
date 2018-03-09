/*
 * Copyright (c) 2010-2018 Osman Shoukry
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.openpojo.registry;

import com.openpojo.business.BusinessIdentity;
import com.openpojo.random.awt.BufferedImageRandomGenerator;
import com.openpojo.random.collection.AbstractCollectionRandomGenerator;
import com.openpojo.random.collection.CollectionRandomGenerator;
import com.openpojo.random.collection.list.*;
import com.openpojo.random.collection.queue.*;
import com.openpojo.random.collection.set.*;
import com.openpojo.random.generator.security.CredentialsRandomGenerator;
import com.openpojo.random.generator.security.EncryptionKeyRandomGenerator;
import com.openpojo.random.generator.security.KerberosTimeRandomGenerator;
import com.openpojo.random.generator.security.PrincipalNameRandomGenerator;
import com.openpojo.random.generator.time.InstantRandomGenerator;
import com.openpojo.random.generator.time.TimeZoneRandomGenerator;
import com.openpojo.random.generator.time.XMLGregorianCalendarRandomGenerator;
import com.openpojo.random.generator.time.ZoneIdRandomGenerator;
import com.openpojo.random.generator.time.ZonedDateTimeRandomGenerator;
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
    newRandomGeneratorService.registerRandomGenerator(UUIDRandomGenerator.getInstance());
    newRandomGeneratorService.registerRandomGenerator(URLRandomGenerator.getInstance());
    newRandomGeneratorService.registerRandomGenerator(URIRandomGenerator.getInstance());

    // AWT
    newRandomGeneratorService.registerRandomGenerator(BufferedImageRandomGenerator.getInstance());

    // Time
    newRandomGeneratorService.registerRandomGenerator(ZonedDateTimeRandomGenerator.getInstance());
    newRandomGeneratorService.registerRandomGenerator(ZoneIdRandomGenerator.getInstance());
    newRandomGeneratorService.registerRandomGenerator(InstantRandomGenerator.getInstance());
    newRandomGeneratorService.registerRandomGenerator(TimeZoneRandomGenerator.getInstance());
    newRandomGeneratorService.registerRandomGenerator(XMLGregorianCalendarRandomGenerator.getInstance());

    // Security
    newRandomGeneratorService.registerRandomGenerator(CredentialsRandomGenerator.getInstance());
    newRandomGeneratorService.registerRandomGenerator(PrincipalNameRandomGenerator.getInstance());
    newRandomGeneratorService.registerRandomGenerator(EncryptionKeyRandomGenerator.getInstance());
    newRandomGeneratorService.registerRandomGenerator(KerberosTimeRandomGenerator.getInstance());

    // Collection
    newRandomGeneratorService.registerRandomGenerator(AbstractCollectionRandomGenerator.getInstance());
    newRandomGeneratorService.registerRandomGenerator(CollectionRandomGenerator.getInstance());

    // Lists
    newRandomGeneratorService.registerRandomGenerator(AbstractListRandomGenerator.getInstance());
    newRandomGeneratorService.registerRandomGenerator(AbstractSequentialListRandomGenerator.getInstance());
    newRandomGeneratorService.registerRandomGenerator(AttributeListRandomGenerator.getInstance());
    newRandomGeneratorService.registerRandomGenerator(ArrayListRandomGenerator.getInstance());
    newRandomGeneratorService.registerRandomGenerator(CopyOnWriteArrayListRandomGenerator.getInstance());
    newRandomGeneratorService.registerRandomGenerator(LinkedListRandomGenerator.getInstance());
    newRandomGeneratorService.registerRandomGenerator(ListRandomGenerator.getInstance());
    newRandomGeneratorService.registerRandomGenerator(RoleListRandomGenerator.getInstance());
    newRandomGeneratorService.registerRandomGenerator(RoleUnresolvedListRandomGenerator.getInstance());
    newRandomGeneratorService.registerRandomGenerator(StackRandomGenerator.getInstance());
    newRandomGeneratorService.registerRandomGenerator(VectorRandomGenerator.getInstance());

    // Sets
    newRandomGeneratorService.registerRandomGenerator(AbstractSetRandomGenerator.getInstance());
    newRandomGeneratorService.registerRandomGenerator(ConcurrentSkipListSetRandomGenerator.getInstance());
    newRandomGeneratorService.registerRandomGenerator(CopyOnWriteArraySetRandomGenerator.getInstance());
    newRandomGeneratorService.registerRandomGenerator(EnumSetRandomGenerator.getInstance());
    newRandomGeneratorService.registerRandomGenerator(HashSetRandomGenerator.getInstance());
    newRandomGeneratorService.registerRandomGenerator(JobStateReasonsRandomGenerator.getInstance());
    newRandomGeneratorService.registerRandomGenerator(LinkedHashSetRandomGenerator.getInstance());
    newRandomGeneratorService.registerRandomGenerator(NavigableSetRandomGenerator.getInstance());
    newRandomGeneratorService.registerRandomGenerator(SetRandomGenerator.getInstance());
    newRandomGeneratorService.registerRandomGenerator(SortedSetRandomGenerator.getInstance());
    newRandomGeneratorService.registerRandomGenerator(TreeSetRandomGenerator.getInstance());

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
