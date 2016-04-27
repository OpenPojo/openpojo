package com.openpojo.validation.test.impl.sampleclasses;

public class BadEqualsImplementation_InstanceEqualsToNullClass {

    boolean equals = false;

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return true;
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

}
