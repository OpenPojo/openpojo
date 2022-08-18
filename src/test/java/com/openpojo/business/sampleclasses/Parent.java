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
class Parent extends Human {

  @BusinessKey
  private String lastName;

  public Parent() {
  }

  /**
   * @param lastName
   */
  public Parent(final String lastName) {
    this.lastName = lastName;
  }

  /**
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * @param lastName
   *     the lastName to set
   */
  public void setLastName(final String lastName) {
    this.lastName = lastName;
  }

}
