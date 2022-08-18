/*
 * Copyright (c) 2010-2019 Osman Shoukry
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

package com.openpojo.issues.issue123;

import java.net.URL;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.reflection.java.Java;
import com.openpojo.reflection.java.load.ClassUtil;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class IssueTest {

  @Test
  public void preRequisiteCondition_ShouldGetPathAsFileColonWithoutDoubleSlashes() {
    Class<?> aClass = ClassUtil.loadClass(TestingClassData.className);
    ClassLoader cl = aClass.getClassLoader();
    String classNameAsPath = aClass.getName().replace(Java.PACKAGE_DELIMITER, Java.PATH_DELIMITER) + Java.CLASS_EXTENSION;
    URL resource = cl.getResource(classNameAsPath);

    assertThat(resource, notNullValue());
    String location = resource.toString();

    assertThat(location, startsWith("jar:file:/"));
    assertThat(location, endsWith(classNameAsPath));

    assertThat(location, not(containsString("//")));
  }

  @Test
  public void shouldFixProtocolForJarIfJarDoesNotHaveColonDoubleSlashes() {
    PojoClass pojoClass = PojoClassFactory.getPojoClass(ClassUtil.loadClass(TestingClassData.className));
    String sourcePath = pojoClass.getSourcePath();

    assertThat(sourcePath, startsWith(TestingClassData.expectedPathStartsWith));
    assertThat(sourcePath, containsString(TestingClassData.expectedPathContains1));
    assertThat(sourcePath, containsString(TestingClassData.expectedPathContains2));
    assertThat(sourcePath, endsWith(TestingClassData.expectedPathEndsWith));
  }

  private static class TestingClassData {
    static String className = "org.objectweb.asm.ClassWriter";

    static String expectedPathStartsWith = "file:/";
    static String expectedPathContains1 = "/.m2/repository/org/ow2/asm/asm/";
    static String expectedPathContains2 = ".jar";
    static String expectedPathEndsWith = className.replace(Java.PACKAGE_DELIMITER, Java.PATH_DELIMITER) + Java.CLASS_EXTENSION;
  }

}
