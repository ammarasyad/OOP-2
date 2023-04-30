package com.tll.backend.pluginhandler;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PluginContext {

    private static PluginContext pluginContext;

    private final HashMap<Class<?>, Object> objectContext;

    private PluginContext() {
        throw new UnsupportedOperationException();
    }

    public static synchronized PluginContext getInstance() {
        if (pluginContext == null) {
            pluginContext = new PluginContext(new HashMap<>());
        }
        return pluginContext;
    }

    public void addToContext(Class<?> classType, Object object) {
        objectContext.put(classType, object);
    }

    public void removeContext(Class<?> classType) {
        objectContext.remove(classType);
    }

    public Object getFromContext(Class<?> classType) {
        return objectContext.get(classType);
    }

    public Map<Class<?>, Object> getContext() {
        return objectContext;
    }


}
