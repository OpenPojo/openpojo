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

package com.openpojo.reflection.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

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

import static com.openpojo.reflection.java.bytecode.asm.SubClassDefinition.GENERATED_CLASS_POSTFIX;

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
        if (clazz.getName().endsWith(GENERATED_CLASS_POSTFIX))
          throw le;
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
    final PojoClassFilter finalFilterChain = getFinalFilterChain(pojoClassFilter);

    final PojoPackage pojoPackage = PojoPackageFactory.getPojoPackage(packageName);

    Queue<PojoPackage> pending = new ConcurrentLinkedQueue<PojoPackage>();
    pending.add(pojoPackage);

    while (!pending.isEmpty()) {
      final PojoPackage entry = pending.remove();
      pending.addAll(entry.getPojoSubPackages());
      pojoClasses.addAll(entry.getPojoClasses(finalFilterChain));
    }
    return pojoClasses;
  }

  private PojoClassFilter getFinalFilterChain(PojoClassFilter pojoClassFilter) {
    return new FilterChain(pojoClassFilter, ServiceRegistrar.getInstance().getPojoCoverageFilterService());
  }
}
