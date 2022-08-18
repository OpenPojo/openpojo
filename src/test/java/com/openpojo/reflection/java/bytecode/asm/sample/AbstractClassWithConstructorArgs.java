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

import java.util.List;

/**
 * @author oshoukry
 */
public abstract class AbstractClassWithConstructorArgs {
  public AbstractClassWithConstructorArgs(String s,
                                          boolean b,
                                          byte by,
                                          char ch,
                                          double d,
                                          float f,
                                          int i,
                                          long l,
                                          short sh,
                                          int[] intarray,
                                          String[] strings,
                                          List<AbstractClassWithVariousAbstractMethods> classes) {
  }
}
