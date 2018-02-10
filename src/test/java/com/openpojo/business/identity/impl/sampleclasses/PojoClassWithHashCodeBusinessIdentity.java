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

package com.openpojo.business.identity.impl.sampleclasses;

import com.openpojo.business.BusinessIdentity;
import com.openpojo.business.annotation.BusinessKey;

/*
 * @author oshoukry
 */
public class PojoClassWithHashCodeBusinessIdentity {

  @BusinessKey
  private String name;

  @BusinessKey
  private Integer age;

  public PojoClassWithHashCodeBusinessIdentity() {

  }

  public PojoClassWithHashCodeBusinessIdentity(String name, int age) {
    this.name = name;
    this.age = age;
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

  @Override
  public int hashCode() {
    return BusinessIdentity.getHashCode(this);
  }

  @Override
  public boolean equals(Object o) {
    return BusinessIdentity.areEqual(this, o);
  }
}
