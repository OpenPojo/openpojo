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
import com.openpojo.business.identity.BusinessValidator;
import com.openpojo.business.utils.BusinessPojoHelper;
import com.openpojo.reflection.PojoField;

/**
 * This class is the default implementation for business validation.
 * Its primary responsibitily is to validate that a business pojo has the following:<br>
 * 1. One or more {@link BusinessKey} fields defined.<br>
 * 2. All Required/Composite fields have values where required.
 * @author oshoukry
 */
public class DefaultBusinessValidator implements BusinessValidator {

    public void validate(final Object object) {
        if (object == null) {
            return;
        }

        boolean compositeGroupPassed = false;
        boolean hasCompositeGroup = false;
        boolean hasBusinessKey = false;
        for (PojoField pojoField : BusinessPojoHelper.getBusinessKeyFields(object.getClass())) {
            final BusinessKey businessKey = pojoField.getAnnotation(BusinessKey.class);
            hasBusinessKey = true;
            if (businessKey.composite()) {
                if (pojoField.get(object) != null) {
                    compositeGroupPassed = true;
                }
                hasCompositeGroup = true;
            } else {
                if (businessKey.required() && pojoField.get(object) == null) {
                    throw new BusinessException(String.format("Field required and can't be null [%s]", pojoField));
                }
            }
        }
        if (!hasBusinessKey) {
            throw new BusinessException(String.format("No business Keys defined on class=[%s]", object.getClass()));
        }

        if (!compositeGroupPassed && hasCompositeGroup) {
            throw new BusinessException(String.format("Non of the fields in the composite group were populated [%s]",
                    object.getClass()));
        }
    }

}
