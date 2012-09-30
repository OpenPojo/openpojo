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

package com.openpojo.reflection.service.impl;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;
import com.openpojo.reflection.PojoPackage;
import com.openpojo.reflection.cache.PojoCache;
import com.openpojo.reflection.filters.FilterBasedOnInheritence;
import com.openpojo.reflection.filters.FilterChain;
import com.openpojo.reflection.impl.PojoClassImpl;
import com.openpojo.reflection.impl.PojoFieldFactory;
import com.openpojo.reflection.impl.PojoMethodFactory;
import com.openpojo.reflection.impl.PojoPackageFactory;
import com.openpojo.reflection.service.PojoClassLookupService;
import com.openpojo.registry.Service;
import com.openpojo.registry.ServiceRegistrar;

import java.util.LinkedList;
import java.util.List;

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

        final FilterBasedOnInheritence inheritencefilter = new FilterBasedOnInheritence(type);
        final FilterChain filterChain = new FilterChain(inheritencefilter, pojoClassFilter);
        return getPojoClassesRecursively(packageName, filterChain);
    }

    public PojoClass getPojoClass(final Class<?> clazz) {
        PojoClass pojoClass = PojoCache.getPojoClass(clazz.getName());
        if (pojoClass == null) {
            pojoClass = new PojoClassImpl(clazz, PojoFieldFactory.getPojoFields(clazz),
                    PojoMethodFactory.getPojoMethods(clazz));
            PojoCache.addPojoClass(clazz.getName(), pojoClass);
        }
        return ServiceRegistrar.getInstance().getPojoClassAdaptationService().adapt
                (pojoClass);
    }

    public List<PojoClass> getPojoClasses(final String packageName) {
        return getPojoClasses(packageName, null);
    }

    public List<PojoClass> getPojoClasses(final String packageName, final PojoClassFilter pojoClassFilter) {
        return PojoPackageFactory.getPojoPackage(packageName).getPojoClasses(pojoClassFilter);
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
            for (final PojoClass pojoClass : entry.getPojoClasses(pojoClassFilter)) {
                pojoClasses.add(pojoClass);
            }
        }
        return pojoClasses;
    }

}
