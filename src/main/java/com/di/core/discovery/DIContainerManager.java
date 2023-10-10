package com.di.core.discovery;

import com.di.core.DIApplication;
import com.di.core.constants.Constants;
import com.di.core.container.DIContainer;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class DIContainerManager {

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
                        // We found a class annotated as a DI component
                        IBeanIDGenerator beanIDGenerator = new DefaultBeanIDGenerator();
                        DIContainer.DI_CONTAINER.addToClassNameToClassMap(
                                beanIDGenerator.generateBeanId(cl, cl_annotation), cl);
                        // Check if the class has already been initialized inside the container
                        if (!DIContainer.DI_CONTAINER.hasBean(cName)) {
                            // TODO: Add constructor injection support
                            BeanInstantiationManager.instantiateBean(cl, cl_annotation);
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

    public static void destroy() {
        DIApplication.getDIContainer().getClassNameToObjectMap().forEach((k, v) -> {
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

}
