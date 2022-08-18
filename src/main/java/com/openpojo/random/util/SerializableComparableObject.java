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

import java.io.Serializable;

/**
 * @author oshoukry
 */
public class SerializableComparableObject implements Serializable, Comparable {
  private static final long serialVersionUID = 1L;

  @SuppressWarnings("NullableProblems")
  public int compareTo(Object other) {
    if (other == null || this.hashCode() > other.hashCode())
      return 1;
    if (this.hashCode() == other.hashCode())
      return 0;
    return -1;
  }

  @Override
  public boolean equals(Object o) {
    return !(o == null || getClass() != o.getClass()) && this.hashCode() == o.hashCode();
  }

  @Override
  public int hashCode() {
    return System.identityHashCode(this);
  }
}
