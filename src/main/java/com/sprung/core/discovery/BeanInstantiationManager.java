package com.sprung.core.discovery;

import com.sprung.core.constants.Constants;
import com.sprung.core.container.SprungContainer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BeanInstantiationManager {

    /*
     * Depth first to instantiate a bean
     */
    public static Object instantiateBean(Class cl) throws InstantiationException,
            IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        System.out.println("Discovering class " + cl.getName());
        Object o = cl.newInstance();
        ClassFieldResolver classFieldResolver = new ClassFieldResolver();
        List<Field> declaredFields = new ArrayList<>();
        classFieldResolver.getClassFields(cl, new Stack<>(), declaredFields);
        for(int i = 0; i < declaredFields.size(); i++) {
            //Field f = declaredFields[i];
            Field f = declaredFields.get(i);
            System.out.println("Field: " + f.getName());
            Annotation[] fieldAnnotations = f.getDeclaredAnnotations();
            if (fieldAnnotations != null && fieldAnnotations.length > 0) {
                // Check if we have an auto-wired field annotation
                for (int j = 0; j < fieldAnnotations.length; j++) {
                    // @autowired annotation
                    if (fieldAnnotations[j].annotationType().getName().
                            equals(Constants.AUTOWIRED_ANNOTATION)) {
                        // Check if the annotation has an isMandatory
                        // attribute
                        Annotation fieldAnnotation = fieldAnnotations[j];
                        Boolean isMandatory = (Boolean)fieldAnnotation.annotationType().getMethod(
                                "required").invoke(fieldAnnotation);
                        if (isMandatory) {
                            Class fieldClassName = f.getType();
                            Object fieldObject = null;
                            //Check if the classname for the field has already been initialized
                            if (SprungContainer.SPRUNG_CONTAINER.isObjectPresent(
                                    fieldClassName.getName())) {
                                fieldObject = SprungContainer.SPRUNG_CONTAINER.getObject(
                                        fieldClassName.getName());
                            } else {
                                // the class that this field points to is not in the container
                                fieldObject = instantiateBean(fieldClassName);
                            }
                            f.setAccessible(true);
                            // Set the field 'f' in the object 'o' using dftObject
                            f.set(o, fieldObject);

                        } else {
                            // TODO: This should refactored
                            System.out.println("Skip auto-wire as isMandatory attribute on @Autowired annotation was set to false");
                        }
                    } else if(fieldAnnotations[j].annotationType().getName().
                            equals(Constants.VALUE_ANNOTATION)) {
                        // Handle @value annotation on a field
                        Annotation fieldAnnotation = fieldAnnotations[j];
                        String value = (String)fieldAnnotation.annotationType().getMethod("value").invoke(fieldAnnotation);
                        f.setAccessible(true);
                        f.set(o, value);
                    }
                }
            }
        }
        // Execute Bean-Lifecycle methods
        // Check if the object implements InitializingBean interface
        ClassInterfaceResolver classInterfaceResolver = new ClassInterfaceResolver(cl);
        if (classInterfaceResolver.getNumImplementedInterfaces() > 0) {
            System.out.println(classInterfaceResolver.toString());
        }
        if (classInterfaceResolver.hasImplemented(Constants.INITIALIZING_BEAN_CLASSNAME)) {
            System.out.println("Well I got a bean that implements " + Constants.INITIALIZING_BEAN_CLASSNAME);
            // Find an after-properties set method
            ClassMethodResolver classMethodResolver = new ClassMethodResolver(cl);
            if (classMethodResolver.getNumImplementedMethods() > 0) {
                System.out.println(classMethodResolver.toString());
                if (classMethodResolver.hasmethod(Constants.INITIALIZING_BEAN_AFTER_PROPERTIES_METHOD_NAME)) {
                    // Invoke bean-lifecycle method
                    Method beanLifecyleAfterPropertiesMethod = cl.getMethod(Constants.INITIALIZING_BEAN_AFTER_PROPERTIES_METHOD_NAME, null);
                    beanLifecyleAfterPropertiesMethod.invoke(o, null);
                }
            }
        }
        return o;
    }

}
