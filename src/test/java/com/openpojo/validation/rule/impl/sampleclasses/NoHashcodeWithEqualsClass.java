package com.openpojo.validation.rule.impl.sampleclasses;

public class NoHashcodeWithEqualsClass {

    // equals without hashCode : not OK

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

}
