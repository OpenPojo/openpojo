/*
 * Copyright (c) 2010-2016 Osman Shoukry
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
  private boolean hasToString = false;

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
        mv.visitMethodInsn(INVOKESPECIAL, className.replace(Java.PACKAGE_DELIMITER, Java.PATH_DELIMITER), "<init>", desc, false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(counter + 1, counter + 1);
        mv.visitEnd();
      }
    }

    if (name.equals("toString") && (access & ACC_ABSTRACT) == 0) {
      hasToString = true;
    }

    return super.visitMethod(access, name, desc, signature, exceptions);
  }

  /**
   * If the class has an abstract toString method, we replace it with a concrete implementation.
   */
  @Override
  public void visitEnd() {
    if (!hasToString) {
      LOGGER.debug("Adding concrete toString implementation");
      MethodVisitor mv;
      mv = cw.visitMethod(ACC_PUBLIC, "toString", "()Ljava/lang/String;", null, null);
      mv.visitCode();
      mv.visitVarInsn(ALOAD, 0);
      mv.visitLdcInsn(className + " synthetic concrete subclass");
      mv.visitInsn(ARETURN);
      mv.visitMaxs(2, 2);
      mv.visitEnd();
    }
    super.visitEnd();
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
