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

package com.openpojo.reflection.java.bytecode.sample;

import java.util.List;

/**
 * @author oshoukry
 */
public abstract class ACompleteAbstractClass {
  private String name;
  private Integer age;
  private List<ACompleteOffspringClass> myChildren;

  public ACompleteAbstractClass(String name, Integer age, List<ACompleteOffspringClass> myChildren) {
    this.name = name;
    this.age = age;
    this.myChildren = myChildren;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public List<ACompleteOffspringClass> getMyChildren() {
    return myChildren;
  }

  public void setMyChildren(List<ACompleteOffspringClass> myChildren) {
    this.myChildren = myChildren;
  }

  public abstract void rewardAChild(ACompleteOffspringClass toddsOffspring);

  public abstract static class ACompleteOffspringClass extends ACompleteAbstractClass {

    public ACompleteOffspringClass(String name, Integer age, List<ACompleteOffspringClass> myChildren) {
      super(name, age, myChildren);
    }
  }
}
