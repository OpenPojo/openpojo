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

package com.openpojo.reflection.impl;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.reflection.cache.PojoCache;
import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.impl.sampleclasses.AClassWithFieldsNotPrefixed;
import com.openpojo.reflection.impl.sampleclasses.AClassWithFieldsPrefixed;
import com.openpojo.reflection.utils.AttributeHelper;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.rule.Rule;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PojoFieldPrefixedFieldsTest {

    @Before
    @After
    public void cleanup() {
        PojoCache.clear();
        AttributeHelper.clearRegistery();
    }

    @Test
    public void shouldHaveGettersAndSetters() {
        AttributeHelper.registerFieldPrefix("m");
        PojoClass pojoClass = PojoClassFactory.getPojoClass(AClassWithFieldsPrefixed.class);
        for (PojoField pojoField : pojoClass.getPojoFields()) {
            Affirm.affirmTrue(String.format("Getters / Setters not found on field =[%s]", pojoField), pojoField
                    .hasGetter()
                    && pojoField.hasSetter());
        }
    }

    @Test
    public void shouldNotHaveGettersAndSetters() {
        PojoClass pojoClass = PojoClassFactory.getPojoClass(AClassWithFieldsPrefixed.class);
        for (PojoField pojoField : pojoClass.getPojoFields()) {
            Affirm.affirmFalse(String.format("Getters / Setters not found on field =[%s]", pojoField), pojoField
                    .hasGetter()
                    || pojoField.hasSetter());
        }
    }

    @Test(expected = ReflectionException.class)
    public void shouldFailAttributeName() {
        AttributeHelper.registerFieldPrefix("mName");
        PojoClassFactory.getPojoClass(AClassWithFieldsPrefixed.class);
    }

    @Test
    public void unRegisteringAPrefix_reflectsOnMethodLookup() {
        AttributeHelper.registerFieldPrefix("m");
        PojoClass pojoClass = PojoClassFactory.getPojoClass(AClassWithFieldsPrefixed.class);
        Rule getterRule = new GetterMustExistRule();
        getterRule.evaluate(pojoClass);

        AttributeHelper.unregisterFieldPrefix("m");
        PojoCache.clear();

        pojoClass = PojoClassFactory.getPojoClass(AClassWithFieldsPrefixed.class);
        boolean unregisterdSuccessfully = false;
        try {
            getterRule.evaluate(pojoClass);
        } catch (AssertionError ae) {
            unregisterdSuccessfully = true;
        }
        if (!unregisterdSuccessfully)
            Assert.fail("unregistering failed?!");
    }

    @Test
    public void unmatchedPrefixesHaveNoEffect() {
        AttributeHelper.registerFieldPrefix("n");
        PojoClass pojoClass = PojoClassFactory.getPojoClass(AClassWithFieldsNotPrefixed.class);
        Rule getterRule = new GetterMustExistRule();
        getterRule.evaluate(pojoClass);
    }

    @Test
    public void registeringNullOrEmptyPrefixIsIgnored() {
        AttributeHelper.registerFieldPrefix(null);
        AttributeHelper.registerFieldPrefix("");
        PojoClass pojoClass = PojoClassFactory.getPojoClass(AClassWithFieldsNotPrefixed.class);
        Rule getterRule = new GetterMustExistRule();
        getterRule.evaluate(pojoClass);

        AttributeHelper.registerFieldPrefix("m");
        pojoClass = PojoClassFactory.getPojoClass(AClassWithFieldsPrefixed.class);
        getterRule.evaluate(pojoClass);
    }
}
