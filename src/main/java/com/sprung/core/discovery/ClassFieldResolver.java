package com.sprung.core.discovery;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Stack;

public class ClassFieldResolver {

    public void getClassFields(Class clazz,
                               Stack<Class> classFieldResolverStack,
                               List<Field> classFieldsList) {
        classFieldResolverStack.push(clazz);
        if (clazz.getSuperclass() != null) {
            this.getClassFields(clazz.getSuperclass(), classFieldResolverStack, classFieldsList);
        } else {
            // Reached root node
            // pop each item from stack and build a list of fields
            while (!classFieldResolverStack.empty()) {
                Class poppedClazz = classFieldResolverStack.pop();
                Field[] poppedClazzFields = poppedClazz.getDeclaredFields();
                for (int i = 0; i < poppedClazzFields.length; i++) {
                    classFieldsList.add(poppedClazzFields[i]);
                }
            }
        }
    }
}
