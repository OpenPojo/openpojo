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

package com.openpojo.validation.utils;

import java.io.Closeable;

/**
 * @author oshoukry
 */
public class CloseableHelper {

  public static void closeResources(Closeable... closeables) {
    if (closeables != null)
      for (Closeable closeable : closeables)
        try {
          if (closeable != null)
            closeable.close();
        } catch (Exception ignored) {
        }
  }

  private CloseableHelper() {
    throw new UnsupportedOperationException(CloseableHelper.class.getName() +  " should not be constructed!");
  }
}
