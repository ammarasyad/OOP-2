package com.tll.backend.pluginhandler;

import com.tll.annotation.AutoWired;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;


public class DependencyInjector {

    public void injectDependency(Object object) {
        DependencyContext dependencyContext = DependencyContext.getInstance();
        for(Field field: object.getClass().getDeclaredFields()){
            if (field.isAnnotationPresent(AutoWired.class)){
                field.setAccessible(true);
                Class<?> fieldType = field.getClass();
                Object value = dependencyContext.getFromContext(fieldType);
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
