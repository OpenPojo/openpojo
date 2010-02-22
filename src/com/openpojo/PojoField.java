package com.openpojo;

import java.lang.annotation.Annotation;

public interface PojoField {

    /**
     * This method gets the value of the field.
     * 
     * @param instance
     *            The instance to extract the value out of.
     * @return
     *         The value of the field.
     */
    public Object get(final Object instance);

    /**
     * Get the String representation of the field name.
     * 
     * @return
     *         Return the name of the field.
     */
    public String getName();

    /**
     * This method sets the value of the field.
     * 
     * @param instance
     *            The instance to set the value on.
     * @param value
     *            The value to set it to.
     */
    public void set(final Object instance, final Object value);

    /**
     * Returns true if this field has a getter method.
     * 
     * @return
     *         Returns true if the getter is set.
     */
    public boolean hasGetter();

    /**
     * This method will invoke the getter method.
     * 
     * @param instance
     *            The instance of the class to invoke the getter on.
     * @return
     *         The value of the field.
     */
    public Object invokeGetter(final Object instance);

    /**
     * @return
     */
    public boolean hasSetter();

    public void inovkeSetter(final Object instance, final Object value);

    public Class<?> getType();

    public <T extends Annotation> T getAnnotation(final Class<T> annotationClass);

    public boolean isFinal();

    public abstract boolean isStatic();

    public abstract boolean isPrivate();

    public abstract boolean isProtected();

    public abstract boolean isPublic();

    public abstract boolean areEqual(final Object first, final Object second);

}