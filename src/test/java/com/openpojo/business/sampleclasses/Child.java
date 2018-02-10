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

package com.openpojo.business.sampleclasses;

import com.openpojo.business.annotation.BusinessKey;

/**
 * @author oshoukry
 */
public class Child extends Parent {

  @BusinessKey
  private String firstName;

  public Child() {

  }

  /**
   * @param firstName
   */
  public Child(final String firstName) {
    this.firstName = firstName;
  }

  /**
   * @param firstName
   */
  public Child(final String firstName, final String lastName, final Character sex) {
    super(lastName);
    this.firstName = firstName;
    setSex(sex);
  }


  /**
   * @return the firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @param firstName
   *     the firstName to set
   */
  public void setFirstName(final String firstName) {
    this.firstName = firstName;
  }
}
