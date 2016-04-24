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

package com.openpojo.reflection.java.packageloader.reader;

import java.net.URL;
import java.util.Set;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.reflection.java.Java;
import com.openpojo.utils.samplejar.SampleJar;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class JarFileReaderTest {

  @Test
  public void onlyPrivateConstructors() {
    PojoClass pojoClass = PojoClassFactory.getPojoClass(JarFileReader.class);
    for (PojoMethod method : pojoClass.getPojoConstructors()) {
      Assert.assertTrue("Constructor must be private [" + method + "]", method.isPrivate());
    }
  }

  @Test
  public void canCreate() {
    JarFileReader jarFileReader = JarFileReader.getInstance((String)null);
    Assert.assertNotNull(jarFileReader);
  }

  @Test
  public void canCreateUsingInvalidJarFileUrl() {
    JarFileReader jarfileReader = JarFileReader.getInstance((URL) null);
    Assert.assertNotNull(jarfileReader);
    Assert.assertFalse(jarfileReader.isValid());
  }

  @Test
  public void canReadJarUsingURLOrFile() {
    JarFileReader urlJarFileReader = JarFileReader.getInstance(SampleJar.getJarURL());
    JarFileReader filepathJarFileReader = JarFileReader.getInstance(SampleJar.getJarFilePath());

    Assert.assertTrue("Invalid URL JarFile [" + SampleJar.getJarURLPath() + "]", urlJarFileReader.isValid());
    Assert.assertTrue("Invalid filepath JarFile [" + SampleJar.getJarFilePath() + "]", filepathJarFileReader.isValid());

    Set<String> urlClassNames = urlJarFileReader.getClassNames();
    Set<String> filepathClassNames = filepathJarFileReader.getClassNames();
    Assert.assertEquals(urlClassNames.size(), filepathClassNames.size());

    for (String urlEntry : urlClassNames) {
      Assert.assertTrue("Failed to find urlEntry [" + urlEntry + "]", filepathClassNames.contains(urlEntry));
    }
  }

  @Test
  public void shouldReturnFalseWhenInvalidFile() {
    JarFileReader jarFileReader = JarFileReader.getInstance((String)null);
    Assert.assertFalse(jarFileReader.isValid());
  }

  @Test
  public void shouldReturnFalseIfFileIsNotJarFile() throws Exception {
    PojoClass pojoClass = PojoClassFactory.getPojoClass(this.getClass());
    String sourcePath = (new URL(pojoClass.getSourcePath())).getPath();
    JarFileReader jarFileReader = JarFileReader.getInstance(sourcePath);
    Assert.assertFalse(jarFileReader.isValid());
  }

  @Test
  public void isValidReturnsTrueWhenFileIsJar() throws Exception {
    System.out.println(SampleJar.getJarFilePath());
    JarFileReader jarFileReader = JarFileReader.getInstance(SampleJar.getJarFilePath());
    Assert.assertTrue(jarFileReader.isValid());
  }

  @Test
  public void canReadEntries() {
    String jarFile = getJarFile("rt.jar");
    Assert.assertNotNull(jarFile);
    JarFileReader jarFileReader = JarFileReader.getInstance(jarFile);
    Assert.assertNotNull(jarFileReader);
    Assert.assertTrue("rt.jar should be valid", jarFileReader.isValid());
    Set<String> classNames = jarFileReader.getClassNames();
    Assert.assertThat(classNames.size(), Matchers.greaterThan(10000)); // actual number is 20,651
  }

  private String getJarFile(String jarFileName) {
    String classPath = System.getProperty("java.class.path");
    classPath += Java.CLASSPATH_DELIMITER + System.getProperty("java.library.path");
    classPath += Java.CLASSPATH_DELIMITER + System.getProperty("java.ext.dirs");
    classPath += Java.CLASSPATH_DELIMITER + System.getProperty("sun.boot.class.path");
    String[] classPathParts = classPath.split(Java.CLASSPATH_DELIMITER);
    for (String entry : classPathParts) {
      if (entry.endsWith(Java.PATH_DELIMITER + jarFileName))
        return entry;
    }
    return null;
  }
}