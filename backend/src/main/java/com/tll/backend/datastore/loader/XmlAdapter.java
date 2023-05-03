package com.tll.backend.datastore.loader;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.tll.backend.datastore.DataStore;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.util.List;

@AllArgsConstructor
public class XmlAdapter implements DataStore {
    @Setter
    private String fileName;

    @Override
    public void save(List<?> objects) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        xmlMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        xmlMapper.writeValue(new File(fileName), objects);
    }

    @Override
    public <T> List<T> load(final Class<T> clazz) throws IOException {
        JavaType javaType = TypeFactory.defaultInstance().constructCollectionType(List.class, clazz);
        return new XmlMapper().readValue(new File(fileName), javaType);
    }
}
