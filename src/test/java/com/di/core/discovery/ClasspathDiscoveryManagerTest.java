package com.di.core.discovery;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class ClasspathDiscoveryManagerTest {

    @Before
    public void setup() {
        System.out.println("Running setup");
    }

    @Test
    public void testClasspathDiscoveryManager() {
        System.out.println("Running " + ClasspathDiscoveryManagerTest.class.getCanonicalName() +
                ".testClasspathDiscoveryManager test");
        try {
            List<String> filesDiscoveredFromClasspath = ClasspathDiscoveryManager.discover();
            filesDiscoveredFromClasspath.forEach(f -> {
                System.out.println("Discovered File: " + f);
            });
        } catch (IOException e) {
            Assert.assertTrue(false);
        }
    }

    @Test
    public void testPathInClasspath() {
        System.out.println("Running " + ClasspathDiscoveryManagerTest.class.getCanonicalName() +
                ".testPathInClasspath test");
        try {
            ClasspathDiscoveryManager.isPathInClasspath("resources",
                    ClasspathDiscoveryManager.discover());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @After
    public void destroy() {
        System.out.println("Running destroy");
    }
}
