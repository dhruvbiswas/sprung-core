package com.di.core.lib.test;

import com.di.core.annotations.AutoWired;
import com.di.core.annotations.Component;
import com.di.core.annotations.Value;

@Component
public class AppClass2 {

    private String className = this.getClass().getName();

    @AutoWired
    private AppClass1 appClass1;

    @Value("some var in AppClass2 that I annotated")
    private String somevar;

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("classname: " + className + "|");
        builder.append("somevar: " + this.somevar + "\n");
        builder.append(appClass1.toString());
        return builder.toString();
    }

}
