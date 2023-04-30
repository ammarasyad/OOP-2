package com.tll.backend.pluginhandler;

import com.tll.annotation.AutoWired;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;


public class PluginResolver {

    public void injectDependency(Class<?> classType, Object object) {
        PluginContext pluginContext = PluginContext.getInstance();
        for(Field field: classType.cast(object).getClass().getDeclaredFields() ){
            if (field.isAnnotationPresent(AutoWired.class)){
                field.setAccessible(true);
                Class<?> fieldType = field.getClass();
                Object value = pluginContext.getFromContext(fieldType);
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
