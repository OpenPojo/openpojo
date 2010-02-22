/**
 * 2010 Copyright Osman Shoukry.
 */
package com.openpojo.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.openpojo.PojoField;
import com.openpojo.exception.ReflectionException;


/**
 * This is the default implementation for PojoField Interface.
 * This is an immutable object and is not supposed to be created directly.
 * see {@link PojoClassFactory}
 *
 * @author oshoukry
 */
public class PojoFieldImpl implements PojoField {

    private final Field field;
    private final Method fieldGetter;
    private final Method fieldSetter;

    public PojoFieldImpl(final Field field, final Method fieldGetter, final Method fieldSetter) {
        this.field = field;
        this.field.setAccessible(true);
        this.fieldGetter = fieldGetter;
        this.fieldSetter = fieldSetter;
    }

    public Object get(final Object instance) {
        try {
            return field.get(instance);
        } catch (Exception e) {
            throw new ReflectionException(e);
        }
    }

    public String getName() {
        return field.getName();
    }

    public void set(final Object instance, final Object value) {
        try {
            field.set(instance, value);
        } catch (Exception e) {
            throw new ReflectionException(e);
        }
    }

    public boolean hasGetter() {
        return fieldGetter != null;
    }

    public Object invokeGetter(final Object instance) {
        try {
            return fieldGetter.invoke(instance);
        } catch (Exception e) {
            throw new ReflectionException(e);
        }
    }

    public boolean hasSetter() {
        return fieldSetter != null;
    }

    public void inovkeSetter(final Object instance, final Object value) {
        try {
            fieldSetter.invoke(instance, value);
        } catch (Exception e) {
            throw new ReflectionException(e);
        }
    }

    public Class<?> getType() {
        return field.getType();
    }

    public <T extends Annotation> T getAnnotation(final Class<T> annotationClass) {
        return field.getAnnotation(annotationClass);
    }

    public boolean isFinal() {
        return Modifier.isFinal(field.getModifiers());
    }

    public boolean isStatic() {
        return Modifier.isStatic(field.getModifiers());
    }

    public boolean isPrivate() {
        return Modifier.isPrivate(field.getModifiers());
    }

    public boolean isProtected() {
        return Modifier.isProtected(field.getModifiers());
    }

    public boolean isPublic() {
        return Modifier.isPublic(field.getModifiers());
    }

    public boolean areEqual(final Object first, final Object second) {
        if (get(first) == null) {
            return get(second) == null;
        }

        return get(first).equals(get(second));
    }

    @Override
    public String toString() {
        return String.format("PojoFieldImpl [field=%s, fieldGetter=%s, fieldSetter=%s]", field, fieldGetter,
                fieldSetter);
    }
}
