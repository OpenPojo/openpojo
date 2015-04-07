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

package com.openpojo.reflection.facade;

import java.util.Arrays;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.reflection.java.load.ClassUtil;

/**
 * This factory is responsible for looking up usable implementations of facade when multiple are possible.
 *
 * @author oshoukry
 */
public final class FacadeFactory {
    /**
     * This method returns the proper loaded facade PojoClass.
     * This method will throw a runtime ReflectionException if non of the facades given are active.
     *
     * @param facadeNames
     *         The fully qualified class names of the possible facades.
     * @return A PojoClass wrapper around the correctly identified ClassName.
     */
    public static PojoClass getLoadedFacadePojoClass(final String[] facadeNames) {
        return PojoClassFactory.getPojoClass(getLoadedFacadeClass(facadeNames));
    }

    public static Class<?> getLoadedFacadeClass(final String[] facadeNames) {
        for (String facadeName : facadeNames) {
            Class clazz = ClassUtil.loadClass(facadeName);
            if (clazz != null)
                return clazz;
        }
        throw ReflectionException.getInstance(String.format("Unable to find suitable implementation among [%s]", Arrays.toString
                (facadeNames)));
    }
}
