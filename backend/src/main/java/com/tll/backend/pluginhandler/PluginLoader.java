package com.tll.backend.pluginhandler;


import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class PluginLoader {

    public void load(String path, String jarName) {
        try {
            JarInputStream jarFile = new JarInputStream(Files.newInputStream(Paths.get(path, jarName)));
            JarEntry jarEntry;
            DependencyContext dependencyContext = DependencyContext.getInstance();
            URL[] urls = { new URL("jar:file:" + path +"!/") };
            URLClassLoader cl = URLClassLoader.newInstance(urls);
            while (true) {
                jarEntry = jarFile.getNextJarEntry();
                if (jarEntry == null) {
                    break;
                }
                if (jarEntry.getName().endsWith(".class")) {
                    String className = jarEntry.getName().substring(0,jarEntry.getName().length()-6);
                    className = className.replace('/', '.');
                    dependencyContext.addToContext(cl.loadClass(className), null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
