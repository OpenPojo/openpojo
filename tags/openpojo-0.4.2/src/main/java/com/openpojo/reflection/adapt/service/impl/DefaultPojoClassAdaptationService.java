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
import com.openpojo.reflection.adapt.PojoClassAdapter;
import com.openpojo.reflection.adapt.service.PojoClassAdaptationService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author oshoukry
 */
public class DefaultPojoClassAdaptationService implements PojoClassAdaptationService {

    private final Set<PojoClassAdapter> pojoClassAdapters = new HashSet<PojoClassAdapter>();

    /**
     * Register a PojoClassAdapter, if the pojoClassAdapter is null, it will be silently ignored.
     *
     * @param pojoClassAdapter
     *         The Pojo Class Adapter to register.
     */
    public void registerPojoClassAdapter(final PojoClassAdapter pojoClassAdapter) {
        if (pojoClassAdapter != null) {
            pojoClassAdapters.add(pojoClassAdapter);
        }
    }

    public void unRegisterPojoClassAdapter(final PojoClassAdapter pojoClassAdapter) {
        pojoClassAdapters.remove(pojoClassAdapter);
    }

    public PojoClass adapt(final PojoClass pojoClass) {
        PojoClass returnPojoClass = pojoClass;
        if (returnPojoClass != null) {
            for (PojoClassAdapter pojoClassAdapter : pojoClassAdapters) {
                returnPojoClass = pojoClassAdapter.adapt(returnPojoClass);
            }
        }
        return returnPojoClass;
    }

    public Set<PojoClassAdapter> getRegisteredPojoAdapterClasses() {
        return Collections.unmodifiableSet(pojoClassAdapters);
    }
}
