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

package com.openpojo.business.identity.impl.sampleclasses;

import com.openpojo.business.BusinessIdentity;
import com.openpojo.business.annotation.BusinessKey;

/**
 * @author oshoukry
 */
public class StringArrayCaseInsensitive {
  @BusinessKey(caseSensitive = false)
  private String[] fullNameInParts;

  public StringArrayCaseInsensitive(final String[] fullNameInParts) {
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
