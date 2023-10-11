package com.di.core.discovery;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class ClassDiscoveryManagerTest {

    @Before
    public void setup() {
        System.out.println("Running setup");
    }

    @Test
    public void testClasspathDiscoveryManager() {
        System.out.println("Running " + ClassDiscoveryManagerTest.class.getCanonicalName() + " test");
        String pkg = "com.di.core.lib.test";
        int expected = 3;
        int actual = 0;
        try {
            List<String> discoveredClasses = ClassDiscoveryManager.discover(pkg);
            discoveredClasses.forEach(f -> {
                System.out.println("Discovered Class: " + f);
            });
            actual = discoveredClasses.size();
            Assert.assertEquals("Expecting " + expected +
                    " number of classes under package " + pkg + " but instead discovered " +
                    actual + " number of classes", expected, actual);
        } catch (IOException e) {
            Assert.assertTrue(false);
        }
    }

    @After
    public void destroy() {
        System.out.println("Running destroy");
    }
}
