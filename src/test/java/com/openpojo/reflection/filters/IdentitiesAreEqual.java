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
