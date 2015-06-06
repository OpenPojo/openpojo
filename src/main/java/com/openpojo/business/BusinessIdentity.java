/*
 * Copyright (c) 2010-2015 Osman Shoukry
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License or any
 *    later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.openpojo.business;

import com.openpojo.business.identity.IdentityFactory;
import com.openpojo.business.utils.BusinessIdentityUtils;
import com.openpojo.reflection.impl.PojoClassFactory;

/**
 * This is the entry point and the main class to use for all your business evaluation, hashCode generation,
 * as well as toString rendering.
 *
 * <br>
 * This class was created to simplify the entry into the business package and is ready for use without writing any code.
 * Simply configure your POJOs with {@link com.openpojo.business.annotation.BusinessKey} annotation, and then delegate your equals and hashCode methods.
 * <br>
 * The toString method can be called even if your POJOs aren't configured with a BusinessKey, it is simply an easy way
 * to print in clean normalized way ALL contents of a Java Class.
 * <br>
 *
 * <strong>Note:</strong><br>
 * All calls to areEqual &amp; getHashCode will result in a call to {@link com.openpojo.business.identity.BusinessValidator}.validate
 * (Object).<br>
 * A {@link com.openpojo.business.exception.BusinessException} will be thrown if the objects don't pass business validation.
 *
 * @author oshoukry
 *
 */
public final class BusinessIdentity {

    /**
     * This method is responsibly for handling equality between two objects.
     * @param first
     *          The first object to compare with.
     * @param second
     *          The second object to compare with.
     * @return
     *          True if both objects are equal, false otherwise.  if either of those objects is null, equality is false.
     */
    public static boolean areEqual(final Object first, final Object second) {
    	if (BusinessIdentityUtils.anyNull(first, second) || !BusinessIdentityUtils.sameClass(first, second)) {
             return false;
         }

        IdentityFactory.getIdentityHandler(first).validate(first);
        IdentityFactory.getIdentityHandler(second).validate(second);
        return IdentityFactory.getIdentityHandler(first).areEqual(first, second);
    }

    /**
     * This method handles generation of the hashCode for a given object.
     * @param object
     *          Object to generate hashCode for.
     * @return
     *          Generated hash code.
     */
    public static int getHashCode(final Object object) {
        IdentityFactory.getIdentityHandler(object).validate(object);
        return IdentityFactory.getIdentityHandler(object).generateHashCode(object);
    }

    public static String toString(final Object instance) {
        if (instance == null)
            return "null";
        return PojoClassFactory.getPojoClass(instance.getClass()).toString(instance);
    }
}
