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

import com.openpojo.reflection.java.Java;
import org.objectweb.asm.ClassReader;

/**
 * @author oshoukry
 */
public class DefaultSubClassDefinition implements SubClassDefinition {
    private final String generatedClassName;
    private final ClassReader classReader;

    public DefaultSubClassDefinition(Class<?> parentClass) {
        this(parentClass, parentClass.getName() + "__Generated_OpenPojo");
    }

    public DefaultSubClassDefinition(Class<?> parentClass, String subClassName) {
        this.generatedClassName = subClassName;
        this.classReader = ClassReaderFactory.getClassReader(parentClass);
    }

    public ClassReader getClassReader() {
        return classReader;
    }

    public String getGeneratedClassNameAsJDKPath() {
        return generatedClassName.replace(Java.PACKAGE_DELIMITER, Java.PATH_DELIMITER);
    }

    public String getGeneratedClassName() {
        return generatedClassName;
    }
}
