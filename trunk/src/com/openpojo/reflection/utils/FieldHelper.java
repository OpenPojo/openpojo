package com.openpojo.reflection.utils;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * This class is a utility class to aid with getting all declared fields for a given class.
 * @author oshoukry
 *
 */
public class FieldHelper {
    public static List<Field> getDeclaredFields(Class<?> clazz) {
        List<Field> fields = new LinkedList<Field>();
        for (Field field : clazz.getDeclaredFields()) {
            fields.add(field);
        }
        return Collections.unmodifiableList(fields);
    }
}
