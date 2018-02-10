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

package com.openpojo.random.loop;

import java.io.Serializable;

public final class Employee implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Minimal business constructor.
   */
  public Employee(final String fullName, final Employee manager) {
    this.fullName = fullName;
    this.manager = manager;
  }

  private String fullName;

  /**
   * This is an Object Loop, to demonstrate how the random factory would handle it.
   */
  private Employee manager;

  /**
   * @return the firstname
   */
  public String getFirstname() {
    return fullName;
  }

  /**
   * @param firstname
   *     the firstname to set
   */
  public void setFirstname(final String firstname) {
    this.fullName = firstname;
  }

  /**
   * @param manager
   *     the manager to set.
   */
  public void setManager(final Employee manager) {
    this.manager = manager;
  }

  /**
   * @return the manager of this person.
   */
  public Employee getManager() {
    return manager;
  }

  @Override
  public String toString() {
    return String.format("Employee [fullName=%s, manager=%s]", fullName, manager);
  }

}
