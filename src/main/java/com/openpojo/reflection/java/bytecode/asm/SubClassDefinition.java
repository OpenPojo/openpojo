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
class SubClassDefinition {
    private final String generatedClassName;
    private final ClassReader classReader;

    public SubClassDefinition(Class<?> clazz) {
        this.generatedClassName = clazz.getName() + "__Generated_OpenPojo";
        try {
            this.classReader = new ClassReader(clazz.getResourceAsStream(Java.PATH_DELIMETER
                    + clazz.getName().replace(Java.PACKAGE_DELIMETER, Java.PATH_DELIMETER) + Java.CLASS_EXTENSION));
        } catch (Throwable t) {
            throw ReflectionException.getInstance("Failed to create ClassReader for class [" + clazz + "]", t);
        }
    }

    public ClassReader getClassReader() {
        return classReader;
    }

    public String getGeneratedClassNameAsJDKPath() {
        return generatedClassName.replace(Java.PACKAGE_DELIMETER, Java.PATH_DELIMETER);
    }

    public String getGeneratedClassName() {
        return generatedClassName;
    }
}
