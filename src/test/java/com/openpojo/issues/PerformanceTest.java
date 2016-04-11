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

package com.openpojo.issues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.openpojo.business.BusinessIdentity;
import com.openpojo.business.annotation.BusinessKey;
import org.junit.Test;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class PerformanceTest {

  private final int max = 1000 * 1000;
  static private final Map stringMap2IntegerList = new HashMap();

  static {

    for (int i = 0; i < 100; i++) {
      List list = new ArrayList(100);
      for (int j = 0; j < 100; j++) {
        list.add(100);
      }
      stringMap2IntegerList.put(i + "", list);
    }
  }

  @Test
  public void testNewIdentity() {
    for (int i = 0; i < max; i++) {
      NewIdentity o1 = new NewIdentity("", stringMap2IntegerList);
      NewIdentity o2 = new NewIdentity("", stringMap2IntegerList);
      o1.equals(o2);
    }
  }

  @Test
  public void testOldIdentity() {
    for (int i = 0; i < max; i++) {
      OldIdentity o1 = new OldIdentity("", stringMap2IntegerList);
      OldIdentity o2 = new OldIdentity("", stringMap2IntegerList);
      o1.equals(o2);
    }
  }
}

@SuppressWarnings("rawtypes")
class OldIdentity {

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((stringMap2IntegerList == null) ? 0 : stringMap2IntegerList.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    OldIdentity other = (OldIdentity) obj;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (stringMap2IntegerList == null) {
      if (other.stringMap2IntegerList != null)
        return false;
    } else if (!stringMap2IntegerList.equals(other.stringMap2IntegerList))
      return false;
    return true;
  }

  @BusinessKey
  private final String name;

  @BusinessKey
  private final Map stringMap2IntegerList;

  public OldIdentity(final String name, final Map stringMap2IntegerList) {
    this.name = name;
    this.stringMap2IntegerList = stringMap2IntegerList;
  }

}

@SuppressWarnings("rawtypes")
class NewIdentity extends OldIdentity {

  public NewIdentity(final String name, final Map stringMap2IntegerList) {
    super(name, stringMap2IntegerList);
  }

  @Override
  public boolean equals(final Object obj) {
    return BusinessIdentity.areEqual(this, obj);
  }

  @Override
  public int hashCode() {
    return BusinessIdentity.getHashCode(this);
  }
}
