package com.tll.gui.data;

import com.tll.backend.datastore.DataStore;
import com.tll.backend.datastore.loader.JsonAdapter;
import com.tll.backend.datastore.loader.ObjAdapter;
import com.tll.backend.datastore.loader.XmlAdapter;
import com.tll.backend.datastore.loader.hibernate.HibernateAdapter;
import com.tll.backend.datastore.loader.hibernate.HibernateDataStore;
import com.tll.backend.datastore.loader.hikari.HikariConfig;
import com.tll.backend.datastore.loader.sql.SqlAdapter;
import com.tll.backend.model.barang.Barang;
import com.tll.backend.model.bill.FixedBill;
import com.tll.backend.model.bill.TemporaryBill;
import com.tll.backend.model.user.Customer;
import com.tll.backend.model.user.Member;
import com.tll.backend.repository.StorableObject;
import com.tll.backend.repository.impl.InMemoryCrudRepository;
import com.tll.backend.repository.impl.barang.BarangRepository;
import com.tll.backend.repository.impl.bill.FixedBillRepository;
import com.tll.backend.repository.impl.bill.TemporaryBillRepository;
import com.tll.backend.repository.impl.user.CustomerRepository;
import com.tll.backend.repository.impl.user.MemberRepository;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class DataHandler implements AutoCloseable {

    private static final String FILE_PATH = getFolderPath();
    private static HikariConfig connection = null;

    public <V extends StorableObject<?>> void save(@NotNull InMemoryCrudRepository<?, V> repository, @NotNull String name, FileTypes fileType) throws IOException {
        DataStore dataStore = getAppropriateDataAdapter(name, fileType);
        List<V> objects = new ArrayList<>();
        repository.findAll().forEach(objects::add);
        dataStore.save(objects);
    }

    @SuppressWarnings("unchecked")
    public <T extends InMemoryCrudRepository<ID, V>, ID extends Comparable<ID>, V extends StorableObject<ID>> T load(Class<T> repositoryClass, @NotNull String name, FileTypes fileType) throws IOException {
        DataStore dataStore = getAppropriateDataAdapter(name, fileType);

        T repository;
        try {
            repository = repositoryClass.getConstructor().newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        Class<?> clazz;
        if (repositoryClass.equals(BarangRepository.class)) {
            clazz = Barang.class;
        } else if (repositoryClass.equals(FixedBillRepository.class)) {
            clazz = FixedBill.class;
        } else if (repositoryClass.equals(TemporaryBillRepository.class)) {
            clazz = TemporaryBill.class;
        } else if (repositoryClass.equals(CustomerRepository.class)) {
            clazz = Customer.class;
        } else if (repositoryClass.equals(MemberRepository.class)) {
            clazz = Member.class;
        } else {
            throw new IllegalArgumentException("Invalid parameter");
        }

        List<V> objects = (List<V>) dataStore.load(clazz);
        objects.forEach(repository::save);
        return repository;
    }

    private DataStore getAppropriateDataAdapter(@NotNull String name, FileTypes fileType) {
        return switch (fileType) {
            case JSON -> new JsonAdapter(FILE_PATH + name + ".json");
            case XML -> new XmlAdapter(FILE_PATH + name + ".xml");
            case OBJ -> new ObjAdapter(FILE_PATH + name + ".obj");
            case SQL -> new SqlAdapter(connection = HikariConfig.INSTANCE);
            case SQL_ORM -> new HibernateAdapter(new HibernateDataStore());
        };
    }

    private static String getFolderPath() {
        String path = DataHandler.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String finalPath = path.substring(0, path.lastIndexOf("/")) + "/data/";
        File file = new File(finalPath);
        if (!file.exists()) {
            if (file.mkdir()) {
                return finalPath;
            }
            throw new RuntimeException("Failed to create data folder");
        }
        return finalPath;
    }

    @Override
    public void close() {
        if (connection != null) {
            connection.close();
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