package com.sprung.core.constants;

import java.util.regex.Pattern;

public interface Constants {

    public static final String SPRUNG = "SPRUNG";
    public static final String CORE = "CORE";

    public static final String fileNameAndExtensionSeparator = ".";

    public static final String FILEEXTENSION_CLASS = "class";
    public static final String REGEXP_CLASS = "class$";
    public static final Pattern regExpPattern = Pattern.compile(REGEXP_CLASS);

    // System Properties
    public static final String SPRUNG_CLASSPATH_SYSTEM_PROPERTY = "java.class.path";
    public static final String SPRUNG_FILE_SEPARATOR_SYSTEM_PROPERTY = "path.separator";

    public static final String CORE_ANNOTATION_PACKAGE_PREFIX = "com.sprung.core.annotations";

    // Annotations
    public static final String COMPONENT_SCAN_ANNOTATION = CORE_ANNOTATION_PACKAGE_PREFIX + fileNameAndExtensionSeparator + "ComponentScan";
    public static final String COMPONENT_ANNOTATION = CORE_ANNOTATION_PACKAGE_PREFIX + fileNameAndExtensionSeparator + "Component";
    public static final String AUTOWIRED_ANNOTATION = CORE_ANNOTATION_PACKAGE_PREFIX + fileNameAndExtensionSeparator + "AutoWired";
    public static final String CONFIGURATION_ANNOTATION = CORE_ANNOTATION_PACKAGE_PREFIX + fileNameAndExtensionSeparator + "Configuration";
    public static final String VALUE_ANNOTATION = CORE_ANNOTATION_PACKAGE_PREFIX + fileNameAndExtensionSeparator + "Value";

    // Bean Lifecycle class names
    public static final String INITIALIZING_BEAN_CLASSNAME = "com.sprung.core.container.InitializingBean";
    public static final String DISPOSABLE_BEAN_CLASSNAME = "com.sprung.core.container.DisposableBean";

    // Bean lifecycle method names
    public static final String INITIALIZING_BEAN_AFTER_PROPERTIES_METHOD_NAME = "afterPropertiesSet";
    public static final String DISPOSABLE_BEAN_DESTROY_METHOD_NAME = "destroy";

}
