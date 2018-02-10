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

package com.openpojo.reflection.java.bytecode.asm.sample;

/**
 * @author oshoukry
 */
public abstract class AbstractClassWithVariousAbstractMethods {
  // Various Objects
  public abstract String objectString();
  public abstract Object objectObject();
  public abstract Boolean objectBoolean();
  public abstract Byte objectByte();
  public abstract Character objectCharacter();
  public abstract Short objectShort();
  public abstract Integer objectInteger();
  public abstract Long objectLong();
  public abstract AbstractClassWithVariousAbstractMethods recursive();

  //   Primitives
  public abstract boolean someBoolean();
  public abstract float someFloat();
  public abstract byte someByte();
  public abstract char someChar();
  public abstract short someShort();
  public abstract int someInt();
  public abstract long someLong();
  public abstract double someDouble();
  public abstract void someVoid();

  // Methods with Params
  public abstract double methodWithParams(String s, int i, float f, double d);

  // Returning Arrays
  public abstract String[] methodReturnsStrings();
}
