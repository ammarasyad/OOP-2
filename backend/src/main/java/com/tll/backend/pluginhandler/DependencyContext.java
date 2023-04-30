package com.tll.backend.pluginhandler;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DependencyContext {

    private static DependencyContext dependencyContext;

    private final HashMap<Class<?>, Object> objectContext;

    private DependencyContext() {
        throw new UnsupportedOperationException();
    }

    public static synchronized DependencyContext getInstance() {
        if (dependencyContext == null) {
            dependencyContext = new DependencyContext(new HashMap<>());
        }
        return dependencyContext;
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
