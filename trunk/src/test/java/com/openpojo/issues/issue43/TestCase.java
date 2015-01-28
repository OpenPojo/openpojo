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
public class TestCase {

    @Test
    public void shouldEscapeSpaces() throws MalformedURLException {
        String givenURL = "file:///apps/jboss/.jenkins/workspace/Mobil%20kontroll%20-%20Trunk%20-%20Deploy%20Utv%20(mk-deploy-utv)" +
                "/model-kund/target/test-classes/se/metria/system/mk/kund/model";
        URL url = new URL(givenURL);

        File expectedFile = new File("/apps/jboss/.jenkins/workspace/Mobil kontroll - Trunk - Deploy Utv (mk-deploy-utv)" +
                "/model-kund/target/test-classes/se/metria/system/mk/kund/model");

        URLToFileSystemAdapter urlToFileSystemAdapter = new URLToFileSystemAdapter(url);

        Assert.assertEquals(expectedFile, urlToFileSystemAdapter.getAsFile());
    }
}
