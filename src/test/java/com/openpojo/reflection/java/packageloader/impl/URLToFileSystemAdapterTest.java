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

package com.openpojo.reflection.java.packageloader.impl;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import com.openpojo.reflection.exception.ReflectionException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class URLToFileSystemAdapterTest {

  private String pathSeperator = "/";
  private String rootPrefix = "/";
  private boolean isWindows = false;

  @Before
  public void setup() {
    String os = System.getProperty("os.name");
    if (os.toLowerCase().contains("windows")) {
      isWindows = true;
      pathSeperator = "\\";
      rootPrefix = new File("/").getAbsolutePath();
    }
  }

  @Test(expected = ReflectionException.class)
  public void whenNullURLShouldThrowException() {
    new URLToFileSystemAdapter(null);
  }

  @Test
  public void invalidURLShouldThrowException() throws MalformedURLException {
    URLToFileSystemAdapter urlToFileSystemAdapter = new URLToFileSystemAdapter(new URL("file://Not A Parse-able URI"));
    try {
      urlToFileSystemAdapter.getAsURI();
      Assert.fail("Invalid URL should've failed to transfer to URI");
    } catch (ReflectionException ignored) {
    }
  }

  @Test
  public void whenURLendsWithPercentDoNotExcape() {
    validateURLtoExpectedFilePath(rootPrefix + "apps%", "file:///apps%");
  }

  @Test
  public void whenURLHasPercent20TurnToSpaces() {
    validateURLtoExpectedFilePath(rootPrefix + "WithOne Two Spaces", "file:///WithOne%20Two%20Spaces");
  }

  @Test
  public void whenOnWindowsMountedShouldPreserveServerAuthority() {
    String expectedFilePath = pathSeperator + "ourserver.com@ourserver.com" + pathSeperator + "A Server Path";
    if (isWindows)
      expectedFilePath = pathSeperator + expectedFilePath;
    validateURLtoExpectedFilePath(expectedFilePath, "file://ourserver.com/A%20Server%20Path/");
  }
  
    @Test
    /**
     * Issue where scanned JAR does not create the file path correctly (gets file: instead of file://). Example of what is found:
     *     jar:file:/Users/userid/.m2/repository/com/someproject/mydto/1.0.2-SNAPSHOT/mydto-1.0.2-SNAPSHOT.jar!/com/someproject/dto/MyDto.class
     * 
     * Without the change this test will throw:
     *  com.openpojo.reflection.exception.ReflectionException: Relative path in absolute URI:
     *  file://sers/userid/.m2/repository/com/someproject/mydto/1.0.2-SNAPSHOT/mydto-1.0.2-SNAPSHOT.jar!/com/someproject/dto/MyDto.class
     */
    public void issue118_JarFilepathNotFormedCorrectly() {
      validateURLtoExpectedFilePath(rootPrefix
        + "Users/userid/.m2/repository/com/someproject/mydto/1.0.2-SNAPSHOT/mydto-1.0.2-SNAPSHOT.jar!/com/someproject/dto/MyDto.class",
          "jar:file:/Users/userid/.m2/repository/com/someproject/mydto/1.0.2-SNAPSHOT/mydto-1.0.2-SNAPSHOT.jar!/com/someproject/dto/MyDto.class");
    }

  private void validateURLtoExpectedFilePath(String expectedFilePath, String url) {
    try {
      URLToFileSystemAdapter urlToFileSystemAdapter = new URLToFileSystemAdapter(new URL(url));
      String absolutePath = urlToFileSystemAdapter.getAsFile().getAbsolutePath();
      Assert.assertEquals(expectedFilePath, absolutePath);
    } catch (MalformedURLException e) {
      Assert.fail("Exception encountered: " + e);
      e.printStackTrace();
    }
  }
}
