/*
 * Copyright (c) 2010-2014 Osman Shoukry
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
