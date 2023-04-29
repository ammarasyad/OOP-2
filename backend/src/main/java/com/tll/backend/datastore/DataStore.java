package com.tll.backend.datastore;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.tll.backend.model.barang.Barang;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

public class DataStore {

    public static void saveToJson(@NotNull Serializable object, String fileName) throws IOException {
        save(object, fileName, FileTypes.JSON);
    }

    public static void saveToXml(@NotNull Serializable object, String fileName) throws IOException {
        save(object, fileName, FileTypes.XML);
    }

    public static void saveToObj(@NotNull Serializable object, String fileName) throws IOException {
        save(object, fileName, FileTypes.OBJ);
    }

    private static void save(@NotNull Serializable object, String fileName, FileTypes fileType) throws IOException {
        switch (fileType) {
            case JSON:
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
                objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
                objectMapper.writeValue(new File(fileName), object);
                break;
            case XML:
                XmlMapper xmlMapper = new XmlMapper();
                xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
                xmlMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
                xmlMapper.writeValue(new File(fileName), object);
                break;
            case OBJ:
                try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
                    oos.writeObject(object);
                }
                break;
            case SQL:
            case SQL_ORM:
                // to be implemented
                break;
        }
    }

    /**
     * Loads data from a file.
     *
     * @param fileName The name of the file to load. Must include the file extension. Extension will be used to infer the file type.
     * @param fileType The <b>expected</b> type of file to load.
     *                 Can be anything from {@link FileTypes}.
     *                 If the file format is mismatched, it will be converted to the expected type.
     * @throws IOException              If an I/O error occurs.
     * @throws FileNotFoundException    If the file does not exist, is a directory rather than a regular file, or for some other reason cannot be opened for reading.
     * @throws IllegalArgumentException If the file type is not supported.
     */
    public static Object load(String fileName, FileTypes fileType) throws IOException, FileNotFoundException, IllegalArgumentException {
        // TODO: Implement load
        return null;
    }

    public enum FileTypes {
        JSON,
        XML,
        OBJ,
        SQL,
        SQL_ORM
    }
}
