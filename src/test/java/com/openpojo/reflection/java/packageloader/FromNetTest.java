package com.openpojo.reflection.java.packageloader;

import org.apache.log4j.Logger;
import org.junit.Test;



public class FromNetTest {
    Logger log;
    private final String packageName = "org.apache.log4j";

    @Test
    public void getClassesFromJAR() throws ClassNotFoundException {
        for (Class<?> entry : FromNet.getClassesForPackage(packageName)) {
            System.out.println(entry.getName());
        }
    }

}
