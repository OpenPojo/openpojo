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

package com.openpojo.issues.issue22;

import com.openpojo.issues.issue22.sampleclasses.SomeBean;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import org.junit.Test;

public class GetterSetterTest {

    @Test
    public void shouldPassGetterSetterTest() {
        PojoClass pojoClass = PojoClassFactory.getPojoClass(SomeBean.class);
        Validator pojoValidator = ValidatorBuilder.create()
                .with(new SetterMustExistRule())
                .with(new GetterMustExistRule())
                .build();
        pojoValidator.validate(pojoClass);
    }

}
