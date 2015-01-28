/*
 * Copyright (c) 2010-2015 Osman Shoukry
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
import com.openpojo.business.cache.BusinessKeyField;
import com.openpojo.business.exception.BusinessException;
import com.openpojo.business.identity.BusinessValidator;
import com.openpojo.business.utils.BusinessPojoHelper;

/**
 * This class is the default implementation for business validation.
 * Its primary responsibility is to validate that a business POJO has the following:<br>
 * 1. One or more {@link BusinessKey} fields defined.<br>
 * 2. All Required/Composite fields have values where required.
 *
 * @author oshoukry
 */
public class DefaultBusinessValidator implements BusinessValidator {

    private DefaultBusinessValidator() {

    }

    public static BusinessValidator getInstance() {
        return DefaultBusinessValidator.Instance.INSTANCE;
    }

    public void validate(final Object object) {
        if (object == null) {
            return;
        }

        boolean compositeGroupPassed = false;
        boolean hasCompositeGroup = false;
        boolean hasBusinessKey = false;
        for (BusinessKeyField businessKeyField : BusinessPojoHelper.getBusinessKeyFields(object.getClass())) {
            hasBusinessKey = true;
            if (businessKeyField.isComposite()) {
                if (businessKeyField.get(object) != null) {
                    compositeGroupPassed = true;
                }
                hasCompositeGroup = true;
            } else {
                if (businessKeyField.isRequired() && businessKeyField.get(object) == null) {
                    throw BusinessException.getInstance((String.format("Field required and can't be null [%s]", businessKeyField)));
                }
            }
        }
        if (!hasBusinessKey) {
            throw BusinessException.getInstance(String.format("No business Keys defined on class=[%s]", object.getClass()));
        }

        if (!compositeGroupPassed && hasCompositeGroup) {
            throw BusinessException.getInstance(String.format("Non of the fields in the composite group were populated [%s]",
                    object.getClass()));
        }
    }

    private static class Instance {
        static final BusinessValidator INSTANCE = new DefaultBusinessValidator();
    }
}
