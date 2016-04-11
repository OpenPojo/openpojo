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

package com.openpojo.issues.issue43;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import com.openpojo.reflection.java.packageloader.impl.URLToFileSystemAdapter;
import org.junit.Test;
import org.testng.Assert;

/**
 * @author oshoukry
 */
public class IssueTest {

  @Test
  public void shouldEscapeSpaces() throws MalformedURLException {
    String givenURL = "file:///apps/jboss/.jenkins/workspace/Mobil%20kontroll%20-%20Trunk%20-%20Deploy%20Utv%20" +
        "(mk-deploy-utv)/model-kund/target/test-classes/se/metria/system/mk/kund/model";
    URL url = new URL(givenURL);

    File expectedFile = new File("/apps/jboss/.jenkins/workspace/Mobil kontroll - Trunk - Deploy Utv (mk-deploy-utv)" +
        "/model-kund/target/test-classes/se/metria/system/mk/kund/model");

    URLToFileSystemAdapter urlToFileSystemAdapter = new URLToFileSystemAdapter(url);

    Assert.assertEquals(expectedFile, urlToFileSystemAdapter.getAsFile());
  }
}
