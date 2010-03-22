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

package com.openpojo.business;

import com.openpojo.business.identity.IdentityFactory;
import com.openpojo.reflection.impl.PojoClassFactory;

/**
 * @author oshoukry
 *
 */
public final class BusinessIdentity {
    public static boolean areEqual(Object first, Object second) {
        IdentityFactory.getBusinessValidator().validate(first);
        IdentityFactory.getBusinessValidator().validate(second);
        return IdentityFactory.getIdentityEvaluator().areEqual(first, second);
    }
    
    public static int getHashCode(Object object) {
        IdentityFactory.getBusinessValidator().validate(object);
        return IdentityFactory.getHashCodeGenerator().doGenerate(object);        
    }
    
    public static String toString(Object instance) {
        return PojoClassFactory.getPojoClass(instance.getClass()).toString(instance);
    }
}
