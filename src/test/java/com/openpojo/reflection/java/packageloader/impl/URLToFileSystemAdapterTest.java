/*
 * Copyright (c) 2010-2015 Osman Shoukry
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License or any
 *    later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
