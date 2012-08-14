package com.openpojo.issues.genericconstructor.sample;

import java.util.EnumSet;
import java.util.Set;

import com.openpojo.issues.genericconstructor.sample.support.DaysOfTheWeek;

public class ClassWithGenericSetEnumConstructor {
    @SuppressWarnings("unused")
    private final Set<DaysOfTheWeek> daysOfTheWeek;

    public ClassWithGenericSetEnumConstructor(final Set<DaysOfTheWeek> daysOfTheWeekSet) {
        daysOfTheWeek = EnumSet.copyOf(daysOfTheWeekSet);
    }
}
