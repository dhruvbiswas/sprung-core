package com.di.core.discovery;

import com.di.core.container.DIContainer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class DiContainerManagerTest {

    @Before
    public void setup() {
        System.out.println("Running setup");
    }

    @Test
    public void testDiContainerManagerTest() {
        System.out.println("Running " + DiContainerManagerTest.class.getCanonicalName() +
                ".testDiContainerManagerTest() test");
        String pkg = "com.di.core.lib.test";
        int expected = 5;
        int actual = 0;
        try {
            List<String> discoveredClasses = ClassDiscoveryManager.discover(pkg);
            discoveredClasses.forEach(f -> {
                System.out.println("Discovered Class: " + f);
            });
            actual = discoveredClasses.size();
            DIContainerManager.initializeContainer(discoveredClasses);
            DIContainer.DI_CONTAINER.getBeanIdToObjectMap().forEach((k, v) -> {
                System.out.println(k + ": " + v.toString());
            });
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
