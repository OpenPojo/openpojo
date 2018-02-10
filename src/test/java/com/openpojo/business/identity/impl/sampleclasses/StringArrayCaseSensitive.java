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

/**
 * @author oshoukry
 */
public class StringArrayCaseSensitive {
  @BusinessKey
  private String[] fullNameInParts;

  public StringArrayCaseSensitive(final String[] fullNameInParts) {
    this.fullNameInParts = fullNameInParts;
  }

  @Override
  public boolean equals(Object other) {
    return BusinessIdentity.areEqual(this, other);
  }

  @Override
  public int hashCode() {
    return BusinessIdentity.getHashCode(this);
  }
}
