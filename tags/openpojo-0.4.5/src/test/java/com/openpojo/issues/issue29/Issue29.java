/*
 * Copyright (c) 2010-2013 Osman Shoukry
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License or any
 *    later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.openpojo.issues.issue29;

import com.openpojo.log.utils.MessageFormatter;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.adapt.PojoClassAdapter;
import com.openpojo.reflection.adapt.impl.JacocoPojoClassAdapter;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.registry.ServiceRegistrar;
import com.openpojo.validation.affirm.Affirm;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class Issue29 {
    private static final String JACOCO_FIELD_NAME = "$jacocoData";
    private static final String JACOCO_METHOD_NAME = "$jacocoInit";

    @SuppressWarnings("unused")
    public static transient boolean[] $jacocoData;

    @SuppressWarnings("unused")
    public static Class<?> $jacocoInit() {
        return null;
    }


    @Test
    public void shouldHideJacocoFieldAndMethod() {
        PojoClassAdapter jacocoPojoClassAdapter = JacocoPojoClassAdapter.getInstance();
        ServiceRegistrar.getInstance().getPojoClassAdaptationService().unRegisterPojoClassAdapter(jacocoPojoClassAdapter);
        PojoClass jacocoPojoClass = PojoClassFactory.getPojoClass(this.getClass());
        ServiceRegistrar.getInstance().initializePojoClassAdaptationService();
        PojoClass cleansedPojoClass = jacocoPojoClassAdapter.adapt(jacocoPojoClass);
        Affirm.affirmEquals("Adapter removed too much", jacocoPojoClass.getPojoFields().size() - 1,
                cleansedPojoClass.getPojoFields().size());
        for (PojoField pojoField : cleansedPojoClass.getPojoFields()) {
            if (pojoField.getName().equals(JACOCO_FIELD_NAME)) {
                Affirm.fail(JACOCO_FIELD_NAME + " field is still visible!!");
            }
        }
        Affirm.affirmEquals("Adapter removed too many Methods", jacocoPojoClass.getPojoMethods().size() - 1,
                cleansedPojoClass.getPojoMethods().size());

        for (PojoMethod pojoMethod : cleansedPojoClass.getPojoMethods()) {
            if (pojoMethod.getName().equals(JACOCO_METHOD_NAME)) {
                Affirm.fail(JACOCO_METHOD_NAME + " method is still visible!!");
            }
        }
    }

    @Test
    public void shouldRegisterJacocoPojoClassAdapterByDefault() {
        for (PojoClassAdapter pojoClassAdapter : ServiceRegistrar.getInstance().getPojoClassAdaptationService()
                .getRegisteredPojoAdapterClasses()) {
            if (pojoClassAdapter.equals(JacocoPojoClassAdapter.getInstance())) {
                return;
            }
        }
        Affirm.fail(MessageFormatter.format("[{0}] was not registered by default", JacocoPojoClassAdapter.getInstance()));
    }
}
