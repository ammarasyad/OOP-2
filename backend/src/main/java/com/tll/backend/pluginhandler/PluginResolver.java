package com.tll.backend.pluginhandler;

import com.tll.plugin.AutoWired;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;


public class PluginResolver {

    public <T> void injectPluginDependency(T object) {
        PluginContext pluginContext = PluginContext.getInstance();
        for(Field field: object.getClass().getDeclaredFields()){
            if (field.isAnnotationPresent(AutoWired.class)){
                AutoWired wiredReq = field.getAnnotation(AutoWired.class);
                field.setAccessible(true);

                Class<?> fieldType = field.getClass();
                String identifier = wiredReq.identifier();
                if (wiredReq.identifier().isBlank()) {
                    identifier = fieldType.getSimpleName();
                }

                var value = pluginContext.getFromContext(identifier, field.getType());
                try {
                    if (value == null) {
                        value = fieldType.getDeclaredConstructor().newInstance();
                    }
                    field.set(object, value);
                } catch (IllegalAccessException | InstantiationException | InvocationTargetException |
                         NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
