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

package com.openpojo.issues.issue47;

import com.openpojo.issues.issue47.sample.ClassWithBoolean_isX;
import com.openpojo.issues.issue47.sample.ClassWithBoolean_isy;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class IssueTest {

    @Test
    public void whenBooleanStartsWith_isAndThirdCharacterIsUpperCase_Getter_IsX_Setter_setX() {
        PojoClass pojoClass = PojoClassFactory.getPojoClass(ClassWithBoolean_isX.class);
        Validator pojoValidator = ValidatorBuilder.create()
                .with(new GetterMustExistRule())
                .with(new SetterMustExistRule())
                .build();
        pojoValidator.validate(pojoClass);
    }

    @Test
    public void whenBooleanStartsWith_is_AndThirdCharacterIsNotUpperCase_Getter_isIsY_Setter_setIsy() {
        PojoClass pojoClass = PojoClassFactory.getPojoClass(ClassWithBoolean_isy.class);
        Validator pojoValidator = ValidatorBuilder.create()
                .with(new GetterMustExistRule())
                .with(new SetterMustExistRule())
                .build();
        pojoValidator.validate(pojoClass);

    }
}
