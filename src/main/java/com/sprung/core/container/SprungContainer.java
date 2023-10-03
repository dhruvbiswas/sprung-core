package com.sprung.core.container;

import java.util.HashMap;
import java.util.Map;

public enum SprungContainer {

    SPRUNG_CONTAINER;

    private Map<String, Class> classNameToClassMap = new HashMap<String, Class>();
    private Map<String, Object> classNameToObjectMap = new HashMap<String, Object>();
    private Map<String, String> typeToClassNameMap = new HashMap<String, String>();

    public synchronized void addToClassNameToClassMap(String classname, Class clazz) {
        SprungContainer.SPRUNG_CONTAINER.classNameToClassMap.put(classname, clazz);
    }

    public synchronized void addToClassNameToObjectMap(String c, Object o) {
        SprungContainer.SPRUNG_CONTAINER.classNameToObjectMap.put(c, o);
    }

    public synchronized void addToTypeToClassNameMap(String type, String c) {
        this.typeToClassNameMap.put(type, c);
    }

    public synchronized boolean isObjectPresent(String classname) {
        if(SprungContainer.SPRUNG_CONTAINER.classNameToObjectMap.containsKey(classname)) {
            return true;
        } else {
            return false;
        }
    }

    public synchronized Object getObject(String classname) {
        return SprungContainer.SPRUNG_CONTAINER.classNameToObjectMap.get(classname);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        SprungContainer.SPRUNG_CONTAINER.classNameToObjectMap.forEach((k, v) -> {
            System.out.println(k + ": " + v.toString());
        });
        return builder.toString();
    }
}
