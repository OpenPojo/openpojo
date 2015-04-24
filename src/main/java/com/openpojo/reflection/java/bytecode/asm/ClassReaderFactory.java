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

package com.openpojo.reflection.java.bytecode.asm;

import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.java.Java;
import org.objectweb.asm.ClassReader;

/**
 * @author oshoukry
 */
public class ClassReaderFactory {

    private ClassReaderFactory() {
        throw new UnsupportedOperationException(ClassReaderFactory.class.getName() + " should not be constructed!");
    }

    public static ClassReader getClassReader(Class clazz) {
        try {
            return new ClassReader(clazz.getResourceAsStream(Java.PATH_DELIMITER
                    + clazz.getName().replace(Java.PACKAGE_DELIMITER, Java.PATH_DELIMITER) + Java.CLASS_EXTENSION));
        } catch (Throwable t) {
            throw ReflectionException.getInstance("Failed to create ClassReader for class [" + clazz + "]", t);
        }
    }

}
