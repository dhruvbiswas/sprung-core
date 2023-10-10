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
        String pkgString = clazz.getAnnotation(ComponentScan.class).pkg();
        // TODO: add error handling here
        try {
            System.out.println("Discovering classes under " + pkgString);
            List<String> discoveredClassList = ClassDiscoveryManager.discover(pkgString);
            DIContainerManager.initializeContainer(discoveredClassList);

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
