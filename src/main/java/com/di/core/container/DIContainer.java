package com.di.core.container;

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
public enum DIContainer {

    DI_CONTAINER;

    private Map<String, Class> classNameToClassMap = new HashMap<String, Class>();
    private Map<String, Object> beanIdToObjectMap = new HashMap<String, Object>();
    private Map<String, String> typeToClassNameMap = new HashMap<String, String>();

    public synchronized void addToClassNameToClassMap(String classname, Class clazz) {
        DIContainer.DI_CONTAINER.classNameToClassMap.put(classname, clazz);
    }

    public synchronized void addToBeanIdToObjectMap(String c, Object o) {
        DIContainer.DI_CONTAINER.beanIdToObjectMap.put(c, o);
    }

    public synchronized void addToTypeToClassNameMap(String type, String c) {
        this.typeToClassNameMap.put(type, c);
    }

    public synchronized boolean hasBean(String classname) {
        if(DIContainer.DI_CONTAINER.beanIdToObjectMap.containsKey(classname)) {
            return true;
        } else {
            return false;
        }
    }

    public synchronized Object getBean(String classname) {
        return DIContainer.DI_CONTAINER.beanIdToObjectMap.get(classname);
    }

    public Map<String, Object> getBeanIdToObjectMap() {
        return beanIdToObjectMap;
    }

    public void setBeanIdToObjectMap(Map<String, Object> classNameToObjectMap) {
        this.beanIdToObjectMap = classNameToObjectMap;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        DIContainer.DI_CONTAINER.beanIdToObjectMap.forEach((k, v) -> {
            System.out.println(k + ": " + v.toString());
        });
        return builder.toString();
    }
}
