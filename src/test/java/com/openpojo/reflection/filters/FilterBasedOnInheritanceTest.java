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
