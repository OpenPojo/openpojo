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

package com.openpojo.reflection.java.bytecode.asm;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.reflection.java.load.ClassUtil;
import com.openpojo.reflection.java.packageloader.reader.JarFileReader;
import com.openpojo.reflection.java.version.Version;
import com.openpojo.reflection.java.version.VersionFactory;

import static com.openpojo.reflection.java.version.VersionFactory.getImplementationVersion;

/**
 * @author oshoukry
 */
public class ASMDetector {
  public static final String ASM_CLASS_NAME = "org.objectweb.asm.ClassWriter";
  private static final String VERSION_MANIFEST_KEY_FALLBACK = "Bundle-Version";

  private ASMDetector() {
  }

  public static ASMDetector getInstance() {
    return Instance.INSTANCE;
  }

  public boolean isASMLoaded() {
    return ClassUtil.isClassLoaded(ASM_CLASS_NAME);
  }

  public Version getVersion() {
    Class<?> clazz = ClassUtil.loadClass(ASM_CLASS_NAME);
    Version implementationVersion = getImplementationVersion(clazz);
    if (implementationVersion.getVersion() == null) {
      implementationVersion = getBundleVersion(clazz);
    }
    return implementationVersion;
  }

  public Version getBundleVersion(Class<?> clazz) {
    Version bundleVersion;

    try {
      PojoClass pojoClass = PojoClassFactory.getPojoClass(clazz);
      String sourcePath = pojoClass.getSourcePath();
      String jarFilePath = JarFileReader.getJarFileNameFromURLPath(sourcePath);
      String bundleVersionManifestEntry = JarFileReader.getInstance(jarFilePath).getManifestEntry(VERSION_MANIFEST_KEY_FALLBACK);
      bundleVersion = VersionFactory.getVersion(bundleVersionManifestEntry);
    } catch (Exception ignored) {
      bundleVersion = VersionFactory.getVersion(null);
    }

    return bundleVersion;
  }

  private static class Instance {
    private static final ASMDetector INSTANCE = new ASMDetector();
  }
}
