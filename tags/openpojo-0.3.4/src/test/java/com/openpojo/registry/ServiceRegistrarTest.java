/**
 * Copyright (C) 2010 Osman Shoukry
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
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

import org.junit.Test;

import com.openpojo.random.RandomFactory;
import com.openpojo.registry.sampleclasses.SomeService;
import com.openpojo.validation.affirm.Affirm;

public class ServiceRegistrarTest {

    @Test
    public final void shouldRegisterAndUnRegisterService() {
        final String serviceName = (String) RandomFactory.getRandomValue(String.class);
        Affirm.affirmNull("ServiceAlready Registered?", ServiceRegistrar.getInstance().getServiceByName(serviceName));
        ServiceRegistrar.getInstance().registerService(serviceName, new Service() {

            public String getName() {
                return serviceName;
            }

        });
        Affirm.affirmNotNull("Service not registered?", ServiceRegistrar.getInstance().getServiceByName(serviceName));
        ServiceRegistrar.getInstance().unregisterService(serviceName);
        Affirm.affirmNull("Service not unregistered?", ServiceRegistrar.getInstance().getServiceByName(serviceName));
    }

    @Test
    public final void shouldGetRegisteredService() {
        Registrar.initializeServiceRegistrar(this.getClass().getPackage().getName());
        Affirm.affirmNotNull("SomeService not registered?", ServiceRegistrar.getInstance().getServiceByName(
                SomeService.class.getName()));
    }

}
