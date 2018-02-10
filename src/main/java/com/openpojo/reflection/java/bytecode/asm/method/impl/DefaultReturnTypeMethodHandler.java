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

package com.openpojo.reflection.java.bytecode.asm.method.impl;

import com.openpojo.reflection.java.bytecode.asm.method.MethodHandler;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import static org.objectweb.asm.Opcodes.ARETURN;
import static org.objectweb.asm.Opcodes.CHECKCAST;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;

/**
 * @author oshoukry
 */
public class DefaultReturnTypeMethodHandler implements MethodHandler {

  public void generateMethod(MethodVisitor methodVisitor,
                             String abstractClassName,
                             String generatedClassName,
                             int access,
                             String name,
                             String desc,
                             String signature,
                             String[] exceptions) {
    Type returnType = Type.getReturnType(desc);

    methodVisitor.visitLdcInsn(returnType);

    methodVisitor.visitMethodInsn(INVOKESTATIC,
        "com/openpojo/random/RandomFactory",
        "getRandomValue",
        "(Ljava/lang/Class;)Ljava/lang/Object;",
        false);

    String replace = returnType.getInternalName();
    methodVisitor.visitTypeInsn(CHECKCAST, replace);
    methodVisitor.visitInsn(ARETURN);
    methodVisitor.visitMaxs(0, 0);
    methodVisitor.visitEnd();
  }
}
