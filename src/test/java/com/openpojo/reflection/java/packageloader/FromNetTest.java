package com.openpojo.reflection.java.packageloader;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.openpojo.reflection.java.packageloader.impl.JARPackageLoader;

public class FromNetTest {
    Logger log;
    private final String packageName = "org";
    private ClassLoader cl;
    private Enumeration<URL> urls;

    @Before
    public void setup() throws IOException {
        cl = Thread.currentThread().getContextClassLoader();
        urls = cl.getResources(packageName);
    }

    @Test
    public void rootResources() throws URISyntaxException {
        while (urls.hasMoreElements()) {
            URL entry = urls.nextElement();
            if (entry.toURI().toString().startsWith("jar")) {
                for (Type type : new JARPackageLoader(entry, packageName).getTypes()) {
                    System.out.println("Type: " + ((Class<?>) type).getName());
                }
                return;
            }
            System.out.println(entry.toURI().toString());
        }
    }

    @Test
    public void getSubPackages() throws URISyntaxException {
        while (urls.hasMoreElements()) {
            URL entry = urls.nextElement();
            if (entry.toURI().toString().startsWith("jar")) {
                for (String subPackageName : new JARPackageLoader(entry, packageName).getSubPackages()) {
                    System.out.println("SubPackage to " + packageName + ": " + subPackageName);

                }
                return;
            }

        }
    }

}
