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

import java.util.ArrayList;
import java.util.List;

import com.openpojo.reflection.java.Java;
import com.openpojo.reflection.java.bytecode.asm.method.MethodHandler;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.RETURN;

/**
 * @author oshoukry
 */
public class InitMethodHandler implements MethodHandler {
  public void generateMethod(MethodVisitor methodVisitor,
                             String abstractClassName,
                             String generatedClassName,
                             int access,
                             String name,
                             String desc,
                             String signature,
                             String[] exceptions) {

    prepareParametersToPassToSuper(desc, methodVisitor);

    methodVisitor.visitMethodInsn(INVOKESPECIAL,
        abstractClassName.replace(Java.PACKAGE_DELIMITER, Java.PATH_DELIMITER),
        "<init>",
        desc,
        false);
    methodVisitor.visitInsn(RETURN);
    methodVisitor.visitMaxs(0, 0);
    methodVisitor.visitEnd();
  }

  private void prepareParametersToPassToSuper(String description, MethodVisitor methodVisitor) {

    int offset = 0;

    methodVisitor.visitCode();
    methodVisitor.visitVarInsn(Opcodes.ALOAD, offset++);

    List<Integer> opCodes = getOpCodes(description);

    for (int idx = 0; idx < opCodes.size(); idx++) {
      final Integer opcode = opCodes.get(idx);
      methodVisitor.visitVarInsn(opcode, idx + offset);
      if (opcode == Opcodes.DLOAD || opcode == Opcodes.LLOAD) // Double and Long take two registers.
        offset++;
    }

  }

  private String getDescWithoutReturnOrBrackets(String desc) {
    return desc.substring(desc.indexOf('(') + 1).substring(0, desc.indexOf(")"));
  }

  private List<Integer> getOpCodes(String description) {
    List<Integer> opcodes = new ArrayList<Integer>();
    String desc = getDescWithoutReturnOrBrackets(description);

    for (int idx = 0; idx < desc.length(); idx++)
      switch (desc.charAt(idx)) {
        case 'Z':
        case 'B':
        case 'C':
        case 'S':
        case 'I':
          opcodes.add(Opcodes.ILOAD);
          break;
        case 'J':
          opcodes.add(Opcodes.LLOAD);
          break;
        case 'F':
          opcodes.add(Opcodes.FLOAD);
          break;
        case 'L': // advance to the next object
          opcodes.add(Opcodes.ALOAD);
          while (desc.charAt(idx) != ';')
            idx++;
          break;
        case 'D':
          opcodes.add(Opcodes.DLOAD);
          break;
        case '[':
          opcodes.add(Opcodes.ALOAD);
          if (desc.charAt(idx + 1) == 'L')
            while (desc.charAt(idx) != ';')
              idx++;
          else
            idx++;
          break;
        default:
          break;
      }

    return opcodes;
  }
}
