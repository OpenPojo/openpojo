/*
 * Copyright (c) 2010-2014 Osman Shoukry
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

package com.openpojo.reflection.adapt.service.impl;

import com.openpojo.log.utils.MessageFormatter;
import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.adapt.PojoClassAdapter;
import com.openpojo.reflection.adapt.service.PojoClassAdaptationService;
import com.openpojo.validation.affirm.Affirm;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class DefaultPojoClassAdaptationServiceTest {

    @Test
    public void shouldRegisterAndUnRegisterAdapter() throws Exception {
        PojoClassAdaptationService pojoClassAdaptationService = new DefaultPojoClassAdaptationService();
        PojoClassAdapterMock pojoClassAdapterMock = new PojoClassAdapterMock();
        pojoClassAdaptationService.registerPojoClassAdapter(pojoClassAdapterMock);
        pojoClassAdaptationService.adapt(anyPojoClass());
        Affirm.affirmTrue("DefaultAdapationService didn't call adapter", pojoClassAdapterMock.isCalled());

        pojoClassAdapterMock.called = false;
        pojoClassAdaptationService.unRegisterPojoClassAdapter(pojoClassAdapterMock);
        pojoClassAdaptationService.adapt(anyPojoClass());
        Affirm.affirmEquals("DefaultAdapationService failed to unRegister adapter", false,
                pojoClassAdapterMock.isCalled());

    }

    @Test
    public void shouldFilterNullPojoClassFromRegisteredPojoClassAdapters() {
        PojoClassAdaptationService pojoClassAdaptationService = new DefaultPojoClassAdaptationService();
        PojoClassAdapterMock pojoClassAdapterMock = new PojoClassAdapterMock();
        pojoClassAdaptationService.registerPojoClassAdapter(pojoClassAdapterMock);
        pojoClassAdaptationService.adapt(null);
        Affirm.affirmFalse("DefaultAdapationService didn't filter 'null' PojoClass call adapter",
                pojoClassAdapterMock.isCalled());

    }

    @Test
    public void shouldRejectNullPojoClassAdapter() {
        PojoClassAdaptationService pojoClassAdaptationService = new DefaultPojoClassAdaptationService();
        pojoClassAdaptationService.registerPojoClassAdapter(null);
        Affirm.affirmEquals(MessageFormatter.format("[{0}] registered a null PojoClassAdapter",
                pojoClassAdaptationService), 0, pojoClassAdaptationService.getRegisteredPojoAdapterClasses().size());
    }

    private class PojoClassAdapterMock implements PojoClassAdapter {
        private boolean called = false;

        public PojoClass adapt(PojoClass pojoClass) {
            called = true;
            return pojoClass;
        }

        public boolean isCalled() {
            return called;
        }
    }

    private PojoClass anyPojoClass() {
        return RandomFactory.getRandomValue(PojoClass.class);
    }

}
