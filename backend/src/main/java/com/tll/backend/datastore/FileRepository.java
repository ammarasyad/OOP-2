package com.tll.backend.datastore;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.tll.backend.datastore.loader.JsonDataLoader;
import com.tll.backend.datastore.loader.ObjDataLoader;
import com.tll.backend.datastore.loader.XmlDataLoader;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

public class FileRepository {

    /**
     * Saves data to a file.
     *
     * @param object   The object to be saved.
     * @param fileName The name of the file to be saved.
     * @param fileType The type of the file to be saved.
     * @throws IOException If the file type is not supported.
     */
    public static void save(@NotNull List<?> object, String fileName, FileTypes fileType) throws IOException {
        switch (fileType) {
            case JSON -> {
                JsonDataLoader jsonDataLoader = new JsonDataLoader(fileName);
                jsonDataLoader.save(object);
            }
            case XML -> {
                XmlDataLoader xmlDataLoader = new XmlDataLoader(fileName);
                xmlDataLoader.save(object);
            }
            case OBJ -> {
                ObjDataLoader objDataLoader = new ObjDataLoader(fileName);
                objDataLoader.save(object);
            }
            case SQL, SQL_ORM -> {
                // not yet implemented
            }
            default -> throw new IOException("File type not supported");
        }
    }

    /**
     * Loads data from a file.
     *
     * @param clazz    The class of the objects to be loaded. The class must have a default constructor.
     * @param fileName The name of the file to be loaded.
     * @param fileType The type of the file to be loaded.
     * @throws IOException If the file type is not supported.
     */
    public static <T> List<T> load(@NotNull Class<T> clazz, String fileName, FileTypes fileType) throws IOException {
        switch (fileType) {
            case JSON -> {
                JsonDataLoader jsonDataLoader = new JsonDataLoader(fileName);
                return jsonDataLoader.load(CollectionsTypeFactory.listOf(clazz));
            }
            case XML -> {
                XmlDataLoader xmlDataLoader = new XmlDataLoader(fileName);
                return xmlDataLoader.load(CollectionsTypeFactory.listOf(clazz));
            }
            case OBJ -> {
                ObjDataLoader objDataLoader = new ObjDataLoader(fileName);
                return objDataLoader.load(CollectionsTypeFactory.listOf(clazz));
            }
            case SQL, SQL_ORM -> {
                // not yet implemented
                return null;
            }
            default -> throw new IOException("File type not supported");
        }
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
