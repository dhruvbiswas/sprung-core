package com.di.core.discovery;

import com.di.core.annotations.ComponentScan;
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

        // String pkg = "com.di.core.lib.test, com.di.core.lib2.test";
        String pkg = "com.di.core.lib2.test";
        int expected = 7;
        int actual = 0;
        try {
            String pkgString = pkg;
            // TODO: add error handling here

            if (pkgString != null && pkgString.length() > 0) {
                // Lets split the string
                String[] pkgs = pkgString.split(",");

                for (int i = 0; i < pkgs.length; i++) {
                    List<String> discoveredClasses = ClassDiscoveryManager.discover(pkgs[i].trim());
                    discoveredClasses.forEach(f -> {
                        System.out.println("Discovered Class: " + f);
                    });
                    actual += discoveredClasses.size();
                    DIContainerManager.initializeContainer(discoveredClasses);
                    DIContainer.DI_CONTAINER.getBeanIdToObjectMap().forEach((k, v) -> {
                        System.out.println(k + ": " + v.toString());
                    });
                }
            }

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
