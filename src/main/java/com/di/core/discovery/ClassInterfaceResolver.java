package com.di.core.discovery;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ClassInterfaceResolver {

    private Map<String, Class> implementedInterfacesMap = new HashMap<>();
    private int numImplementedInterfaces = 0;

    public ClassInterfaceResolver(Class cl) {
        Class[] implementingInterfaces = cl.getInterfaces();
        if (implementingInterfaces != null && implementingInterfaces.length > 0) {
            numImplementedInterfaces = implementingInterfaces.length;
            for (Class implementingInterface : implementingInterfaces) {
                this.implementedInterfacesMap.put(implementingInterface.getName(), implementingInterface);
            }
        }
    }

    public boolean hasImplemented(String classname) {
        return this.implementedInterfacesMap.containsKey(classname);
    }

    public List<String> getInterfaces() {
        List<String> implementedInterfaces = new ArrayList<>();
        implementedInterfacesMap.forEach((k, v) -> {
            implementedInterfaces.add(k);
        });
        return implementedInterfaces;
    }

    public int getNumImplementedInterfaces() {
        return numImplementedInterfaces;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("interfaces:");
        this.implementedInterfacesMap.forEach((k, v) -> {
            builder.append(k + ",");
        });
        builder.append("\n");
        return builder.toString();
    }

}
