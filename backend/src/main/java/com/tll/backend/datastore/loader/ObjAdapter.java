package com.tll.backend.datastore.loader;

import com.fasterxml.jackson.databind.JavaType;
import com.tll.backend.datastore.DataStore;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class ObjAdapter implements DataStore {
    @Setter
    private String fileName;

    @Override
    public void save(List<?> objects) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
            oos.writeObject(objects);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> load(final JavaType clazz) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))) {
            return new ArrayList<>((List<T>) ois.readObject());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
