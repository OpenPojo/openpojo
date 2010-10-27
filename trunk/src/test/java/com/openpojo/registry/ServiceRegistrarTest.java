package com.openpojo.registry;

import org.junit.Test;

import com.openpojo.random.RandomFactory;
import com.openpojo.registry.sampleclasses.SomeService;
import com.openpojo.validation.affirm.Affirm;

public class ServiceRegistrarTest {

    @Test
    public final void shouldRegisterAndUnRegisterService() {
        final String serviceName = (String) RandomFactory.getRandomValue(String.class);
        Affirm.affirmNull("ServiceAlready Registered?", ServiceRegistrar.getServiceByName(serviceName));
        ServiceRegistrar.registerService(serviceName, new Service() {

            public String getName() {
                return serviceName;
            }

        });
        Affirm.affirmNotNull("Service not registered?", ServiceRegistrar.getServiceByName(serviceName));
        ServiceRegistrar.unregisterService(serviceName);
        Affirm.affirmNull("Service not unregistered?", ServiceRegistrar.getServiceByName(serviceName));
    }

    @Test
    public final void shouldGetRegisteredService() {
        Registrar.initializeServiceRegistrar(this.getClass().getPackage().getName());
        Affirm.affirmNotNull("SomeService not registered?", ServiceRegistrar.getServiceByName(SomeService.class
                .getName()));
    }

}
