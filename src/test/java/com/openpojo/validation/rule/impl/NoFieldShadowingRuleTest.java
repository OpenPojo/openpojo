/*
 * Copyright (c) 2010-2017 Osman Shoukry
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

package com.openpojo.validation.rule.impl;

import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.sampleclasses.NoShadowAChildWithFieldShadowing;
import com.openpojo.validation.rule.impl.sampleclasses.NoShadowAParentClassWithOneField;
import com.openpojo.validation.rule.impl.sampleclasses.NoShadowSerializableChild;
import com.openpojo.validation.rule.impl.sampleclasses.NoShadowSerializableChildWithoutImplementationOfSerializableInterface;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;

/**
 * @author oshoukry
 */
public class NoFieldShadowingRuleTest {
  private Validator validator;

  @Before
  public void setup() {
    validator = ValidatorBuilder
        .create()
        .with(new NoFieldShadowingRule())
        .build();
  }

  @Test
  public void aClassWithoutFields() {
    PojoClass aClassWithoutFields = PojoClassFactory.getPojoClass(AClassWithSyntheticField.class);
    validator.validate(aClassWithoutFields);
  }

  private class AClassWithSyntheticField {
  }

  @Test
  public void aClassWithSyntheticFieldShadowing() {
    PojoClass aClassWithSyntheticFieldShadowing = PojoClassFactory.getPojoClass(AClassWithSyntheticFieldShadowing.class);
    validator.validate(aClassWithSyntheticFieldShadowing);

  }
  
  private class AClassWithSyntheticFieldShadowing extends AClassWithSyntheticField {
  }

  @Test
  public void ensureAParentClassWithOneFieldSampleClassIsAccurate() {
    PojoClass aParentClassWithOneField = PojoClassFactory.getPojoClass(NoShadowAParentClassWithOneField.class);
    List<PojoField> pojoFields = aParentClassWithOneField.getPojoFields();
    Assert.assertThat(pojoFields.size(), is(1));
    Assert.assertThat(pojoFields.get(0).getName(), is("aField"));
  }

  @Test
  public void ensureAChildWithFieldShadowingIsAccurateWW() {
    PojoClass aChildWithFieldShadowing = PojoClassFactory.getPojoClass(NoShadowAChildWithFieldShadowing.class);
    List<PojoField> pojoFields = aChildWithFieldShadowing.getPojoFields();
    Assert.assertThat(pojoFields.size(), is(1));
    Assert.assertThat(pojoFields.get(0).getName(), is("aField"));
  }

  @Test
  public void aChildWithFieldShadowingShouldFail() {
    PojoClass aChildWithFieldShadowing = PojoClassFactory.getPojoClass(NoShadowAChildWithFieldShadowing.class);
    try {
      validator.validate(aChildWithFieldShadowing);
      Assert.fail("Expected [NoShadowAChildWithFieldShadowing.class] to fail NoFieldShadowRule but didn't");
    } catch (AssertionError ae) {
      Assert.assertEquals("Field=[PojoFieldImpl " +
          "[field=private java.lang.String com.openpojo.validation.rule.impl.sampleclasses.NoShadowAChildWithFieldShadowing.aField, " +
          "fieldGetter=null, " +
          "fieldSetter=null]] " +
          "shadows field with the same name in " +
          "parent class=[[PojoFieldImpl [" +
          "field=private java.lang.String com.openpojo.validation.rule.impl.sampleclasses.NoShadowAParentClassWithOneField.aField, " +
          "fieldGetter=null," +
          " fieldSetter=null]]]", ae.getMessage());
    }
  }

  @Test
  public void preConfiguredShadowFieldsSkipped() {
    PojoClass aChildWithFieldShadowing = PojoClassFactory.getPojoClass(NoShadowAChildWithFieldShadowing.class);
    validator = ValidatorBuilder
        .create()
        .with(new NoFieldShadowingRule("aField"))
        .build();
    validator.validate(aChildWithFieldShadowing);
  }

  @Test
  public void nonMatchingSkipFieldsHaveNoEffect() {
    PojoClass aChildWithFieldShadowing = PojoClassFactory.getPojoClass(NoShadowAChildWithFieldShadowing.class);
    validator = ValidatorBuilder
        .create()
        .with(new NoFieldShadowingRule("a non existing field name"))
        .build();
    try {
      validator.validate(aChildWithFieldShadowing);
      Assert.fail("Should've failed shadow check");
    } catch (AssertionError ignored) {

    }
  }

  @Test
  public void shouldNotAddserialVersionUIDToShadowFieldList() {
    validator.validate(PojoClassFactory.getPojoClass(NoShadowSerializableChild.class));
  }
  
  @Test
  public void shouldNotAddserialVersionUIDFromChildWithoutInterfaceToShadowFieldList() {
    validator.validate(PojoClassFactory.getPojoClass(NoShadowSerializableChildWithoutImplementationOfSerializableInterface.class));
  }


}
