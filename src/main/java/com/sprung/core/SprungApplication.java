package com.sprung.core;

import com.sprung.core.annotations.ComponentScan;
import com.sprung.core.constants.Constants;
import com.sprung.core.container.SprungContainer;
import com.sprung.core.discovery.ClassDiscoveryManager;
import com.sprung.core.discovery.ClassInterfaceResolver;
import com.sprung.core.discovery.SprungContainerInitiationManager;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class SprungApplication {

    public static void run(Class<?> clazz, String[] args) {
        String pkgString = clazz.getAnnotation(ComponentScan.class).pkg();
        // TODO: add error handling here
        try {
            System.out.println("Discovering classes under " + pkgString);
            List<String> discoveredClassList = ClassDiscoveryManager.discover(pkgString);
            SprungContainerInitiationManager.initializeContainer(discoveredClassList);

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
        SprungApplication.getSprungContainer().getClassNameToObjectMap().forEach((k, v) -> {
            // For each object in the container check if the object implements
            // a Disposable bean interface, if the object implements that interface
            // call its destroy method
            Class cl = v.getClass();
            System.out.println("What class is in the objectmap: " + cl.getCanonicalName());
            ClassInterfaceResolver classInterfaceResolver = new ClassInterfaceResolver(cl);
            if (classInterfaceResolver.hasImplemented(Constants.DISPOSABLE_BEAN_CLASSNAME)) {
                // Invoke disposable bean destroy method
                try {
                    System.out.println("Invoking bean lifecycle method for " + cl.getCanonicalName());
                    Method disposableBeanLifecycleMethod = cl.getMethod(Constants.DISPOSABLE_BEAN_DESTROY_METHOD_NAME, null);
                    disposableBeanLifecycleMethod.invoke(v, null);
                } catch (NoSuchMethodException e) {
                    // TODO: Add logging
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public static SprungContainer getSprungContainer() {
        return SprungContainer.SPRUNG_CONTAINER;
    }
}
