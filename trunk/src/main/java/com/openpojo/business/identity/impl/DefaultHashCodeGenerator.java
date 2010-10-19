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
import com.openpojo.business.exception.BusinessException;
import com.openpojo.business.identity.HashCodeGenerator;
import com.openpojo.business.utils.BusinessIdentityUtils;
import com.openpojo.business.utils.BusinessPojoHelper;
import com.openpojo.reflection.PojoField;

/**
 * This class is the default implmentation for hash code generation.
 * The algorithm is simple and conforms to the java spec for hash code generation.
 * <br>
 * Please note that as per spec, two equal hash codes don't mean that objects are equal,
 * while two un-equal hash codes DO mean that the objects are not equal.
 * <br>
 *
 * @author oshoukry
 */

public class DefaultHashCodeGenerator implements HashCodeGenerator {

    private DefaultHashCodeGenerator() {
    }

    public static HashCodeGenerator getInstance() {
        return Instance.INSTANCE;
    }


    public int doGenerate(final Object object) {
        if (object == null) {
            throw BusinessException.getInstance("null parameter passed object=[null]");
        }

        final int prime = 31;
        int result = 1;

        for (PojoField pojoField : BusinessPojoHelper.getBusinessKeyFields(object.getClass())) {
            BusinessKey businessKey = pojoField.getAnnotation(BusinessKey.class);
            result = prime * result + BusinessIdentityUtils.getHashCode(pojoField, object, businessKey.caseSensitive());
        }
        return result;
    }

    private static class Instance {
        static final HashCodeGenerator INSTANCE = new DefaultHashCodeGenerator();
    }
}
