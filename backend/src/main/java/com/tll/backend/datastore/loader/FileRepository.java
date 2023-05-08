package com.tll.backend.datastore.loader;

import com.tll.backend.datastore.DataStore;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

public class FileRepository {

    /**
     * Saves data to a file.
     *
     * @param object   The object to be saved.
     * @param dataStore The adapter to be used to save the file.
     * @throws IOException If the file type is not supported.
     */
    public static void save(@NotNull List<?> object, DataStore dataStore) throws IOException {
        dataStore.save(object);
    }

    /**
     * Loads data from a file.
     *
     * @param clazz    The class of the objects to be loaded. The class must have a default constructor.
     * @param dataStore The adapter to be used to load the file.
     * @throws IOException If the file type is not supported.
     */
    public static <T> List<T> load(@NotNull Class<T> clazz, DataStore dataStore) throws IOException {
        return dataStore.load(clazz);
    }
}
