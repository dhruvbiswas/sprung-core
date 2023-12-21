package com.di.core.discovery;

import com.di.core.constants.Constants;
import com.di.core.utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassDiscoveryManager {

    public static List<String> discover(String packageName) throws IOException {
        List<String> classes = new ArrayList<String>();
        String classpath = System.getProperty(Constants.DI_CLASSPATH_SYSTEM_PROPERTY);
        String[] paths = classpath.split(System.getProperty(Constants.DI_FILE_SEPARATOR_SYSTEM_PROPERTY));
        packageName = packageName.replace(".", "/");
        for(int i = 0; i < paths.length; i++) {
            ClassDiscoveryManager.findClasses(packageName, paths[i], classes);
        }
        return classes;
    }

    public static void findClasses(String packageName,
                                   String path,
                                   List<String> classes) throws IOException {
        File file = new File(path);
        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File discoveredFile = files[i];
                findClasses(packageName, discoveredFile.getAbsolutePath(), classes);
            }
        } else if (file.exists() && file.isFile()) {
            //For now lets use an extension based approach (we are only interested in .class and .jar files)
            if (file.getName().endsWith(".class")) {
                if (packageName.equals("")) {
                    String classNameDerivedFromFileAbsolutePath =
                            StringUtils.constructClassNameFromPackageNameSequence(file.getAbsolutePath(),
                                    packageName);
                    if(classNameDerivedFromFileAbsolutePath != null) {
                        classes.add(classNameDerivedFromFileAbsolutePath);
                    }
                } else {
                    if (file.getAbsolutePath().contains(packageName)) {
                        String classNameDerivedFromFileAbsolutePath =
                                StringUtils.constructClassNameFromPackageNameSequence(file.getAbsolutePath(),
                                        packageName);
                        if(classNameDerivedFromFileAbsolutePath != null) {
                            classes.add(classNameDerivedFromFileAbsolutePath);
                        }
                    }
                }
            } else if (file.getName().endsWith(".jar")) {
                JarFile  jf = new JarFile(file.getAbsolutePath());
                Enumeration<JarEntry> jarEntries = jf.entries();
                while (jarEntries.hasMoreElements()) {
                    if(packageName.equals("")) {
                        String entryName = jarEntries.nextElement().getName();
                        String classNameDerivedFromFileRelativePath =
                                StringUtils.constructClassNameFromRelativePathFileName(entryName);
                        if(classNameDerivedFromFileRelativePath != null) {
                            classes.add(classNameDerivedFromFileRelativePath);
                        }
                    } else {
                        String entryName = jarEntries.nextElement().getName();
                        if (entryName.endsWith(".class") && entryName.contains(packageName)) {
                            String classNameDerivedFromFileRelativePath =
                                    StringUtils.constructClassNameFromRelativePathFileName(entryName);
                            if(classNameDerivedFromFileRelativePath != null) {
                                classes.add(classNameDerivedFromFileRelativePath);
                            }
                        }
                    }
                }
            }
        }
    }
}
