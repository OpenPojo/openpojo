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

package com.openpojo.reflection.java.version;

import com.openpojo.reflection.java.version.impl.VersionImp;

/**
 * @author oshoukry
 */
public class VersionFactory {

  public static Version getImplementationVersion(Class clazz) {
    if (clazz != null)
      return getVersion(clazz.getPackage().getImplementationVersion());
    return new VersionImp(null);
  }


  public static Version getVersion(String version) {
    return new VersionImp(version);
  }

  private VersionFactory() {
    throw new UnsupportedOperationException(VersionFactory.class.getName() +  " should not be constructed!");

  }
}
