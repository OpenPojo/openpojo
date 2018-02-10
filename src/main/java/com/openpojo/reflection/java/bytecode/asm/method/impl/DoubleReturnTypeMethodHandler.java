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

import com.openpojo.reflection.java.Java;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * @author oshoukry
 */
public class DoubleReturnTypeMethodHandler extends AbstractReturnTypeMethodHandler {
  private static final Type HANDLER_TYPE = Type.getObjectType(Double.class.getName());
  private static final int OPCODE = Opcodes.DRETURN;
  private static final String asPrimitiveMethod = "doubleValue";
  private static final String RETURN_DESCRIPTION = "()D";
  private static final String CLASS_PATH= HANDLER_TYPE.getClassName().replace(Java.PACKAGE_DELIMITER, Java.PATH_DELIMITER);

  protected String getInternalName() {
    return CLASS_PATH;
  }

  protected String getAsPrimitiveMethod() {
    return  asPrimitiveMethod;
  }

  protected String getReturnDescription() {
    return RETURN_DESCRIPTION;
  }

  protected int getOpCode() {
    return OPCODE;
  }
}
