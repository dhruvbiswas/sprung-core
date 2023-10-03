package com.sprung.core.context;

import java.util.HashMap;
import java.util.Map;

public enum SprungContext {

    SPRUNG_CONTEXT;

    private Map<String, Class> componentClassMap = new HashMap<>();

    public synchronized void addToComponentClassMap(String classname, Class c) {
        this.componentClassMap.put(classname, c);
    }

    public Map<String, Class> getComponentClassMap() {
        return componentClassMap;
    }

    public static SprungContext getSprungContext() {
        return SprungContext.SPRUNG_CONTEXT;
    }

}
