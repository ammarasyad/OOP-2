package com.tll.backend.pluginhandler;

import com.tll.plugin.Plugin;

import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PluginLoader {

    public Plugin load(String path, String jarName) {
        try (JarFile jarFile = new JarFile(Paths.get(path, jarName).toFile())) {

            Enumeration<JarEntry> entries = jarFile.entries();

            PluginContext pluginContext = PluginContext.getInstance();
            URL[] urls = { new URL("jar:file:" + Paths.get(path,jarName) +"!/") };
            URLClassLoader cl = URLClassLoader.newInstance(urls);
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                if (jarEntry == null) {
                    break;
                }
                if (jarEntry.getName().endsWith(".class") && !jarEntry.getName().startsWith("module-info")) {
                    String className = jarEntry.getName().substring(0,jarEntry.getName().length()-6);
                    className = className.replace('/', '.');
                    System.out.println(className);
                    Class<?> classLoad = cl.loadClass(className);
                    var obj = classLoad.getDeclaredConstructor().newInstance();
                    if (classLoad.isInterface() || !(obj instanceof Plugin))
                        continue;
                    pluginContext.addToContext(String.valueOf(classLoad), null);
                    return (Plugin) obj;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
