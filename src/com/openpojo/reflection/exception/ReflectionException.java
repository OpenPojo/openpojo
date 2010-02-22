/**
 * 2010 Copyright Osman Shoukry.
 */
package com.openpojo.reflection.exception;

/**
 * 
 * @author oshoukry
 */
public class ReflectionException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    /**
     * Reflection Exception with message constructor
     * @param message
     *          The description of the exception.
     */
    public ReflectionException(String message) {
        super(message);
    }
    
    /**
     * Reflection Exception with throwable constructor.
     * @param cause
     *          The root cause of this reflection exception.
     */
    public ReflectionException(Throwable cause) {
        super(cause);
    }

    /**
     * Reflection Exception with message & throwable constructor
     * @param message
     *          The description of the exception.
     * @param cause
     *          The root cause of this reflection exception.
     */
    public ReflectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
