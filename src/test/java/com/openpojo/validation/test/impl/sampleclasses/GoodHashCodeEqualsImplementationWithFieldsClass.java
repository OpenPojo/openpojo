package com.openpojo.validation.test.impl.sampleclasses;

public class GoodHashCodeEqualsImplementationWithFieldsClass {

    private int fieldValue;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + fieldValue;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GoodHashCodeEqualsImplementationWithFieldsClass other = (GoodHashCodeEqualsImplementationWithFieldsClass) obj;
        if (fieldValue != other.fieldValue)
            return false;
        return true;
    }

}
