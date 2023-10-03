package com.sprung.core.utils;

import com.sprung.core.constants.Constants;

import java.io.File;

public class StringUtils {

    public static String constructClassNameFromPackageNameSequence(String path, String sequence) {
        String classname = null;
        // Only interested in class files
        if (path.endsWith(Constants.FILEEXTENSION_CLASS)) {
            String[] tokens = path.split(sequence);
            String classNameAsFileName = sequence + tokens[1];
            //System.out.println("classNameAsFileName: " + classNameAsFileName);
            String[] classNameAsFileNameTokens = classNameAsFileName.split("\\" + Constants.fileNameAndExtensionSeparator);
            classname = classNameAsFileNameTokens[0].replace(File.separator, ".");
        }
        return classname;
    }

    public static String constructClassNameFromRelativePathFileName(String path) {
        String classname = null;
        // Only interested in class files
        if (path.endsWith(Constants.FILEEXTENSION_CLASS)) {
            String[] classNameAsFileNameTokens = path.split("\\" + Constants.fileNameAndExtensionSeparator);
            classname = classNameAsFileNameTokens[0].replace(File.separator, ".");
        }
        return classname;
    }

}
