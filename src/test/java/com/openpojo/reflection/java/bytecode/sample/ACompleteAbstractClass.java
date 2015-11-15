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
