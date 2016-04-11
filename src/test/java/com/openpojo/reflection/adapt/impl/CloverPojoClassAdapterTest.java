/*
 * Copyright (c) 2010-2016 Osman Shoukry
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

package com.openpojo.reflection.adapt.impl;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.adapt.impl.sampleclasses.CloverInstrumentedClass;
import com.openpojo.reflection.coverage.impl.Clover3;
import com.openpojo.reflection.coverage.impl.Clover4;
import com.openpojo.reflection.coverage.impl.Cobertura;
import com.openpojo.reflection.coverage.impl.Jacoco;
import com.openpojo.reflection.coverage.service.PojoCoverageFilterService;
import com.openpojo.reflection.coverage.service.PojoCoverageFilterServiceFactory;
import com.openpojo.reflection.coverage.service.impl.DefaultPojoCoverageFilterService;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.registry.ServiceRegistrar;
import com.openpojo.validation.affirm.Affirm;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class CloverPojoClassAdapterTest {


  private PojoClass cloverInstrumentedPojoClass;
  private PojoClass cloverCleanedPojoClass;
  private static PojoCoverageFilterService originalPojoCoverageFilterService;
  private static PojoCoverageFilterService cloverPojoCoverageFilterService = PojoCoverageFilterServiceFactory
      .createPojoCoverageFilterServiceWith(Clover3.getInstance());

  @BeforeClass
  public static void initialSetup() {
    originalPojoCoverageFilterService = ServiceRegistrar.getInstance().getPojoCoverageFilterService();
  }

  @Before
  public void setup() {
    PojoCoverageFilterService allButClover3PojoCoverageFilterService = new DefaultPojoCoverageFilterService();

    allButClover3PojoCoverageFilterService.registerCoverageDetector(Cobertura.getInstance());
    allButClover3PojoCoverageFilterService.registerCoverageDetector(Jacoco.getInstance());

    ServiceRegistrar.getInstance().setPojoCoverageFilterService(allButClover3PojoCoverageFilterService);
    cloverInstrumentedPojoClass = PojoClassFactory.getPojoClass(CloverInstrumentedClass.class);
    ServiceRegistrar.getInstance().setPojoCoverageFilterService(cloverPojoCoverageFilterService);
    cloverCleanedPojoClass = CloverPojoClassAdapter.getInstance().adapt(cloverInstrumentedPojoClass);
  }

  @AfterClass
  public static void reInstateInitialSetup() {
    ServiceRegistrar.getInstance().setPojoCoverageFilterService(originalPojoCoverageFilterService);
  }

  @Test
  public void ensureCloverInstrumentedClassNotChanged() {
    int expectedFieldCount = 4;
    if (Clover4.getInstance().isLoaded())
      expectedFieldCount++;
    Affirm.affirmEquals("Fields added/removed?", expectedFieldCount, cloverInstrumentedPojoClass.getPojoFields().size());
    Affirm.affirmEquals("Methods added/removed?", 3, cloverInstrumentedPojoClass.getPojoMethods().size());
  }

  @Test
  public void shouldSkipFieldsStartingWith__CLR() {
    Affirm.affirmEquals("Cobertura fields not filtered?", 2, cloverCleanedPojoClass.getPojoFields().size());
  }

  @Test
  public void shouldNotSkipAnyMethods() {
    Affirm.affirmEquals("Cobertura methods not filtered?", 3, cloverCleanedPojoClass.getPojoMethods().size());
  }

}
