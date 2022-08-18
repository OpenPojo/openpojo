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

package com.openpojo.issues.issue26.pojo;

import com.openpojo.business.BusinessIdentity;
import com.openpojo.business.annotation.BusinessKey;

public class SomeParentEntity {
  private Long id;

  @BusinessKey
  private String name;

  @BusinessKey
  private SomeChildEntity child;

  @SuppressWarnings("unused")
  private SomeParentEntity() {

  }

  public SomeParentEntity(final Long id, final String name, final SomeChildEntity child) {
    this.id = id;
    this.name = name;
    this.child = child;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public SomeChildEntity getChild() {
    return child;
  }

  public void setChild(final SomeChildEntity child) {
    this.child = child;
  }

  @Override
  public boolean equals(final Object o) {
    return BusinessIdentity.areEqual(this, o);
  }

  @Override
  public int hashCode() {
    return BusinessIdentity.getHashCode(this);
  }
}
