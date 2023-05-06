package com.tll.gui.data;

import com.tll.backend.datastore.DataStore;
import com.tll.backend.datastore.loader.JsonAdapter;
import com.tll.backend.datastore.loader.ObjAdapter;
import com.tll.backend.datastore.loader.XmlAdapter;
import com.tll.backend.datastore.loader.hibernate.HibernateAdapter;
import com.tll.backend.datastore.loader.hibernate.HibernateDataStore;
import com.tll.backend.datastore.loader.hikari.HikariConfig;
import com.tll.backend.datastore.loader.sql.SqlAdapter;
import com.tll.backend.repository.StorableObject;
import com.tll.backend.repository.impl.InMemoryCrudRepository;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataHandler {

    private static final String FILE_PATH = "src/main/resources/data/json/";

    public static <V extends StorableObject<?>> void save(@NotNull InMemoryCrudRepository<?, V> repository, @NotNull String name, FileTypes fileType) throws IOException {
        try (HikariConfig config = HikariConfig.INSTANCE) {
            DataStore dataStore = getAppropriateDataAdapter(name, fileType, config);
            List<V> objects = new ArrayList<>();
            repository.findAll().forEach(objects::add);
            dataStore.save(objects);
        }
    }

    public static <ID extends Comparable<ID>, V extends StorableObject<ID>> InMemoryCrudRepository<ID, V> load(Class<V> clazz, @NotNull String name, FileTypes fileType) throws IOException {
        try (HikariConfig config = HikariConfig.INSTANCE) {
            DataStore dataStore = getAppropriateDataAdapter(name, fileType, config);
            List<V> objects = dataStore.load(clazz);
            InMemoryCrudRepository<ID, V> repository = new InMemoryCrudRepository<>(new HashMap<ID, V>());
            objects.forEach(repository::save);
            return repository;
        }
    }

    private static DataStore getAppropriateDataAdapter(@NotNull String name, FileTypes fileType, HikariConfig config) {
        return switch (fileType) {
            case JSON -> new JsonAdapter(FILE_PATH + name + ".json");
            case XML -> new XmlAdapter(FILE_PATH + name + ".xml");
            case OBJ -> new ObjAdapter(FILE_PATH + name + ".obj");
            case SQL -> new SqlAdapter(config);
            case SQL_ORM -> new HibernateAdapter(new HibernateDataStore());
        };
    }


    public enum FileTypes {
        JSON,
        XML,
        OBJ,
        SQL,
        SQL_ORM
    }
}
