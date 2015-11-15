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

package com.openpojo.reflection.impl.sampleclasses;

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