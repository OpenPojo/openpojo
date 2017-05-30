package com.openpojo.validation.test.impl.sampleclasses;

public class BadEqualsImplementation_IncorrectInitialCheckClass {

    boolean equals = false;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        return 0;
    }

}
