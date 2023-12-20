package com.di.core.lib.test;

import com.di.core.annotations.AutoWired;
import com.di.core.container.DisposableBean;
import com.di.core.container.InitializingBean;

public class AppClass6 implements InitializingBean, DisposableBean {

    private String classname = AppClass6.class.getCanonicalName();

    @AutoWired
    private AppClass2 appClass2;

    public String toString() {
        return "I autowired AppClass2 instance: " +
                appClass2.toString() + " inside " + classname + " instance\n";
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Called afterPropertiesSet bean-lifecycle method from inside " +
                classname + " instance");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("Called destroy bean-lifecycle method from inside " +
                classname + " instance");
    }
}
