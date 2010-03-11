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
package com.openpojo.business.identity.impl;

import com.openpojo.business.annotation.BusinessKey;
import com.openpojo.business.identity.HashCodeGenerator;
import com.openpojo.business.utils.BusinessIdentityUtils;
import com.openpojo.business.utils.BusinessPojoHelper;
import com.openpojo.reflection.PojoField;

/**
 * @author oshoukry
 * 
 */

public class DefaultHashCodeGenerator implements HashCodeGenerator {

    public int doGenerate(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("null parameter passed object=[null]");
        }

        final int prime = 31;
        int result = 1;

        for (PojoField pojoField : BusinessPojoHelper.getBusinessKeyFields(object.getClass())) {
            BusinessKey businessKey = pojoField.getAnnotation(BusinessKey.class);
            result = prime * result + BusinessIdentityUtils.getHashCode(pojoField, object, businessKey.ignoreCase());
        }
        return result;
    }
}
