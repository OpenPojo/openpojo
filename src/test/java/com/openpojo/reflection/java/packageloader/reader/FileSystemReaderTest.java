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

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.exception.ReflectionException;
import com.openpojo.reflection.java.packageloader.impl.URLToFileSystemAdapter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * @author oshoukry
 */
public class FileSystemReaderTest {
  private File file;

  @Before
  public void setup() {
    file = new File(System.getProperty("user.dir")
        + File.separator + ".tmp.testing.folder." + RandomFactory.getRandomValue(String.class));
    file.mkdir();
  }

  @After
  public void tearDown() {
    file.delete();
  }

  @Test
  public void shouldNotFailForEmptyPaths() throws MalformedURLException {
    URL url = new URL("file://" + file.getAbsolutePath());
    FileSystemReader fileSystemReader = FileSystemReader.getInstance(url);
    assertThat(fileSystemReader, notNullValue());
    assertThat(fileSystemReader.getSubPackagesOfPackage("").size(), is(0));
    assertThat(fileSystemReader.getTypesInPackage("").size(), is(0));
  }

  @Test
  public void throwsProperException() throws MalformedURLException {
    String anyUrl = anyUrl();
    try {
      FileSystemReader.getInstance(new URL(anyUrl));
      fail("Expected exception not thrown!");
    } catch (ReflectionException re) {
      URLToFileSystemAdapter ufsa = new URLToFileSystemAdapter(new URL(anyUrl));
      assertEquals("Failed to retrieve entries in path: "
              + "[" + ufsa.getAsFile().getAbsolutePath() + "]"
              + " created from URI: [" + ufsa.getAsURI() + "].  Please report this issue @ http://openpojo.com"
          , re.getMessage());
    }
  }

  private String anyUrl() {
    return "http://any.domain." + RandomFactory.getRandomValue(String.class) + ".com";
  }
}