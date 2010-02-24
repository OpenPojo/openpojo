package com.openpojo.validation.utils;

import com.openpojo.reflection.PojoField;

/**
 * This Validation helper utility class will carry the collection of common
 * tasks performed by various validation tasks.
 * 
 * @author oshoukry
 */
public final class ValidationHelper {
    /**
     * Return true if the PojoField is marked as static and is final.
     * @param fieldEntry
     *          The field to test.
     * @return
     *          True if the field was declared static final, false otherwise.
     */
    public static boolean isStaticFinal(PojoField fieldEntry) {
        return fieldEntry.isFinal() && fieldEntry.isStatic();
    }

}
