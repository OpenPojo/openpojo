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

package com.openpojo.validation.affirm;

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author oshoukry
 */
public abstract class AbstractAffirmationTest {

  public abstract Affirmation getAffirmation();

  @Before
  public void setup() {
    AffirmationFactory.getInstance().setActiveAffirmation(getAffirmation());
  }

  /**
   * Test method for {@link com.openpojo.validation.affirm.Affirm#fail(java.lang.String)}.
   */
  @Test
  public void testFail() {
    try {
      Affirm.fail("Expected failure!!");
    } catch (AssertionError e) {
      return;
    }
    Assert.fail("Affirm.fail(String) failed to fail :)!!");
  }

  @Test
  public void testFailWithNullMessage() {
    try {
      Affirm.fail(null);
    } catch (AssertionError e) {
      return;
    }
    Assert.fail("Affirm.fail(null) failed to fail :)!!");
  }

  /**
   * Test method for {@link com.openpojo.validation.affirm.Affirm#affirmTrue(java.lang.String, boolean)}.
   */
  @Test
  public void testAffirmTrue() {
    Affirm.affirmTrue("Affirm.affirmTrue on true failed!!", true);
    try {
      Affirm.affirmTrue("Affirm.affirmTrue on false passed!!", false);
    } catch (AssertionError e) {
      return;
    }
    Assert.fail("Affirm.affirmTrue call on false passed!!");
  }

  /**
   * Test method for {@link com.openpojo.validation.affirm.Affirm#affirmFalse(java.lang.String, boolean)}.
   */
  @Test
  public void testAffirmFalse() {
    Affirm.affirmFalse("Affirm.affirmFalse on false failed!!", false);
    try {
      Affirm.affirmFalse("Affirm.affirmTrue on true passed!!", true);
    } catch (AssertionError e) {
      return;
    }
    Assert.fail("Affirm.affirmFalse call on true passed!!");
  }

  /**
   * Test method for {@link com.openpojo.validation.affirm.Affirm#affirmNotNull(java.lang.String, java.lang.Object)}.
   */
  @Test
  public void testAffirmNotNull() {
    Affirm.affirmNotNull("Affirm.affirmNotNull on non-null failed!!", new Object());
    try {
      Affirm.affirmNotNull("Affirm.affirmNotNull on null passed!!", null);
    } catch (AssertionError e) {
      return;
    }
    Assert.fail("Affirm.affirmNotNull call on null passed!!");
  }

  /**
   * Test method for {@link com.openpojo.validation.affirm.Affirm#affirmNull(java.lang.String, java.lang.Object)}.
   */
  @Test
  public void testAffirmNull() {
    Affirm.affirmNull("Affirm.affirmNull on null failed!!", null);
    try {
      Affirm.affirmNull("Affirm.affirmNull on non-null passed!!", new Object());
    } catch (AssertionError e) {
      return;
    }
    Assert.fail("Affirm.affirmNull call on non-null passed!!");
  }

  /**
   * Test method for
   * {@link com.openpojo.validation.affirm.Affirm#affirmEquals(java.lang.String, java.lang.Object, java.lang.Object)}.
   */
  @Test
  @SuppressWarnings("UnnecessaryBoxing")
  public void testAffirmEquals() {
    Integer five = new Integer(5);
    Integer anotherFive = new Integer(5);
    Integer six = new Integer(6);

    Affirm.affirmEquals("Affirm.affirmEquals on equal objects failed", five, anotherFive);
    try {
      Affirm.affirmEquals("Affirm.affirmEquals on non-equal objects should have failed.", five, six);
    } catch (AssertionError e) {
      return;
    }
    Assert.fail("Affirm.affirmEquals call on non-equal objects passed!");
  }

  @Test
  public void testAffirmSame() {
    Object o = new Object();
    Affirm.affirmSame("Affirm.affirmSame on same objects failed", o, o);
    try {
      Affirm.affirmSame("Affirm.affirmSame on non-same objects should have failed", new Object(), new Object());
    } catch (AssertionError e) {
      return;
    }
    Assert.fail("Affirm.affirmSame call on non-same objects passed");
  }

  @Test
  public void testAffirmContains() {
    List<String> myList = new LinkedList<String>();
    myList.add("This");
    myList.add("is");
    myList.add("mylist");
    Affirm.affirmContains("should find the word 'is' in my list", "is", myList);

    try {
      Affirm.affirmContains("should not find the word 'WHAT' in my list", "WHAT", myList);
    } catch (AssertionError e) {
      return;
    }
    Assert.fail("Affirm.affirmContains failed to detect that 'WHAT' is not part of the collection");
  }

  @Test
  public void testAffirmContainsWithNulls() {
    List<String> myList = new LinkedList<String>();
    myList.add("This");
    myList.add("is");
    myList.add("mylist");
    myList.add(null);

    Affirm.affirmContains("Should find a null in the list", null, myList);
    myList.remove(null);

    try {
      Affirm.affirmContains("Should not find a null in the list", null, myList);
    } catch (AssertionError e) {
      return;
    }
    Assert.fail("Affirm.affirmContains failed to detect a missing null in the list");
  }

  @Test(expected = java.lang.AssertionError.class)
  public void shouldFailWhenArraysNotEqualSizes() {
    byte[] expected = new byte[] { (byte) 0xaa, (byte) 0xbb, (byte) 0xcc };
    byte[] actual = new byte[] { (byte) 0xaa };
    Affirm.affirmEquals("Should fail due to size", expected, actual);
  }

  @Test
  public void whenArrayIsClonedEqualityShouldPass() {
    byte[] expected = new byte[] { (byte) 0xaa, (byte) 0xbb, (byte) 0xcc };
    byte[] actual = expected.clone();
    Affirm.affirmEquals("Array clone should be equal", expected, actual);

  }

  @Test(expected = java.lang.AssertionError.class)
  public void shouldFailWhenArrayItemMismatch() {
    byte[] expected = new byte[] { (byte) 0xaa, (byte) 0xbb, (byte) 0xcc };
    byte[] actual = new byte[] { (byte) 0xaa, (byte) 0xbb, (byte) 0xdd };
    Affirm.affirmEquals("Should fail due to element mismatch", expected, actual);
  }

  @Test(expected = java.lang.AssertionError.class)
  public void shouldNotFailWhenArrayIsNull() {
    byte[] expected = new byte[] { (byte) 0xaa, (byte) 0xbb, (byte) 0xcc };
    Affirm.affirmEquals("Should not fail due to null", expected, null);
  }

}
