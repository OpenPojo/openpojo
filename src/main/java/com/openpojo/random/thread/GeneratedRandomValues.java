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

package com.openpojo.random.thread;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

/**
 * This class holds random generated types on current thread.
 *
 * @author oshoukry
 */
public class GeneratedRandomValues {
  private static ThreadLocal<Set<Type>> threadLocal = new ThreadLocal<Set<Type>>() {
    @Override
    protected Set<Type> initialValue() {
      return new HashSet<Type>();
    }
  };

  /**
   * Add type to the thread list of types generated.
   *
   * @param type
   *     The type to add.
   */
  public static void add(final Type type) {
    threadLocal.get().add(type);
  }

  /**
   * Check if this type was added by this thread already.
   *
   * @param type
   *     The type to check for.
   * @return Returns true if the type has been added by this thread already.
   */
  public static boolean contains(final Type type) {
    Set<Type> generatedValues = threadLocal.get();
    return generatedValues.contains(type);
  }

  /**
   * Remove a specific type from the list.
   *
   * @param type
   *     The type to remove.
   */
  public static void remove(final Type type) {
    threadLocal.get().remove(type);
  }

  private GeneratedRandomValues() {
    throw new UnsupportedOperationException(GeneratedRandomValues.class.getName() +  " should not be constructed!");
  }
}
