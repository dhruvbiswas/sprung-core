package com.di.core.constants;

import java.util.regex.Pattern;

public interface Constants {

    String DI = "DI";
    String CORE = "CORE";

    String fileNameAndExtensionSeparator = ".";
    String componentScanPackageNameSeparator = ",";

    String FILEEXTENSION_CLASS = "class";
    String REGEXP_CLASS = "class$";
    Pattern regExpPattern = Pattern.compile(REGEXP_CLASS);

    // System Properties
    String DI_CLASSPATH_SYSTEM_PROPERTY = "java.class.path";
    String DI_FILE_SEPARATOR_SYSTEM_PROPERTY = "path.separator";

    String CORE_ANNOTATION_PACKAGE_PREFIX = "com.di.core.annotations";

    // Annotations
    String COMPONENT_SCAN_ANNOTATION = CORE_ANNOTATION_PACKAGE_PREFIX + fileNameAndExtensionSeparator + "ComponentScan";
    String COMPONENT_ANNOTATION = CORE_ANNOTATION_PACKAGE_PREFIX + fileNameAndExtensionSeparator + "Component";
    String AUTOWIRED_ANNOTATION = CORE_ANNOTATION_PACKAGE_PREFIX + fileNameAndExtensionSeparator + "AutoWired";
    String CONFIGURATION_ANNOTATION = CORE_ANNOTATION_PACKAGE_PREFIX + fileNameAndExtensionSeparator + "Configuration";
    String VALUE_ANNOTATION = CORE_ANNOTATION_PACKAGE_PREFIX + fileNameAndExtensionSeparator + "Value";
    String BEAN_ANNOTATION = CORE_ANNOTATION_PACKAGE_PREFIX + fileNameAndExtensionSeparator + "Bean";

    // Bean Lifecycle class names
    String INITIALIZING_BEAN_CLASSNAME = "com.di.core.container.InitializingBean";
    String DISPOSABLE_BEAN_CLASSNAME = "com.di.core.container.DisposableBean";

    // Bean lifecycle method names
    String INITIALIZING_BEAN_AFTER_PROPERTIES_METHOD_NAME = "afterPropertiesSet";
    String DISPOSABLE_BEAN_DESTROY_METHOD_NAME = "destroy";

}
