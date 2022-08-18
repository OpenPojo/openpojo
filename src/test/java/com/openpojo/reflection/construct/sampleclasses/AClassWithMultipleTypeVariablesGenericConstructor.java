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

package com.openpojo.reflection.construct.sampleclasses;

/**
 * @author oshoukry
 */
public class AClassWithMultipleTypeVariablesGenericConstructor<K, V, T extends CharSequence> {
  private K myK;
  private V myV;
  private T myT;

  public AClassWithMultipleTypeVariablesGenericConstructor(final K k, final V v, final T t) {
    this.myK = k;
    this.myV = v;
    this.myT = t;
  }

  public K getMyK() {
    return myK;
  }

  public V getMyV() {
    return myV;
  }

  public T getMyT() {
    return myT;
  }
}
