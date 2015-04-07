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

package com.openpojo.reflection.java.bytecode;

import java.lang.reflect.Modifier;

import com.openpojo.log.Logger;
import com.openpojo.log.LoggerFactory;
import com.openpojo.reflection.java.bytecode.asm.ASMDetector;
import com.openpojo.reflection.java.bytecode.asm.ASMNotLoadedException;
import com.openpojo.reflection.java.bytecode.asm.ASMService;

/**
 * This factory is to be used to generate a subclass for a given PojoClass.
 *
 * @author oshoukry
 */
public class ByteCodeFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(ByteCodeFactory.class);
    private static boolean asm_enabled = ASMDetector.getInstance().isASMLoaded();
    private static ASMService asmService = ASMService.getInstance();

    private ByteCodeFactory() {
        throw new IllegalStateException(ByteCodeFactory.class.getName() + " should not be constructed!");
    }

    public static <T> Class<? extends T> getSubClass(Class<T> clazz) {
        if (isNull(clazz) || isAnInterface(clazz) || isAnEnum(clazz) || isPrimitive(clazz) || isAnArray(clazz) || isFinal(clazz)) {
            LOGGER.error("Invalid request to generate a subclass for [{0}], argument must be [not null, not an interface, not an enum, " +
                            "not primitive, not an array or a final class",
                    clazz);
            return null;
        }

        if (!asm_enabled)
            throw ASMNotLoadedException.getInstance();

        LOGGER.info("Generating subclass for class [{0}]", clazz);
        return asmService.createSubclassFor(clazz);
    }

    private static <T> boolean isNull(Class<T> clazz) {
        return clazz == null;
    }

    private static <T> boolean isAnInterface(Class<T> clazz) {
        return Modifier.isInterface(clazz.getModifiers());
    }

    private static <T> boolean isAnEnum(Class<T> clazz) {
        return clazz.isEnum();
    }

    private static <T> boolean isPrimitive(Class<T> clazz) {
        return clazz.isPrimitive();
    }

    private static <T> boolean isAnArray(Class<T> clazz) {
        return clazz.isArray();
    }

    private static <T> boolean isFinal(Class<T> clazz) {
        return Modifier.isFinal(clazz.getModifiers());
    }
}
