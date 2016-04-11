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

package com.openpojo.reflection.java.load;

import com.openpojo.reflection.java.bytecode.asm.ClassReaderFactory;

/**
 * @author oshoukry
 */
public class ClassUtil {
  private ClassUtil() {
    throw new UnsupportedOperationException(ClassReaderFactory.class.getName() + " should not be constructed!");
  }

  public static boolean isClassLoaded(String className) {
    return loadClass(className) != null;
  }

  public static Class<?> loadClass(String className) {
    return loadClass(className, true);
  }

  public static Class<?> loadClass(String className, boolean initialize) {
    return loadClass(className, initialize, getThreadClassLoader());
  }

  public static Class<?> loadClass(String className, boolean initialize, ClassLoader classloader) {
    try {
      return Class.forName(className, initialize, classloader);
    } catch (LinkageError linkageError) { // class depends on another that wasn't found.
    } catch (ClassNotFoundException classNotFoundException) { // no such class found.
    }
    return null;
  }

  private static ClassLoader getThreadClassLoader() {
    return Thread.currentThread().getContextClassLoader();
  }
}
