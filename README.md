DI-Core
======

DI-Core is an experimental Spring like Dependency Injection framework that is a work-in-progress.

- For now the framework supports the following annotations
  - ComponentScan
  - Component
  - Autowired
  - Value
  - Configuration (Basic Preliminary Implementation Complete)
  - PropertySource (Work In Progress)

The source base is an original implementation and no part of the source was copied from actual Spring.
The only set of files that have been picked from Spring are the actual annotation files just to be consistent
with Spring's original annotation definitions. 

- To build DI-Core
  - mvn clean install

- Kindly visit the following file to check DI-Core Dependency Injection Framework being used in a sample application
  - https://github.com/dhruvbiswas/sample/blob/develop/src/main/java/com/sample/app/Main.java

Sample usage of ComponentScan in an application

package com.sample.app;

import com.sample.app.lib.AppClass1;
import com.sample.app.lib.AppClass3;
import com.sample.app.lib.AppClass4;
import com.sample.app.lib.AppClass5;
import com.di.core.DIApplication;
import com.di.core.annotations.ComponentScan;
import com.di.core.runners.CommandLineRunner;

import java.io.IOException;

@ComponentScan(pkg = "com.sample.app.lib")
public class Main extends CommandLineRunner {

    public static void main(String[] args) throws IOException {
        DIApplication.run(Main.class, args);
    }

    @Override
    public void run(String[] args) throws Exception {
        System.out.println("Executing main class.....");
        System.out.println("Dumping Container Classes.....");
        System.out.println(DIApplication.getDIContainer().toString());
        System.out.println("Done Dumping Container Classes.....");

        // TODO: Currently there is no XML support for creating bean-container
        // TODO: Only annotations are supported for DI-Autowiring
        // TODO: The key into the object factory is an FQ class
        // TODO: There should be a proper/better-way to generate bean-ids when using
        // TODO: annotation based DI-Autowiring

        AppClass1 appClass1 = (AppClass1) DIApplication.getDIContainer().getBean(
                AppClass1.class.getCanonicalName());
        System.out.println("I got an autowired class inside the container...." + appClass1.toString());
        AppClass3 appClass3 = (AppClass3) DIApplication.getDIContainer().
                getBean(AppClass3.class.getCanonicalName());
        System.out.println(appClass3.toString());

        AppClass4 appClass4 = (AppClass4) DIApplication.getDIContainer().
                getBean(AppClass4.class.getCanonicalName());
        System.out.println(appClass4.toString());

        // We used a custom name for this bean
        AppClass5 appClass5 = (AppClass5) DIApplication.getDIContainer().
                getBean("AppClass5Bean");
        System.out.println(appClass5.toString());
    }

}
