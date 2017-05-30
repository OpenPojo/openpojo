package com.openpojo.validation.test.impl.sampleclasses;

public class BadEqualsImplementation_IncorrectFieldUsageClass {

    boolean equals = false;

    @Override
    public boolean equals(Object obj) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

}
