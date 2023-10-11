package com.di.core.lib.test;

import com.di.core.annotations.Component;
import com.di.core.annotations.Value;

@Component
public class AppClass1 {

    private String className = this.getClass().getName();

    @Value("some var in AppClass1 that I annotated")
    private String somevar;

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("classname: " + className + "|");
        builder.append("somevar: " + this.somevar);
        return builder.toString();
    }

}