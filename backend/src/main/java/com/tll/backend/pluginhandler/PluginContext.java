package com.tll.backend.pluginhandler;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PluginContext {

    private static PluginContext pluginContext;

    private final HashMap<String, Object> objectContext;

    private PluginContext() {
        throw new UnsupportedOperationException();
    }

    public static synchronized PluginContext getInstance() {
        if (pluginContext == null) {
            pluginContext = new PluginContext(new HashMap<>());
        }
        return pluginContext;
    }

    public void addToContext(String identifier, Object object) {
        objectContext.put(identifier, object);
    }

    public void removeContext(String identifier) {
        objectContext.remove(identifier);
    }

    public <T> T getFromContext(String identifier, Class<T> classType) {
        return classType.cast(objectContext.get(identifier));
    }

    public Map<String, Object> getContext() {
        return objectContext;
    }


}
