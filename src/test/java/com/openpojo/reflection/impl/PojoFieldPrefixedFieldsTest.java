/*
 * Copyright (c) 2010-2016 Osman Shoukry
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.openpojo.reflection.impl;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.reflection.cache.PojoCache;
import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.impl.sample.classes.AClassWithFieldsNotPrefixed;
import com.openpojo.reflection.impl.sample.classes.AClassWithFieldsPrefixed;
import com.openpojo.reflection.utils.AttributeHelper;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.rule.Rule;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PojoFieldPrefixedFieldsTest {

  @Before
  @After
  public void cleanup() {
    PojoCache.clear();
    AttributeHelper.clearRegistry();
  }

  @Test
  public void shouldHaveGettersAndSetters() {
    AttributeHelper.registerFieldPrefix("m");
    PojoClass pojoClass = PojoClassFactory.getPojoClass(AClassWithFieldsPrefixed.class);
    for (PojoField pojoField : pojoClass.getPojoFields()) {
      Affirm.affirmTrue(String.format("Getters / Setters not found on field =[%s]", pojoField), pojoField.hasGetter() &&
          pojoField.hasSetter());
    }
  }

  @Test
  public void shouldNotHaveGettersAndSetters() {
    PojoClass pojoClass = PojoClassFactory.getPojoClass(AClassWithFieldsPrefixed.class);
    for (PojoField pojoField : pojoClass.getPojoFields()) {
      Affirm.affirmFalse(String.format("Getters / Setters not found on field =[%s]", pojoField), pojoField.hasGetter() ||
          pojoField.hasSetter());
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
