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

package com.openpojo.validation.rule.impl;

import com.openpojo.business.cache.BusinessKeyField;
import com.openpojo.reflection.PojoClass;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.rule.Rule;

import static com.openpojo.business.utils.BusinessPojoHelper.getBusinessKeyFields;

/**
 * This rule ensures that PojoClass declares at least one required {@link com.openpojo.business.annotation.BusinessKey}.<br>
 * Required BusinessKey means either the required = true, or composite = true.
 *
 * @author oshoukry
 */
public class BusinessKeyMustExistRule implements Rule {

    public void evaluate(final PojoClass pojoClass) {
        for (BusinessKeyField businessField : getBusinessKeyFields(pojoClass.getClazz())) {
            if (businessField.isRequired() || businessField.isComposite()) {
                return;
            }
        }

        Affirm.fail(String.format("[%s] doesn't declare any 'required' BusinessKeys!!", pojoClass.getClazz()));
    }
}
