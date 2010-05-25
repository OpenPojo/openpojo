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
package com.openpojo.reflection.facade;

import java.util.Arrays;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.impl.PojoClassFactory;

/**
 * @author oshoukry
 */
public final class FacadeFactory {
    public static PojoClass getLoadedFacadePojoClass(final String[] facadeNames) {
        for (String facadeName : facadeNames) {
            try {
                return PojoClassFactory.getPojoClass(Class.forName(facadeName));
            } catch (Exception e) {
                // this class not found, lets try the next one in the list.
            }
        }
        throw ReflectionException.getInstance(String.format("Unable to find suitable implementation among [%s]", Arrays
                .toString(facadeNames)));
    }
}
