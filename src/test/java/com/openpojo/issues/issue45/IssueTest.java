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

package com.openpojo.issues.issue45;

import java.lang.reflect.TypeVariable;
import java.util.List;

import com.openpojo.issues.issue45.sample.AClassWithGenericArray;
import com.openpojo.issues.issue45.sample.AClassWithGenericCollection;
import com.openpojo.issues.issue45.sample.AParameterizedClass;
import com.openpojo.issues.issue45.sample.ClassWithVariousGenericSetList;
import com.openpojo.issues.issue45.sample.SomeGeneric;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;

/**
 * @author oshoukry
 */
public class IssueTest {

  private PojoClass aClassWithGenericCollectionPojo;
  private PojoClass aParameterizedClass;
  private Validator pojoValidator;

  @Before
  public void setUp() throws Exception {
    aClassWithGenericCollectionPojo = PojoClassFactory.getPojoClass(AClassWithGenericCollection.class);
    aParameterizedClass = PojoClassFactory.getPojoClass(AParameterizedClass.class);

    pojoValidator = ValidatorBuilder.create()
        .with(new GetterMustExistRule())
        .with(new SetterMustExistRule())
        .with(new SetterTester())
        .with(new GetterTester())
        .build();
  }

  @Test
  @SuppressWarnings("AssertEqualsBetweenInconvertibleTypes")
  public void ensureAClassWithGenericCollectionPojo_HasNotBeenModified() {
    Assert.assertThat("Fields added / removed to sample class?", aClassWithGenericCollectionPojo.getPojoFields().size(), is(1));
    PojoField pojoField = aClassWithGenericCollectionPojo.getPojoFields().get(0);
    Assert.assertEquals(List.class, pojoField.getType());
    Assert.assertThat("Field should have only one parameter", pojoField.getParameterTypes().size(), is(1));
    Assert.assertEquals(SomeGeneric.class, pojoField.getParameterTypes().get(0));
  }

  @Test
  public void ensureAParameterizedClass_HasNotBeenModified() {
    Assert.assertThat(aParameterizedClass.getPojoFields().size(), is(1));
    Assert.assertThat(aParameterizedClass.getClazz().getTypeParameters().length, is(1));
    Assert.assertTrue(TypeVariable.class.isAssignableFrom(aParameterizedClass.getClazz().getTypeParameters()[0].getClass()));
  }

  @Test
  public void whenAClassHasGenericCollection_RandomGeneratorShouldPopulateProperly() {
    pojoValidator.validate(aClassWithGenericCollectionPojo);
  }

  @Test
  public void testAParameterizedClass() {
    pojoValidator.validate(aParameterizedClass);
  }

  @Test
  public void testAClassWithGenericArray() {
    pojoValidator.validate(PojoClassFactory.getPojoClass(AClassWithGenericArray.class));
  }

  @Test
  public void testEndToEndCollections() {
    pojoValidator = ValidatorBuilder.create().with(new SetterMustExistRule())
        .with(new SetterTester())
        .build();

    pojoValidator.validate(PojoClassFactory.getPojoClass(ClassWithVariousGenericSetList.class));
  }

}
