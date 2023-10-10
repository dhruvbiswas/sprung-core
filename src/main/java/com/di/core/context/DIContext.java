package com.di.core.context;

import java.util.HashMap;
import java.util.Map;

public enum DIContext {

    DI_CONTEXT;

    private Map<String, Class> componentClassMap = new HashMap<>();

    public synchronized void addToComponentClassMap(String classname, Class c) {
        this.componentClassMap.put(classname, c);
    }

    public Map<String, Class> getComponentClassMap() {
        return componentClassMap;
    }

    public static DIContext getDiContext() {
        return DIContext.DI_CONTEXT;
    }

}
