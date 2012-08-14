package com.openpojo.issues.issue26.pojo;

import com.openpojo.business.BusinessIdentity;
import com.openpojo.business.annotation.BusinessKey;

public class SomeChildEntity {
    private Long id;

    @BusinessKey
    private String name;

    @SuppressWarnings("unused")
    private SomeChildEntity() {
    }

    public SomeChildEntity(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
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
