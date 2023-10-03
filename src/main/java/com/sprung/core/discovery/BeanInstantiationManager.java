package com.sprung.core.discovery;

import com.sprung.core.constants.Constants;
import com.sprung.core.container.SprungContainer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BeanInstantiationManager {

    public static Object instantiateBean(Class cl) throws InstantiationException,
            IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        System.out.println("Discovering class " + cl.getName());
        Object o = cl.newInstance();
        ClassFieldResolver classFieldResolver = new ClassFieldResolver();
        List<Field> declaredFields = new ArrayList<Field>();
        classFieldResolver.getClassFields(cl, new Stack<Class>(), declaredFields);
        for(int i = 0; i < declaredFields.size(); i++) {
            //Field f = declaredFields[i];
            Field f = declaredFields.get(i);
            System.out.println("Field: " + f.getName());
            Annotation[] fieldAnnotations = f.getDeclaredAnnotations();
            if (fieldAnnotations != null && fieldAnnotations.length > 0) {
                // Check if we have an auto-wired field annotation
                for (int j = 0; j < fieldAnnotations.length; j++) {
                    // Handle autowired annotation
                    if (fieldAnnotations[j].annotationType().getName().
                            equals(Constants.SPRUNG_COMPONENT_AUTOWIRED_ANNOTATION)) {
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
                        }
                    } else if(fieldAnnotations[j].annotationType().getName().
                            equals(Constants.SPRUNG_COMPONENT_CONFIGURATION)){
                        // Handle configuration annotation
                        // This is also a Sprung bean and we add this bean into
                        // the container map
                        Annotation fieldAnnotation = fieldAnnotations[j];
                        Class fieldClassName = f.getType();
                        Object fieldObject = null;
                        // The field has been annotated as @configuration
                        // Read the field property and initialize its value
                    }
                }
            }
        }
        return o;
    }

}
