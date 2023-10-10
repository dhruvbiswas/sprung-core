package com.sprung.core.discovery;

import com.sprung.core.constants.Constants;
import com.sprung.core.container.SprungContainer;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class SprungContainerInitiationManager {

    // This method takes a list of flattened classes
    // Then runs through the list of classes and checks if any class
    // has been annotated as
    // @Component
    public static void initializeContainer(List<String> discoveredClassList) {
        try {
            for (int i = 0; i < discoveredClassList.size(); i++) {
                String cName = discoveredClassList.get(i);
                Class cl = Class.forName(discoveredClassList.get(i));
                Annotation[] cl_annotations = cl.getAnnotations();
                // Discover annotations in each class and check if
                // the classes have been annotated as Component
                for (int j = 0; j < cl_annotations.length; j++) {
                    Annotation cl_annotation = cl_annotations[j];
                    //System.out.println("Name: " + cl_annotation.annotationType().getName());
                    // For now we are interested in classes that have SprunComponent Annotation
                    if (cl_annotation != null &&
                            cl_annotation.annotationType().getName().equals(
                                    Constants.COMPONENT_ANNOTATION)) {
                        //System.out.println("Class Name: " + cl.getName());
                        // We found a class annotated as sprung component
                        SprungContainer.SPRUNG_CONTAINER.addToClassNameToClassMap(cName, cl);
                        // Check if the class has already been initialized inside the container
                        if (!SprungContainer.SPRUNG_CONTAINER.isObjectPresent(cName)) {
                            // Instantiate using default no-argument constructor
                            Object o = BeanInstantiationManager.instantiateBean(cl);
                            SprungContainer.SPRUNG_CONTAINER.addToClassNameToObjectMap(cName, o);
                        }
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

}
