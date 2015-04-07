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

import java.util.Arrays;

import com.openpojo.log.Logger;
import com.openpojo.log.LoggerFactory;
import com.openpojo.reflection.java.Java;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

/**
 * @author oshoukry
 */
class SubClassCreator extends ClassVisitor {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubClassCreator.class);
    private final ClassWriter cw;
    private String className;
    private final String generateWithName;

    public SubClassCreator(ClassWriter cw, String generateWithName) {
        super(ASM5);
        this.cw = cw;
        this.generateWithName = generateWithName;
    }

    /**
     * Called when a class is visited. This is the method called first
     */
    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        LOGGER.debug("Visiting class: " + name);
        this.className = name;
        LOGGER.debug("Class Major Version: " + version);
        cw.visit(version, ACC_PUBLIC + ACC_SUPER, generateWithName, null, name, null);

        LOGGER.debug("Super class: " + superName);
        LOGGER.debug("Generating new Class with name:" + generateWithName);
        super.visit(version, access, name, signature, superName, interfaces);
    }

    /**
     * This method will copy over the constructors to the subclass.
     */
    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        LOGGER.debug("Method: " + name + " access:" + access + " desc:" + desc + " signature:" + signature + " exceptions:" +
                Arrays.toString(exceptions));
        if (name.equals("<init>") && access == ACC_PUBLIC || access == ACC_PROTECTED) {
            MethodVisitor mv = cw.visitMethod(access, name, desc, signature, exceptions);
            int counter = getParameterCount(desc);
            // wire constructor method to super
            {
                mv.visitCode();
                for (int idx = 0; idx <= counter; idx++) {
                    mv.visitVarInsn(ALOAD, idx);
                }
                mv.visitMethodInsn(INVOKESPECIAL, className.replace(Java.PACKAGE_DELIMETER, Java.PATH_DELIMETER),
                        "<init>", desc, false);
                mv.visitInsn(RETURN);
                mv.visitMaxs(counter + 1, counter + 1);
                mv.visitEnd();
            }
        }

        return super.visitMethod(access, name, desc, signature, exceptions);
    }

    private int getParameterCount(String desc) {
        String parameterSeperator = ";";
        int parameterCount = 0;
        int idx = desc.indexOf(parameterSeperator, 0);

        while (idx >= 0) {
            parameterCount++;
            idx = desc.indexOf(parameterSeperator, idx + 1);
        }

        return parameterCount;
    }
}