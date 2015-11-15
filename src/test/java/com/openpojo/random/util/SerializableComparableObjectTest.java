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

package com.openpojo.random.util;

import com.openpojo.validation.affirm.Affirm;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class SerializableComparableObjectTest {

  @Test
  public void shouldWireCompareToOverHashCodeValue() {
    SerializableComparableObject firstInstance = new SerializableComparableObject();
    SerializableComparableObjectStub secondInstance = new SerializableComparableObjectStub();

    secondInstance.hashCode = firstInstance.hashCode() - 1;
    Affirm.affirmEquals("CompareTo should return 1", 1, firstInstance.compareTo(secondInstance));

    secondInstance.hashCode = firstInstance.hashCode() + 1;
    Affirm.affirmEquals("CompareTo should return -1", -1, firstInstance.compareTo(secondInstance));

    secondInstance.hashCode = firstInstance.hashCode();
    Affirm.affirmEquals("CompareTo should return 0", 0, firstInstance.compareTo(secondInstance));
  }

  private class SerializableComparableObjectStub extends SerializableComparableObject {
    private int hashCode;

    public int hashCode() {
      return hashCode;
    }
  }
}
