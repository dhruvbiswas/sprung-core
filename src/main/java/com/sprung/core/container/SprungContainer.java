package com.sprung.core.container;

import java.util.HashMap;
import java.util.Map;

// TODO: this should be re-factored
// Currently the Container does not support a good DS
// that holds bean object mappings
// The container should support tuple based querying instead
// of having a flat map of objects
// The objects in the container should be queryable based on
// what object is a component, what object is a bean, what object
// is a config. It should support an object graph as well as
// separate maps for each of these object types
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

    public Map<String, Object> getClassNameToObjectMap() {
        return classNameToObjectMap;
    }

    public void setClassNameToObjectMap(Map<String, Object> classNameToObjectMap) {
        this.classNameToObjectMap = classNameToObjectMap;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        SprungContainer.SPRUNG_CONTAINER.classNameToObjectMap.forEach((k, v) -> {
            System.out.println(k + ": " + v.toString());
        });
        return builder.toString();
    }
}
