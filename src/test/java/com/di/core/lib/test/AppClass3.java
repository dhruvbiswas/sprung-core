package com.di.core.lib.test;

import com.di.core.annotations.Component;
import com.di.core.annotations.Value;

public class AppClass3 {

    private String className = this.getClass().getName();

    @Value("some var in AppClass3 that I annotated")
    private String somevar;

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("classname: " + className + "|");
        builder.append("somevar: " + this.somevar);
        return builder.toString();
    }

}