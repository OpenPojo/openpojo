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

package com.openpojo.reflection.service.impl;

import java.util.LinkedList;
import java.util.List;

import com.openpojo.log.LoggerFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;
import com.openpojo.reflection.PojoPackage;
import com.openpojo.reflection.cache.PojoCache;
import com.openpojo.reflection.filters.FilterBasedOnInheritance;
import com.openpojo.reflection.filters.FilterChain;
import com.openpojo.reflection.impl.PojoClassImpl;
import com.openpojo.reflection.impl.PojoFieldFactory;
import com.openpojo.reflection.impl.PojoMethodFactory;
import com.openpojo.reflection.impl.PojoPackageFactory;
import com.openpojo.reflection.service.PojoClassLookupService;
import com.openpojo.registry.Service;
import com.openpojo.registry.ServiceRegistrar;

/**
 * @author oshoukry
 */
public class DefaultPojoClassLookupService implements Service, PojoClassLookupService {

  public DefaultPojoClassLookupService() {

  }

  public String getName() {
    return this.getClass().getName();
  }

  public List<PojoClass> enumerateClassesByExtendingType(final String packageName, final Class<?> type,
                                                         final PojoClassFilter pojoClassFilter) {

    final FilterBasedOnInheritance inheritanceFilter = new FilterBasedOnInheritance(type);
    final FilterChain filterChain = new FilterChain(inheritanceFilter, pojoClassFilter);
    return getPojoClassesRecursively(packageName, filterChain);
  }

  public PojoClass getPojoClass(final Class<?> clazz) {
    PojoClass pojoClass = PojoCache.getPojoClass(clazz.getName());
    if (pojoClass == null) {
      try {
        pojoClass = new PojoClassImpl(clazz, PojoFieldFactory.getPojoFields(clazz), PojoMethodFactory.getPojoMethods(clazz));
        pojoClass = ServiceRegistrar.getInstance().getPojoCoverageFilterService().adapt(pojoClass);
      } catch (LinkageError le) {
        LoggerFactory.getLogger(this.getClass()).warn("Failed to load class [{0}], exception [{1}]", clazz, le);
      }
      PojoCache.addPojoClass(clazz.getName(), pojoClass);
    }
    return pojoClass;
  }

  public List<PojoClass> getPojoClasses(final String packageName) {
    return getPojoClasses(packageName, null);
  }

  public List<PojoClass> getPojoClasses(final String packageName, final PojoClassFilter pojoClassFilter) {
    return PojoPackageFactory.getPojoPackage(packageName).getPojoClasses(getFinalFilterChain(pojoClassFilter));
  }

  public List<PojoClass> getPojoClassesRecursively(final String packageName, final PojoClassFilter pojoClassFilter) {
    final List<PojoClass> pojoClasses = new LinkedList<PojoClass>();

    final PojoPackage pojoPackage = PojoPackageFactory.getPojoPackage(packageName);
    final List<PojoPackage> packages = new LinkedList<PojoPackage>();
    packages.add(pojoPackage);

    for (int counter = 0; counter < packages.size(); counter++) {
      final PojoPackage entry = packages.get(counter);

      // add newly discovered paths
      packages.addAll(entry.getPojoSubPackages());

      // add all classes in current path
      for (final PojoClass pojoClass : entry.getPojoClasses(getFinalFilterChain(pojoClassFilter))) {
        pojoClasses.add(pojoClass);
      }
    }
    return pojoClasses;
  }

  private PojoClassFilter getFinalFilterChain(PojoClassFilter pojoClassFilter) {
    return new FilterChain(pojoClassFilter, ServiceRegistrar.getInstance().getPojoCoverageFilterService());
  }

}
