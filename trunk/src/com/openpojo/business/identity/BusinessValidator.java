/**
 * 2010 Copyright Osman Shoukry.
 */
package com.openpojo.business.identity;

/**
 * @author oshoukry
 *
 */
public interface BusinessValidator {
    /**
     * This method validates an object to comply with the BusinessKey annotation rules.
     * If an object fails the validation, a BusinessException will be thrown.
     * @param object
     *          The Business Object to be validated.
     * @return
     *          Returns True
     */
    public void validate(Object object);
}
