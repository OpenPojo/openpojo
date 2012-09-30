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

import java.util.HashSet;
import java.util.Set;

/**
 * @author oshoukry
 */
public class DefaultPojoClassAdaptationService implements PojoClassAdaptationService {

    private final Set<PojoClassAdaptor> pojoClassAdaptors = new HashSet<PojoClassAdaptor>();

    public void registerPojoClassAdaptor(final PojoClassAdaptor pojoClassAdaptor) {
        pojoClassAdaptors.add(pojoClassAdaptor);
    }

    public void unRegisterPojoClassAdaptor(final PojoClassAdaptor pojoClassAdaptor) {
        pojoClassAdaptors.remove(pojoClassAdaptor);
    }

    public PojoClass adapt(final PojoClass pojoClass) {
        PojoClass returnPojoClass = pojoClass;
        for (PojoClassAdaptor pojoClassAdaptor : pojoClassAdaptors) {
            returnPojoClass = pojoClassAdaptor.adapt(returnPojoClass);
        }
        return returnPojoClass;
    }
}
