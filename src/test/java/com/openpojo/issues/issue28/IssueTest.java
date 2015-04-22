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

package com.openpojo.issues.issue28;

import org.junit.Before;
import org.junit.Test;

import com.openpojo.issues.issue28.sample.AChildOfAnotherChildClass;
import com.openpojo.issues.issue28.sample.AnotherChildClass;
import com.openpojo.issues.issue28.sample.ChildClass;
import com.openpojo.issues.issue28.sample.ParentClass;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.PojoValidator;
import com.openpojo.validation.rule.impl.NoFieldShadowingRule;

/**
 * @author oshoukry
 *
 */
public class IssueTest {
    final PojoValidator pojoValidator = new PojoValidator();

    @Before
    public void setup() {
        pojoValidator.addRule(new NoFieldShadowingRule());
    }

    @Test(expected = AssertionError.class)
    public void shouldFailBecauseShadowingParentField() {
        final PojoClass pojoClass = PojoClassFactory.getPojoClass(ChildClass.class /* ParentClass.class is parent */);
        pojoValidator.runValidation(pojoClass);
    }

    @Test(expected = AssertionError.class)
    public void shouldFailBecauseShadowingParentsParentField() {
        final PojoClass pojoClass = PojoClassFactory.getPojoClass(AChildOfAnotherChildClass.class /* AnotherChildClass.class is parent */);
        pojoValidator.runValidation(pojoClass);
    }

    @Test
    public void shouldPassNoShadowing() {
        final PojoClass pojoClass = PojoClassFactory.getPojoClass(AnotherChildClass.class /* Object is parent */);
        pojoValidator.runValidation(pojoClass);
    }

    @Test
    public void shouldPassBecauseOfDefaultObjectParent() {
        final PojoClass pojoClass = PojoClassFactory.getPojoClass(ParentClass.class /* Object is parent */);
        pojoValidator.runValidation(pojoClass);
    }

    @Test
    public void shouldPassBecauseNoParentDefined() {
        final PojoClass pojoClass = PojoClassFactory.getPojoClass(Object.class /* No Parent */);
        pojoValidator.runValidation(pojoClass);
    }

}
