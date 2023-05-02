package com.tll.backend.datastore.loader;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.tll.backend.datastore.DataAdapter;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.util.List;

@AllArgsConstructor
public class JsonAdapter implements DataAdapter {
    @Setter
    private String fileName;

    @Override
    public void save(List<?> objects) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        objectMapper.writeValue(new File(fileName), objects);
    }

    @Override
    public <T> List<T> load(final JavaType clazz) throws IOException {
        return new JsonMapper().readValue(new File(fileName), clazz);
    }
}
