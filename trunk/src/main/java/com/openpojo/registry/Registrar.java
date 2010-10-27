/**
 * Copyright (C) 2010 Osman Shoukry
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.openpojo.registry;

import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.construct.InstanceFactory;
import com.openpojo.reflection.filters.FilterNestedClasses;
import com.openpojo.reflection.impl.PojoClassFactory;

/**
 * @author oshoukry
 */
public final class Registrar {
    private static final String OPEN_POJO_PACKAGE_ROOT = "com.openpojo";

    public static void init() {
        initializeServiceRegistrar(OPEN_POJO_PACKAGE_ROOT);
    }

    public synchronized static void initializeServiceRegistrar(final String packageName) {
        List<PojoClass> services = PojoClassFactory.enumerateClassesByExtendingType(packageName, Service.class,
                new FilterNestedClasses());
        for (PojoClass service : services) {
            Service serviceInstance = (Service) InstanceFactory.getInstance(service);
            ServiceRegistrar.registerService(serviceInstance.getName(), serviceInstance);
        }
    }
}
