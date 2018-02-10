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

package com.openpojo.reflection.impl.sample.classes;

/**
 * @author oshoukry
 */
public class AClassWithEquality {

  private String name;
  private Integer age;
  @SuppressWarnings({ "unused", "FieldCanBeLocal" })
  private Boolean noGetterBoolean;
  public Boolean noSetterBoolean;

  public AClassWithEquality() {

  }

  public AClassWithEquality(final String name, final Integer age) {
    this.name = name;
    this.age = age;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(final Integer age) {
    this.age = age;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    AClassWithEquality other = (AClassWithEquality) obj;
    if (age == null) {
      if (other.age != null) {
        return false;
      }
    } else if (!age.equals(other.age)) {
      return false;
    }
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }
    return true;
  }

  public Boolean getNoSetterBoolean() {
    return noSetterBoolean;
  }

  public void setNoGetterBoolean(final Boolean noGetterBoolean) {
    this.noGetterBoolean = noGetterBoolean;
  }

}