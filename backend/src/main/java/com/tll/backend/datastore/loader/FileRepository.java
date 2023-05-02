package com.tll.backend.datastore.loader;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.tll.backend.datastore.DataAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

public class FileRepository {

    /**
     * Saves data to a file.
     *
     * @param object   The object to be saved.
     * @param dataAdapter The adapter to be used to save the file.
     * @throws IOException If the file type is not supported.
     */
    public static void save(@NotNull List<?> object, DataAdapter dataAdapter) throws IOException {
        dataAdapter.save(object);
    }

    /**
     * Loads data from a file.
     *
     * @param clazz    The class of the objects to be loaded. The class must have a default constructor.
     * @param dataAdapter The adapter to be used to load the file.
     * @throws IOException If the file type is not supported.
     */
    public static <T> List<T> load(@NotNull Class<T> clazz, DataAdapter dataAdapter) throws IOException {
        return dataAdapter.load(CollectionsTypeFactory.listOf(clazz));
    }

    // Nested private class for creating collection types, for Single Responsibility Principle
    private static class CollectionsTypeFactory {
        static JavaType listOf(Class<?> clazz) {
            return TypeFactory.defaultInstance().constructCollectionType(List.class, clazz);
        }
    }

    public enum FileTypes {
        JSON,
        XML,
        OBJ,
        SQL,
        SQL_ORM
    }
}
