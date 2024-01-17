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

- To build sprung-core
  - mvn clean install

- Kindly visit the following file to check Sprung-Core Dependency Injection Framework being used in a sample application
  - https://github.com/dhruvbiswas/sample/blob/develop/src/main/java/com/sample/app/Main.java
