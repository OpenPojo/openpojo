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

package com.openpojo.validation.rule.impl;

import java.util.LinkedList;
import java.util.List;

import com.openpojo.log.utils.MessageFormatter;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.rule.Rule;

//@formatter:off
/**
 * This rule ensures that no subclass shadows fields defined in a parent class.
 *
 *{@code
 *  For example:
 *   Public class A {
 *     private String myString;
 *   }
 *
 *   Public class B extends A {
 *     private String myString;
 *   }
 *}
 *
 * @author oshoukry
 *
 */
//@formatter:on
public class NoFieldShadowingRule implements Rule {

    public void evaluate(final PojoClass pojoClass) {
        final List<PojoField> parentPojoFields = new LinkedList<PojoField>();
        PojoClass parentPojoClass = pojoClass.getSuperClass();
        while (parentPojoClass != null) {
            parentPojoFields.addAll(parentPojoClass.getPojoFields());
            parentPojoClass = parentPojoClass.getSuperClass();
        }
        final List<PojoField> childPojoFields = pojoClass.getPojoFields();
        for (final PojoField childPojoField : childPojoFields) {
            if (contains(childPojoField.getName(), parentPojoFields)) {
                Affirm.fail(MessageFormatter.format("Field=[{0}] shadows field with the same name in parent class=[{1}]",
                                                    childPojoField, parentPojoFields));
            }
        }

    }

    private boolean contains(final String fieldName, final List<PojoField> pojoFields) {
        for (final PojoField pojoField : pojoFields) {
            if (pojoField.getName().equals(fieldName)) {
                return true;
            }
        }
        return false;
    }

}
