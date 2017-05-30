package com.openpojo.validation.rule.impl.sampleclasses;

public class NoEqualsWithHashcodeClass {

    // hashCode without equals : not OK

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
