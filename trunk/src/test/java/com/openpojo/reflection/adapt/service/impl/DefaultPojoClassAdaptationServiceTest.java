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

package com.openpojo.reflection.adapt.service.impl;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.adapt.PojoClassAdaptor;
import com.openpojo.reflection.adapt.service.PojoClassAdaptationService;
import com.openpojo.validation.affirm.Affirm;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class DefaultPojoClassAdaptationServiceTest {
    @Test
    public void shouldRegisterAndUnRegisterAdaptor() throws Exception {
        PojoClassAdaptationService pojoClassAdaptationService = new DefaultPojoClassAdaptationService();
        PojoClassAdaptorMock pojoClassAdaptorMock = new PojoClassAdaptorMock();
        pojoClassAdaptationService.registerPojoClassAdaptor(pojoClassAdaptorMock);
        pojoClassAdaptationService.adapt(null);
        Affirm.affirmTrue("DefaultAdapationService didn't call adaptor", pojoClassAdaptorMock.isCalled());

        pojoClassAdaptorMock.called = false;
        pojoClassAdaptationService.unRegisterPojoClassAdaptor(pojoClassAdaptorMock);
        pojoClassAdaptationService.adapt(null);
        Affirm.affirmEquals("DefaultAdapationService failed to unRegister adaptor", false,
                pojoClassAdaptorMock.isCalled());

    }

    private class PojoClassAdaptorMock implements PojoClassAdaptor {
        private boolean called = false;

        @Override
        public PojoClass adapt(PojoClass pojoClass) {
            called = true;
            return pojoClass;
        }

        public boolean isCalled() {
            return called;
        }
    }
}
