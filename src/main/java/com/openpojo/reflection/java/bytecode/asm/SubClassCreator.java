/*
 * Copyright (c) 2010-2018 Osman Shoukry
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
import com.openpojo.reflection.java.bytecode.asm.method.MethodHandler;
import com.openpojo.reflection.java.bytecode.asm.method.MethodHandlerFactory;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import static org.objectweb.asm.Opcodes.*;

/**
 * @author oshoukry
 */
class SubClassCreator extends ClassVisitor {

  private static final Logger LOGGER = LoggerFactory.getLogger(SubClassCreator.class);
  private final ClassWriter cw;
  private String className;
  private final String generateWithName;

  SubClassCreator(ClassWriter cw, String generateWithName) {
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
    if (name.equals("<init>") && (access & (ACC_PUBLIC | ACC_PROTECTED)) != 0 || (access & ACC_ABSTRACT) != 0)
      handleMethod(access, name, desc, signature, exceptions);
    return super.visitMethod(access, name, desc, signature, exceptions);
  }

  private MethodVisitor handleMethod(int access, String name, String desc, String signature, String[] exceptions) {
    final MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, name, desc, signature, exceptions);
    String returnTypeClass = getReturnTypeClass(desc);
    MethodHandler handler = MethodHandlerFactory.getInstance().getHandler(name + desc, name, returnTypeClass);
    handler.generateMethod(mv, className, generateWithName, access, name, desc, signature, exceptions);
    return mv;
  }

  private String getReturnTypeClass(String desc) {
    Type type = Type.getReturnType(desc);
    return type.getClassName();
  }

}
