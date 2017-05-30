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
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.rule.Rule;
import com.openpojo.validation.rule.impl.sampleclasses.AClassImplementingEqualsAndHashCode;
import com.openpojo.validation.rule.impl.sampleclasses.AClassImplementingEqualsOnly;
import com.openpojo.validation.rule.impl.sampleclasses.AClassImplementingHashcodeOnly;
import com.openpojo.validation.rule.impl.sampleclasses.AClassNotImplementingHashcodeOrEquals;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class EqualsAndHashCodeMatchRuleTest {
  private Rule rule = new EqualsAndHashCodeMatchRule();

  @Test
  public void mustImplementRule() {
    PojoClass equalsAndHashcodeMatchRule = PojoClassFactory.getPojoClass(EqualsAndHashCodeMatchRule.class);
    List<PojoClass> interfaces = equalsAndHashcodeMatchRule.getInterfaces();
    Assert.assertTrue("Expected interface=[Rule] to be implemented, but was not", interfaces.contains(PojoClassFactory
        .getPojoClass(Rule.class)));
  }

  @Test
  public void shouldPassIfNoEqualsOrHashcodeImplemented() {
    PojoClass aClassNotImplementingHashCodeOrEquals = PojoClassFactory.getPojoClass(AClassNotImplementingHashcodeOrEquals.class);
    List<PojoMethod> methods = aClassNotImplementingHashCodeOrEquals.getPojoMethods();


    Assert.assertEquals(1, methods.size());
    Assert.assertTrue(methods.get(0).isConstructor());

    rule.evaluate(aClassNotImplementingHashCodeOrEquals);
  }

  @Test
  public void shouldFailOnlyEqualsIsImplemented() {
    PojoClass aClassImplementingEqualsOnly = PojoClassFactory.getPojoClass(AClassImplementingEqualsOnly.class);
    List<PojoMethod> methods = aClassImplementingEqualsOnly.getPojoMethods();

    Assert.assertEquals(2, methods.size());
    boolean constructorFound = false;
    boolean equalsFound = false;

    for (PojoMethod method : methods) {
      if (method.isConstructor())
        constructorFound = true;
      if (method.getName().equals("equals")
          && method.getPojoParameters().size() == 1
          && method.getReturnType().equals(boolean.class))
        equalsFound = true;
    }

    Assert.assertTrue("Constructor not found", constructorFound);
    Assert.assertTrue("Equals not found", equalsFound);

    try {
      rule.evaluate(aClassImplementingEqualsOnly);
      Assert.fail("Should have failed validation but did not");
    } catch (AssertionError ae) {
      Assert.assertEquals("equals implemented but hashcode isn't in Pojo [" + aClassImplementingEqualsOnly + "]", ae.getMessage
          ());
    }
  }

  @Test
  public void shouldFailOnlyHashcodeIsImplemented() {
    PojoClass aClassImplementingHashcodeOnly = PojoClassFactory.getPojoClass(AClassImplementingHashcodeOnly.class);
    List<PojoMethod> methods = aClassImplementingHashcodeOnly.getPojoMethods();

    Assert.assertEquals(2, methods.size());
    boolean constructorFound = false;
    boolean hashCode = false;
    for (PojoMethod method : methods) {
      if (method.isConstructor())
        constructorFound = true;
      if (!method.isConstructor() && method.getName().equals("hashCode")
          && method.getPojoParameters().size() == 0
          && method.getReturnType().equals(int.class))
        hashCode = true;
    }

    Assert.assertTrue("Constructor not found", constructorFound);
    Assert.assertTrue("hashCode not found", hashCode);

    try {
      rule.evaluate(aClassImplementingHashcodeOnly);
      Assert.fail("Should have failed validation but did not");
    } catch (AssertionError ae) {
      Assert.assertEquals("hashCode implemented but equals isn't in Pojo [" + aClassImplementingHashcodeOnly + "]",
          ae.getMessage());
    }
  }

  @Test
  public void shouldPassWhenEqualsAndHashCodeAreImplemented() {
    PojoClass aClassImplementingEqualsAndHashcode = PojoClassFactory.getPojoClass(AClassImplementingEqualsAndHashCode.class);
    List<PojoMethod> methods = aClassImplementingEqualsAndHashcode.getPojoMethods();

    Assert.assertEquals(3, methods.size());

    boolean constructorFound = false;
    boolean equalsFound = false;
    boolean hashCodeFound = false;

    for (PojoMethod method : methods) {
      if (method.isConstructor())
        constructorFound = true;
      if (method.getName().equals("hashCode")
          && method.getPojoParameters().size() == 0
          && method.getReturnType().equals(int.class))
        hashCodeFound = true;
      if (method.getName().equals("equals")
          && method.getPojoParameters().size() == 1
          && method.getReturnType().equals(boolean.class))
        equalsFound = true;
    }

    Assert.assertTrue("Constructor not found", constructorFound);
    Assert.assertTrue("Equals not found", equalsFound);
    Assert.assertTrue("hashCode not found", hashCodeFound);

    rule.evaluate(aClassImplementingEqualsAndHashcode);
  }
}
