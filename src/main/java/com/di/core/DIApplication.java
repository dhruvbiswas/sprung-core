package com.di.core;

import com.di.core.annotations.ComponentScan;
import com.di.core.container.DIContainer;
import com.di.core.discovery.ClassDiscoveryManager;
import com.di.core.discovery.DIContainerManager;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class DIApplication {

    public static void run(Class<?> clazz, String[] args) {
        // TODO: Add support to auto-discover beans even when there
        // TODO: is no @ComponentScan annotation configured
        String pkgString = clazz.getAnnotation(ComponentScan.class).pkg();
        // TODO: add error handling here
        try {
            if (pkgString != null && pkgString.length() > 0) {
                // Lets split the string
                String[] pkgs = pkgString.split(",");

                for (int i = 0; i < pkgs.length; i++) {
                    System.out.println("Discovering classes under " + pkgs[i].trim());
                    List<String> discoveredClassList = ClassDiscoveryManager.discover(pkgs[i].trim());
                    DIContainerManager.initializeContainer(discoveredClassList);
                }

            }

            // Lets keep it simple for the time being
            // Lets Create a new instance of the Class clazz
            Object o = clazz.newInstance();
            // Lets assume we are looking for the run method only for now
            Method mainMethod = clazz.getMethod("run", String[].class);
            // Lets invoke the run method on the instance
            mainMethod.invoke(o, (Object) args);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            destroy();
        }
    }

    public static void destroy() {
        DIContainerManager.destroy();
    }

    public static DIContainer getDIContainer() {
        return DIContainer.DI_CONTAINER;
    }
}
