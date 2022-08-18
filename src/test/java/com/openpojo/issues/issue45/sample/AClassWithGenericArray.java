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

package com.openpojo.issues.issue45.sample;

/**
 * @author oshoukry
 */
public class AClassWithGenericArray<T extends SomeGeneric> {
  private T[] someGenericArray;

  public T[] getSomeGenericArray() {
    return someGenericArray;
  }

  public void setSomeGenericArray(T[] someGenericArray) {
    if (someGenericArray == null)
      throw new IllegalArgumentException("Array must not be null");
    for (SomeGeneric entry : someGenericArray) {
      if (!entry.getClass().equals(SomeGeneric.class)) {
        throw new IllegalArgumentException("Expected entries of type [" + SomeGeneric.class.toString() + "] got ["
            + entry.getClass().toString() + "]");
      }
    }
    this.someGenericArray = someGenericArray;
  }

}
