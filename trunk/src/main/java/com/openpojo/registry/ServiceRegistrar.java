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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.construct.InstanceFactory;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.registry.exception.ServiceException;

/**
 * @author oshoukry
 */
public class ServiceRegistrar {
    private static final Map<String, Service> SERVICES = new ConcurrentHashMap<String, Service>();

    private static final String OPEN_POJO_PACKAGE_ROOT = "com.openpojo";

    static {
        findAndRegisterServices();
    }

    private static void findAndRegisterServices() {
        List<PojoClass> services = PojoClassFactory.enumerateClassesByExtendingType(OPEN_POJO_PACKAGE_ROOT,
                Service.class, null);
        for (PojoClass service : services) {
            Service serviceInstance = (Service) InstanceFactory.getInstance(service);
            registerService(serviceInstance.getName(), serviceInstance);
        }
        throw ServiceException.getInstance("UnImplemented!!");
    }

    public static void registerService(final String name, final Service service) {
        SERVICES.put(name, service);
        throw ServiceException.getInstance("UnImplemented!!");
    }

    public static Service getServiceByName(final String name) {
        throw ServiceException.getInstance("UnImplemented!!");
    }

    public static Service getDefaultIdentityService() {
        throw ServiceException.getInstance("UnImplemented!!");
    }

    public static Service getJavaPrimitiveToClassMappingService() {
        throw ServiceException.getInstance("UnImplemented!!");
    }
}
