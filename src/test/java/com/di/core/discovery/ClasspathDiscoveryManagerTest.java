package com.di.core.discovery;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/*
 * This is incomplete.... We want to to be able to search
 * for a particular filename in classpath
 * This would work on the command line without any problem
 * But somehow intelliJ editor within the editor is not sourcing
 * ./src/main/resources or ./test/main/resources into classpath
 * by default
 * ClasspathDiscoveryManager should work from the command line
 * as well from inside the editor
 */
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

    @Test
    public void testApplicationProperties() {
        Thread.currentThread().setContextClassLoader(ClasspathDiscoveryManagerTest.class.getClassLoader());
        InputStream resourceStream = Thread.currentThread().getContextClassLoader().
                getResourceAsStream("application.properties");
        Properties prop = new Properties();
        if (resourceStream != null) {
            try {
                prop.load(resourceStream);
            } catch (IOException e) {
                Assert.assertTrue(false);
            }
        }
        System.out.println(prop.getProperty("com.di.core.message1"));
        System.out.println(prop.getProperty("com.di.core.message2"));
        System.out.println(prop.getProperty("com.di.core.message3"));
    }

    @After
    public void destroy() {
        System.out.println("Running destroy");
    }
}
