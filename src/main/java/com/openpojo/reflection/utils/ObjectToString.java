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

package com.openpojo.reflection.utils;

import java.util.Arrays;

/**
 * @author oshoukry
 */
public class ObjectToString {

  public static String toString(Object o) {
    return getHandler(o).toString(o);
  }

  private static ObjectToStringHandler getHandler(Object o) {
    if (o == null)
      return new ObjectToStringHandler() {
        public String toString(Object o) {
          return null;
        }
      };

    if (!o.getClass().isArray())
      return new ObjectToStringHandler() {
        public String toString(Object o) {
          return o.toString();
        }
      };

    // Array handling
    // Since Java has no way of auto-boxing an array of primitives, each must be examined independently.
    Class<?> componentType = o.getClass().getComponentType();

    if (componentType == byte.class)
      return new ObjectToStringHandler() {
        public String toString(Object o) {
          return Arrays.toString(byte[].class.cast(o));
        }
      };

    if (componentType == char.class)
      return new ObjectToStringHandler() {
        public String toString(Object o) {
          return Arrays.toString(char[].class.cast(o));
        }
      };

    if (componentType == short.class)
      return new ObjectToStringHandler() {
        public String toString(Object o) {
          return Arrays.toString(short[].class.cast(o));
        }
      };

    if (componentType == int.class)
      return new ObjectToStringHandler() {
        public String toString(Object o) {
          return Arrays.toString(int[].class.cast(o));
        }
      };

    if (componentType == long.class)
      return new ObjectToStringHandler() {
        public String toString(Object o) {
          return Arrays.toString(long[].class.cast(o));
        }
      };

    if (componentType == float.class)
      return new ObjectToStringHandler() {
        public String toString(Object o) {
          return Arrays.toString(float[].class.cast(o));
        }
      };

    if (componentType == double.class)
      return new ObjectToStringHandler() {
        public String toString(Object o) {
          return Arrays.toString(double[].class.cast(o));
        }
      };

    if (componentType == boolean.class)
      return new ObjectToStringHandler() {
        public String toString(Object o) {
          return Arrays.toString(boolean[].class.cast(o));
        }
      };

    return new ObjectToStringHandler() {
      public String toString(Object o) {
        return Arrays.deepToString((Object[]) o);
      }
    };
  }

  interface ObjectToStringHandler {
    String toString(Object o);
  }

  private ObjectToString() {
    throw new UnsupportedOperationException(ObjectToString.class.getName() +  " should not be constructed!");
  }
}