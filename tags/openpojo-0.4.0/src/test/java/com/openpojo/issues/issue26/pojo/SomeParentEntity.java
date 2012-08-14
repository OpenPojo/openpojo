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
