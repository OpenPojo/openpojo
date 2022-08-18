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

package com.openpojo.issues.issue68.sample;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author oshoukry
 */
public class TestClass {
  private Map<String, SomeType[]> myMap;
  private Set<SomeType[]> mySet;
  private Collection<SomeType[]> myCollection;
  private List<SomeType[]> myList;

  public Map<String, SomeType[]> getMyMap() {
    return myMap;
  }

  public void setMyMap(Map<String, SomeType[]> myMap) {
    this.myMap = myMap;
  }

  public Set<SomeType[]> getMySet() {
    return mySet;
  }

  public void setMySet(Set<SomeType[]> mySet) {
    this.mySet = mySet;
  }

  public Collection<SomeType[]> getMyCollection() {
    return myCollection;
  }

  public void setMyCollection(Collection<SomeType[]> myCollection) {
    this.myCollection = myCollection;
  }

  public List<SomeType[]> getMyList() {
    return myList;
  }

  public void setMyList(List<SomeType[]> myList) {
    this.myList = myList;
  }
}
