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

package com.openpojo.issues.issue84;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;
//@formatter:off
/**
 * Creates this class:
 *{@code
 *  package com.openpojo.issues.issue84;
 *  import sun.security.krb5.Credentials;
 *
 *  public class ClassWithCredentials {
 *    private Credentials credentials;
 *    public Credentials getCredentials() {
 *      return this.credentials;
 *    }
 *
 *    public void setCredentials(Credentials credentials) {
 *      this.credentials = credentials;
 *    }
 *  }
 *}
 * @author oshoukry
 */
 //@formatter:on
final class ClassWithCredentialsDumper {

  private final static String CLASS_PACKAGE = "com/openpojo/issues/issue84/ClassWithCredentials";
  private static final String SUN_SECURITY_KRB_5_CREDENTIALS = "sun/security/krb5/Credentials";

  static byte[] dump() {

    ClassWriter cw = new ClassWriter(0);
    FieldVisitor fv;
    MethodVisitor mv;
    AnnotationVisitor av0;

    cw.visit(52, ACC_FINAL + ACC_SUPER, CLASS_PACKAGE, null, "java/lang/Object", null);

    {
      fv = cw.visitField(ACC_PRIVATE, "credentials", "L" + SUN_SECURITY_KRB_5_CREDENTIALS + ";", null, null);
      fv.visitEnd();
    }
    {
      mv = cw.visitMethod(0, "<init>", "()V", null, null);
      mv.visitCode();
      mv.visitVarInsn(ALOAD, 0);
      mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
      mv.visitInsn(RETURN);
      mv.visitMaxs(1, 1);
      mv.visitEnd();
    }
    {
      mv = cw.visitMethod(ACC_PUBLIC, "getCredentials", "()L" + SUN_SECURITY_KRB_5_CREDENTIALS + ";", null, null);
      mv.visitCode();
      mv.visitVarInsn(ALOAD, 0);
      mv.visitFieldInsn(GETFIELD, CLASS_PACKAGE, "credentials", "L" + SUN_SECURITY_KRB_5_CREDENTIALS + ";");
      mv.visitInsn(ARETURN);
      mv.visitMaxs(1, 1);
      mv.visitEnd();
    }
    {
      mv = cw.visitMethod(ACC_PUBLIC, "setCredentials", "(L" + SUN_SECURITY_KRB_5_CREDENTIALS + ";)V", null, null);
      mv.visitCode();
      mv.visitVarInsn(ALOAD, 0);
      mv.visitVarInsn(ALOAD, 1);
      mv.visitFieldInsn(PUTFIELD, CLASS_PACKAGE, "credentials", "L" + SUN_SECURITY_KRB_5_CREDENTIALS + ";");
      mv.visitInsn(RETURN);
      mv.visitMaxs(2, 2);
      mv.visitEnd();
    }
    cw.visitEnd();

    return cw.toByteArray();
  }
}
