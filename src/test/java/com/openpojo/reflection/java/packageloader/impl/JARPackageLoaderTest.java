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

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;

import com.openpojo.log.utils.MessageFormatter;
import com.openpojo.reflection.java.Java;
import com.openpojo.validation.affirm.Affirm;
import org.junit.Before;
import org.junit.Test;

/**
 * @author oshoukry
 */
public class JARPackageLoaderTest {
  private final String packageName = "org.apache.log4j";
    private final String[] expectedSubPackagesNames = new String[]{
        packageName + ".chainsaw",
        packageName + ".config",
        packageName + ".helpers",
        packageName + ".jdbc",
        packageName + ".jmx",
        packageName + ".lf5",
        packageName + ".net",
        packageName + ".nt",
        packageName + ".or",
        packageName + ".pattern",
        packageName + ".rewrite",
        packageName + ".spi",
        packageName + ".varia",
        packageName + ".xml"
    };

    private final String[] expectedClassesNames = new String[]{
        "org.apache.log4j.Appender",
        "org.apache.log4j.AppenderSkeleton",
        "org.apache.log4j.AsyncAppender$DiscardSummary",
        "org.apache.log4j.AsyncAppender$Dispatcher",
        "org.apache.log4j.AsyncAppender",
        "org.apache.log4j.BasicConfigurator",
        "org.apache.log4j.Category",
        "org.apache.log4j.CategoryKey",
        "org.apache.log4j.ConsoleAppender$SystemErrStream",
        "org.apache.log4j.ConsoleAppender$SystemOutStream",
        "org.apache.log4j.ConsoleAppender",
        "org.apache.log4j.DailyRollingFileAppender",
        "org.apache.log4j.DefaultCategoryFactory",
        "org.apache.log4j.DefaultThrowableRenderer",
        "org.apache.log4j.Dispatcher",
        "org.apache.log4j.EnhancedPatternLayout",
        "org.apache.log4j.EnhancedThrowableRenderer",
        "org.apache.log4j.FileAppender",
        "org.apache.log4j.HTMLLayout",
        "org.apache.log4j.Hierarchy",
        "org.apache.log4j.Layout",
        "org.apache.log4j.Level",
        "org.apache.log4j.LogMF",
        "org.apache.log4j.LogManager",
        "org.apache.log4j.LogSF",
        "org.apache.log4j.LogXF",
        "org.apache.log4j.Logger",
        "org.apache.log4j.MDC",
        "org.apache.log4j.NDC$DiagnosticContext",
        "org.apache.log4j.NDC",
        "org.apache.log4j.NameValue",
        "org.apache.log4j.PatternLayout",
        "org.apache.log4j.Priority",
        "org.apache.log4j.PropertyConfigurator",
        "org.apache.log4j.PropertyWatchdog",
        "org.apache.log4j.ProvisionNode",
        "org.apache.log4j.RollingCalendar",
        "org.apache.log4j.RollingFileAppender",
        "org.apache.log4j.SimpleLayout",
        "org.apache.log4j.SortedKeyEnumeration",
        "org.apache.log4j.TTCCLayout",
        "org.apache.log4j.WriterAppender" };

  @Before
  public void setUp() {
  }

  @Test
  public final void shouldGetJarSubPackages() {

    JARPackageLoader jarPackage = getJarPackageLoader(packageName);

    Set<String> subPackagesNames = jarPackage.getSubPackages();

    Affirm.affirmEquals(MessageFormatter.format("SubPackages added/removed to {0} found[{1}]?!", packageName, subPackagesNames)
        , expectedSubPackagesNames.length, subPackagesNames.size());

    for (String expectedPackageName : expectedSubPackagesNames) {
      Affirm.affirmTrue(MessageFormatter.format("Expected package[{0}] not found", expectedPackageName),
          subPackagesNames.contains(expectedPackageName));
    }
  }

  @Test
  public final void shouldGetJarSubClasses() {
    JARPackageLoader jarPackage = getJarPackageLoader(packageName);

    Set<String> classesNames = new LinkedHashSet<String>();
    for (Type type : jarPackage.getTypes()) {
      classesNames.add(((Class<?>) type).getName());
    }

    Affirm.affirmEquals(MessageFormatter.format("Classes added/removed to {0} found[{1}]?!", packageName, classesNames),
        expectedClassesNames.length, classesNames.size());

    for (String expectedClassName : classesNames) {
      Affirm.affirmTrue(MessageFormatter.format("Expected class[{0}] not found", expectedClassName),
          classesNames.contains(expectedClassName));
    }
  }

  private JARPackageLoader getJarPackageLoader(final String packageName) {
    Enumeration<URL> resources = null;
    try {
      resources = Thread.currentThread().getContextClassLoader().getResources(
          packageName.replace(Java.PACKAGE_DELIMITER, Java.PATH_DELIMITER));
    } catch (IOException e) {
      Affirm.fail(MessageFormatter.format("Failed to get resources for package[{0}] got exception[{1}]", packageName, e));
    }
    URL resource = resources.nextElement();

    Affirm.affirmEquals(MessageFormatter.format("[{0}] not located in a jar file!!", packageName), "jar", resource.getProtocol());

    return new JARPackageLoader(resource, packageName);
  }

}
