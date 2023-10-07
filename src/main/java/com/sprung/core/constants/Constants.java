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

    // Annotations
    public static final String SPRUNG_COMPONENT_SCAN_ANNOTATION = "com.sprung.core.annotations.SprungComponentScan";
    public static final String SPRUNG_COMPONENT_ANNOTATION = "com.sprung.core.annotations.SprungComponent";
    public static final String SPRUNG_COMPONENT_AUTOWIRED_ANNOTATION = "com.sprung.core.annotations.AutoWired";
    public static final String SPRUNG_COMPONENT_CONFIGURATION = "com.sprung.core.annotations.Configuration";
    public static final String SPRUNG_COMPONENT_VALUE = "com.sprung.core.annotations.Value";

}
