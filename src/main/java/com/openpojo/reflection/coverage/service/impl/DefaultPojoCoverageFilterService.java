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

package com.openpojo.reflection.coverage.service.impl;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.coverage.CoverageDetector;
import com.openpojo.reflection.coverage.service.PojoCoverageFilterService;

/**
 * @author oshoukry
 */
public class DefaultPojoCoverageFilterService implements PojoCoverageFilterService {

  private PojoClassCoverageFilter pojoClassCoverageFilter = new PojoClassCoverageFilter();
  private PojoClassCoverageAdapter pojoClassCoverageAdapter = new PojoClassCoverageAdapter();

  public synchronized void registerCoverageDetector(CoverageDetector coverageDetector) {
    pojoClassCoverageFilter.add(coverageDetector.getPojoClassFilter());
    pojoClassCoverageAdapter.add(coverageDetector.getPojoClassAdapter());
  }

  public PojoClass adapt(PojoClass pojoClass) {
    return pojoClassCoverageAdapter.adapt(pojoClass);
  }

  public boolean include(PojoClass pojoClass) {
    return pojoClassCoverageFilter.include(pojoClass);
  }

}
