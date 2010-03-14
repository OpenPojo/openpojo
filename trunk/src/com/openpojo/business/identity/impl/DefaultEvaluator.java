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
import com.openpojo.business.identity.IdentityEvaluator;
import com.openpojo.business.utils.BusinessIdentityUtils;
import com.openpojo.business.utils.BusinessPojoHelper;
import com.openpojo.reflection.PojoField;

/**
 * @author oshoukry
 */
public class DefaultEvaluator implements IdentityEvaluator {

    public boolean areEqual(final Object first, final Object second) {
        if (BusinessIdentityUtils.anyNull(first, second) || !BusinessIdentityUtils.sameClass(first, second)) {
            return false;
        }

        if (BusinessIdentityUtils.sameInstance(first, second)) {
            return true;
        }

        boolean runningEquality = true;
        for (PojoField pojoField : BusinessPojoHelper.getBusinessKeyFields(first.getClass())) {
            BusinessKey businessKey = pojoField.getAnnotation(BusinessKey.class);
            runningEquality = runningEquality && BusinessIdentityUtils.areEqual(pojoField, first, second, businessKey.caseSensitive());
        }

        return runningEquality;
    }

}
