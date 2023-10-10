package com.di.core.discovery;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

public class DefaultBeanIDGenerator implements IBeanIDGenerator {
    @Override
    public String generateBeanId(Class cl, Annotation cl_annotation) {
        String annotation_value = "";
        try {
            annotation_value = (String)cl_annotation.annotationType().
                    getMethod("value").invoke(cl_annotation);
        } catch (IllegalAccessException e) {
            System.out.println("Could not retrieve annotation value");
        } catch (InvocationTargetException e) {
            System.out.println("Could not retrieve annotation value");
        } catch (NoSuchMethodException e) {
            System.out.println("Could not retrieve annotation value");
        }
        // TODO: add a better check
        if (annotation_value != null && annotation_value.length() > 0) {
            return annotation_value;
        } else {
            return cl.getCanonicalName();
        }
    }
}
