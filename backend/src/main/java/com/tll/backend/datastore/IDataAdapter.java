package com.tll.backend.datastore;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public interface IDataAdapter<T> {
    T convert();
    default void save(String fileName, T object) {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
            oos.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
