package com.openpojo.validation.test.impl.sampleclasses;

import java.util.concurrent.ThreadLocalRandom;

public class BadHashCodeImplementationClass {

    @Override
    public int hashCode() {
        return ThreadLocalRandom.current().nextInt();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
