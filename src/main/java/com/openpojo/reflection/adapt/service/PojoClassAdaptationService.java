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

package com.openpojo.reflection.adapt.service;

import java.util.Set;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.adapt.PojoClassAdapter;

/**
 * A service to modify PojoClasses.
 * The primary reason for usage should be to mask certain fields from being visible to downstream processes.
 * Can be used to make invisible certain constructors, or fields and their methods.
 *
 * @author oshoukry
 */
public interface PojoClassAdaptationService {

    /**
     * This method is used to register POJO class adapter.
     *
     * @param pojoClassAdapter
     *         The pojoClassAdapter to register.
     */
    public void registerPojoClassAdapter(PojoClassAdapter pojoClassAdapter);

    /**
     * Unregister a previously registered PojoClassAdapter.
     *
     * @param pojoClassAdapter
     *         the pojoClassAdapter to unregister
     */
    public void unRegisterPojoClassAdapter(PojoClassAdapter pojoClassAdapter);

    /**
     * @param pojoClass
     *         The pojoClass to modify.
     * @return processed PojoClass.
     */
    public PojoClass adapt(PojoClass pojoClass);

    /**
     * @return the set of registered pojo adapter classes.
     */
    public Set<PojoClassAdapter> getRegisteredPojoAdapterClasses();
}
