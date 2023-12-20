package com.di.core.discovery;

import com.di.core.constants.Constants;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/*
 * This is work in progress, currently stuck at not being
 * able to discover src/main/resources and src/test/resources
 * while being run from inside the editor
 */
public class ClasspathDiscoveryManager {

    public static List<String> discover() throws IOException {
        List<String> classes = new ArrayList<>();
        String classpath = System.getProperty(Constants.DI_CLASSPATH_SYSTEM_PROPERTY);
        String[] paths = classpath.split(System.getProperty(Constants.DI_FILE_SEPARATOR_SYSTEM_PROPERTY));
        for (int i = 0; i < paths.length; i++) {
            ClasspathDiscoveryManager.findFiles(paths[i], classes);
        }
        return classes;
    }

    public static void findFiles(String path,
                                 List<String> fileList) throws IOException {
        File file = new File(path);
        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File discoveredFile = files[i];
                findFiles(discoveredFile.getAbsolutePath(), fileList);
            }
        } else if (file.exists() && file.isFile()) {
            if (file.getName().endsWith(".jar")) {
                JarFile jf = new JarFile(file.getAbsolutePath());
                Enumeration<JarEntry> jarEntries = jf.entries();
                while (jarEntries.hasMoreElements()) {
                    String entryName = jarEntries.nextElement().getName();
                    // Append the full path to the jarfile name
                    fileList.add(file.getAbsolutePath() + ":" + entryName);
                }
            }
        }
    }

    public static boolean isPathInClasspath(String path, List<String> fileList) {
        fileList.forEach(f -> {
            if(f.contains(path)) {
                System.out.println("Found " + path + " in " + f);
            }
        });
        return true;
    }

}
