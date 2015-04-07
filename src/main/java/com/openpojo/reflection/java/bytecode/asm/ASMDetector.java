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

import com.openpojo.reflection.java.load.ClassUtil;

/**
 * @author oshoukry
 */
public class ASMDetector {
    public String ASM_CLASS_NAME = "org.objectweb.asm.ClassWriter";

    private ASMDetector() {
    }

    public static ASMDetector getInstance() {
        return Instance.INSTANCE;
    }

    public boolean isASMLoaded() {
        return ClassUtil.isClassLoaded(ASM_CLASS_NAME);
    }

    private static class Instance {
        private static final ASMDetector INSTANCE = new ASMDetector();
    }
}
