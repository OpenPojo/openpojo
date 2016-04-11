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

package com.openpojo.reflection.filters;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class FilterBasedOnInheritanceTest extends IdentitiesAreEqual {
  @Test
  public void shouldBeIdentityEqual() {
    FilterBasedOnInheritance instanceOne = new FilterBasedOnInheritance(SomeInterface.class);
    FilterBasedOnInheritance instanceTwo = new FilterBasedOnInheritance(SomeInterface.class);

    checkEqualityAndHashCode(instanceOne, instanceTwo);
  }

  @Test
  public void givenDifferentInterfaces_shouldNotBeEqual() {
    FilterBasedOnInheritance instanceOne = new FilterBasedOnInheritance(SomeInterface.class);
    FilterBasedOnInheritance instanceTwo = new FilterBasedOnInheritance(SomeOtherInterface.class);
    Assert.assertFalse(instanceOne.equals(instanceTwo));
    Assert.assertFalse(instanceTwo.equals(instanceOne));
    Assert.assertTrue(instanceOne.hashCode() != instanceTwo.hashCode());

    FilterBasedOnInheritance instanceThree = new FilterBasedOnInheritance(null);
    Assert.assertFalse(instanceOne.equals(instanceThree));
    Assert.assertFalse(instanceThree.equals(instanceOne));
    Assert.assertTrue(instanceOne.hashCode() != instanceThree.hashCode());
  }

  public interface SomeInterface {
  }

  public interface SomeOtherInterface {

  }
}
