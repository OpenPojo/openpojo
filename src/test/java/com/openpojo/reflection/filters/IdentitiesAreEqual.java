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

import org.testng.Assert;

/**
 * @author oshoukry
 */
public class IdentitiesAreEqual {

  protected void checkEqualityAndHashCode(Object instanceOne, Object instanceTwo) {
    twoInstancesShouldBeEqual(instanceOne, instanceTwo);
    twoInstanceShouldHaveTheSameHashCode(instanceOne, instanceTwo);
    sameInstanceShouldBeEqualToItself(instanceOne);
    instanceEqualsReturnsFalseWithObject(instanceOne);
    instanceEqualsNullIsFalse(instanceOne);
  }

  private void twoInstancesShouldBeEqual(Object instanceOne, Object instanceTwo) {
    Assert.assertNotSame(instanceOne, instanceTwo);
    Assert.assertEquals(instanceOne, instanceTwo);
  }

  private void twoInstanceShouldHaveTheSameHashCode(Object instanceOne, Object instanceTwo) {
    Assert.assertNotSame(instanceOne, instanceTwo);
    Assert.assertEquals(instanceOne.hashCode(), instanceTwo.hashCode());
  }

  private void sameInstanceShouldBeEqualToItself(Object instanceOne) {
    Assert.assertEquals(instanceOne, instanceOne);
  }

  private void instanceEqualsReturnsFalseWithObject(Object instanceOne) {
    Assert.assertTrue(!instanceOne.equals(new Object()));
  }

  private void instanceEqualsNullIsFalse(Object instanceOne) {
    Assert.assertTrue(!instanceOne.equals(instanceOne.getClass().cast(null)));
  }
}
