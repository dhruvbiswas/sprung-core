package com.di.core.discovery;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ClassMethodResolver {

    private Map<String, Class> implementedMethodMap = new HashMap<>();
    private int numImplementedMethods = 0;

    public ClassMethodResolver(Class cl) {
        Method[] implementedMethods = cl.getMethods();
        if(implementedMethods != null && implementedMethods.length > 0) {
            this.numImplementedMethods = implementedMethods.length;
            for (Method implementedMethod : implementedMethods) {
                this.implementedMethodMap.put(implementedMethod.getName(), cl);
            }
        }
    }

    public boolean hasmethod(String methodName) {
        return this.implementedMethodMap.containsKey(methodName);
    }

    public List<String> getMethods() {
        List<String> implementedMethods = new ArrayList<>();
        this.implementedMethodMap.forEach((k, v) -> {
            implementedMethods.add(k);
        });
        return implementedMethods;
    }

    public int getNumImplementedMethods() {
        return numImplementedMethods;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("methods:");
        implementedMethodMap.forEach((k, v) -> {
            stringBuilder.append(k + ",");
        });
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

}
