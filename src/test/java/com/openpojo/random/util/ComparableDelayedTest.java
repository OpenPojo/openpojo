/*
 * Copyright (c) 2010-2018 Osman Shoukry
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

package com.openpojo.random.util;

import com.openpojo.validation.affirm.Affirm;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class ComparableDelayedTest {

  @Test
  public void delayShouldBeBetweenMinus3And1() {
    for (int count = 0; count < 100; count++) {
      long delay = new ComparableDelayed().getDelay(null);
      Assert.assertTrue("Should be under 2", 2 > delay);
      Assert.assertTrue("Should be over -3", -4 < delay);
    }
  }

  @Test
  public void shouldWireCompareToOverHashCodeValue() {
    ComparableDelayed firstInstance = new ComparableDelayed();
    ComparableDelayedStub secondInstance = new ComparableDelayedStub();

    secondInstance.hashCode = firstInstance.hashCode() - 1;
    Affirm.affirmEquals("CompareTo should return 1", 1, firstInstance.compareTo(secondInstance));

    secondInstance.hashCode = firstInstance.hashCode() + 1;
    Affirm.affirmEquals("CompareTo should return -1", -1, firstInstance.compareTo(secondInstance));

    secondInstance.hashCode = firstInstance.hashCode();
    Affirm.affirmEquals("CompareTo should return 0", 0, firstInstance.compareTo(secondInstance));
  }

  private class ComparableDelayedStub extends ComparableDelayed {
    private int hashCode;

    public int hashCode() {
      return hashCode;
    }
  }
}
