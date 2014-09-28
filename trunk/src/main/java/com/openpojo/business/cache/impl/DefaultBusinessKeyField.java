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

package com.openpojo.business.cache.impl;

import com.openpojo.business.annotation.BusinessKey;
import com.openpojo.business.cache.BusinessKeyField;
import com.openpojo.reflection.PojoField;

/**
 * @author oshoukry
 */
public class DefaultBusinessKeyField implements BusinessKeyField {

    private final boolean isComposite;
    private final boolean isCaseSensitive;
    private final boolean isRequired;
    private final PojoField pojoField;

    public DefaultBusinessKeyField(PojoField pojoField, BusinessKey annotation) {
        this.pojoField = pojoField;
        isComposite = annotation.composite();
        isCaseSensitive = annotation.caseSensitive();
        isRequired = annotation.required();
    }

    public boolean isComposite() {
        return isComposite;
    }

    public boolean isCaseSensitive() {
        return isCaseSensitive;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public Object get(Object instance) {
        return pojoField.get(instance);
    }

    public boolean isArray() {
        return pojoField.isArray();
    }

    public String toString() {
        return String.format("DefaultBusinessKeyField [isRequired=%s, isComposite=%s, isCaseSensitive=%s, pojoField=%s]",
                isRequired, isComposite, isCaseSensitive, pojoField);
    }
}