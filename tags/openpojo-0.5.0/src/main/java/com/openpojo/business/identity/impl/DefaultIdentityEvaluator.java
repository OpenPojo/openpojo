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

package com.openpojo.business.identity.impl;

import com.openpojo.business.annotation.BusinessKey;
import com.openpojo.business.identity.IdentityEvaluator;
import com.openpojo.business.utils.BusinessIdentityUtils;
import com.openpojo.business.utils.BusinessPojoHelper;
import com.openpojo.reflection.PojoField;

/**
 * This class is the default implementation for identity evaluation.
 * The primary function of this class is to evaluate two business entities' equality. <br>
 * <br>
 * Note:
 * if two business fields are defined as optional (aka required = false) and both are set to null,
 * they are considered equal.
 *
 * @author oshoukry
 */
public class DefaultIdentityEvaluator implements IdentityEvaluator {

    private DefaultIdentityEvaluator() {

    }

    public static IdentityEvaluator getInstance() {
        return Instance.INSTANCE;
    }

    public boolean areEqual(final Object first, final Object second) {
    	if (BusinessIdentityUtils.sameInstance(first, second)) {
            return true;
        }

        boolean runningEquality = true;
        for (PojoField pojoField : BusinessPojoHelper.getBusinessKeyFields(first.getClass())) {
            BusinessKey businessKey = pojoField.getAnnotation(BusinessKey.class);
            runningEquality = runningEquality
                    && BusinessIdentityUtils.areEqual(pojoField, first, second, businessKey.caseSensitive());
        }
        return runningEquality;
    }

    private static class Instance {
        static final IdentityEvaluator INSTANCE = new DefaultIdentityEvaluator();
    }

}
