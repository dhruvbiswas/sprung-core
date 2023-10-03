package com.sprung.core;

import com.sprung.core.annotations.SprungComponentScan;
import com.sprung.core.container.SprungContainer;
import com.sprung.core.discovery.ClassDiscoveryManager;
import com.sprung.core.discovery.SprungContainerInitiationManager;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class SprungApplication {

    public static void run(Class<?> clazz, String[] args) {
        String pkgString = clazz.getAnnotation(SprungComponentScan.class).pkg();
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
        }
    }

    public static SprungContainer getSprungContainer() {
        return SprungContainer.SPRUNG_CONTAINER;
    }
}
