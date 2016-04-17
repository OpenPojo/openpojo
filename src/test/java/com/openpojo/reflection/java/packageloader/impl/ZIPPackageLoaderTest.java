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

package com.openpojo.reflection.java.packageloader.impl;

import java.net.MalformedURLException;
import java.net.URLClassLoader;

import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.construct.InstanceFactory;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.reflection.java.Java;
import com.openpojo.utils.samplejar.SampleJar;
import com.openpojo.validation.affirm.Affirm;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class ZIPPackageLoaderTest {

  @Test
  public void canLoadZipFile() throws MalformedURLException, ClassNotFoundException {

    URLClassLoader urlClassLoader = SampleJar.getURLClassLoader();

    String[] classNames = new String[] { "com.openpojotest.AClass", "com.openpojotest.AndAnotherClass" };

    boolean saidHello = false;
    boolean gotGreetingMessage = false;

    for (String className : classNames) {
      Class entry = urlClassLoader.loadClass(className);
      PojoClass pojoClass = PojoClassFactory.getPojoClass(entry);

      Affirm.affirmEquals("Should be equal", SampleJar.getJarURLPath() + className.replace(Java.PACKAGE_DELIMITER, Java.PATH_DELIMITER) +
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
