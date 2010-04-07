/**
 * Copyright (C) 2010 Osman Shoukry
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.openpojo.business.identity;

import com.openpojo.business.identity.impl.DefaultBusinessValidator;
import com.openpojo.business.identity.impl.DefaultEvaluator;
import com.openpojo.business.identity.impl.DefaultHashCodeGenerator;

/**
 * This is the Default factory that holds the default implementation of
 * {@link IdentityEvaluator}, {@link HashCodeGenerator} and {@link BusinessValidator}.
 * <br>
 * This IdentityFactory can be reconfigured with any other implementation using the "set" methods.
 *
 * @author oshoukry
 */
public final class IdentityFactory {

    private static IdentityEvaluator identityEvaluator;
    private static HashCodeGenerator hashCodeGenerator;
    private static BusinessValidator businessValidator;

    static {
        // Initialize the Factory with default implementations.
        IdentityFactory.setBusinessValidator(new DefaultBusinessValidator());
        IdentityFactory.setHashCodeGenerator(new DefaultHashCodeGenerator());
        IdentityFactory.setIdentityEvaluator(new DefaultEvaluator());
    }
    /**
     * @return the identityEvaluator
     */
    public static IdentityEvaluator getIdentityEvaluator() {
        return identityEvaluator;
    }

    /**
     * @param identityEvaluator
     *            the identityEvaluator to set
     */
    public static void setIdentityEvaluator(final IdentityEvaluator identityEvaluator) {
        IdentityFactory.identityEvaluator = identityEvaluator;
    }

    /**
     * @return the hashCodeGenerator
     */
    public static HashCodeGenerator getHashCodeGenerator() {
        return hashCodeGenerator;
    }

    /**
     * @param hashCodeGenerator
     *            the hashCodeGenerator to set
     */
    public static void setHashCodeGenerator(final HashCodeGenerator hashCodeGenerator) {
        IdentityFactory.hashCodeGenerator = hashCodeGenerator;
    }

    /**
     * @return the businessValidator
     */
    public static BusinessValidator getBusinessValidator() {
        return businessValidator;
    }

    /**
     * @param businessValidator
     *            the businessValidator to set
     */
    public static void setBusinessValidator(final BusinessValidator businessValidator) {
        IdentityFactory.businessValidator = businessValidator;
    }
}
