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

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.construct.InstanceFactory;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.reflection.java.Java;
import com.openpojo.validation.affirm.Affirm;
import org.junit.Before;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class ZIPPackageLoaderTest {

  private boolean isWindows = false;

  @Before
  public void setup() {
    if (System.getProperty("os.name").toLowerCase().contains("windows"))
      isWindows = true;
  }

  @Test
  public void canLoadZipFile() throws MalformedURLException, ClassNotFoundException {

    String mypath = "jar:file://";
    String userdir = System.getProperty("user.dir");
    if (isWindows)
      mypath += userdir.substring(2).replace("\\", "/"); // skip over the C:
    else
      mypath += userdir;

    mypath += "/test/sampleJar.zip!/";

    URLClassLoader urlClassLoader = URLClassLoader.newInstance(new URL[] { new URL(mypath) });

    String[] classNames = new String[] { "com.openpojotest.AClass", "com.openpojotest.AndAnotherClass" };

    boolean saidHello = false;
    boolean gotGreetingMessage = false;

    for (String className : classNames) {
      Class entry = urlClassLoader.loadClass(className);
      PojoClass pojoClass = PojoClassFactory.getPojoClass(entry);

      Affirm.affirmEquals("Should be equal", mypath + className.replace(Java.PACKAGE_DELIMITER, Java.PATH_DELIMITER) +
          Java.CLASS_EXTENSION, "jar:" + pojoClass.getSourcePath());

      if (pojoClass.getName().equals("com.openpojotest.AClass")) {
        for (PojoMethod pojoMethod : pojoClass.getPojoMethods()) {
          if (pojoMethod.getName().equals("sayHello")) {
            Object instance = InstanceFactory.getInstance(pojoClass);
            String hello = (String) pojoMethod.invoke(instance);
            Affirm.affirmEquals("sayHello failed!!", "Hello World!", hello);
            saidHello = true;
          }
        }
      } else {
        String randomString = RandomFactory.getRandomValue(String.class);
        for (PojoMethod pojoMethod : pojoClass.getPojoMethods()) {
          if (pojoMethod.getName().equals("getGreetingMessage")) {
            Object instance = InstanceFactory.getInstance(pojoClass);
            String greetingMessage = (String) pojoMethod.invoke(instance, randomString);
            Affirm.affirmEquals("getGreetingMessage failed!!", "Hello " + randomString + ", so good to meet you",
                greetingMessage);
            gotGreetingMessage = true;
          }
        }
      }
    }

    Affirm.affirmTrue("Should have saidHello & gotGreetingMessage", saidHello && gotGreetingMessage);
  }

}
