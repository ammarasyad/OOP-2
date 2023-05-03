package com.tll.backend.datastore.loader;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.tll.backend.datastore.DataStore;
import com.tll.backend.datastore.loader.helper.BillMixin;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.javatuples.Pair;

import java.io.File;
import java.io.IOException;
import java.util.List;

@AllArgsConstructor
public class JsonAdapter implements DataStore {
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
    public <T> List<T> load(final Class<T> clazz) throws IOException {
        JavaType javaType = TypeFactory.defaultInstance().constructCollectionType(List.class, clazz);
        return new JsonMapper().addMixIn(Pair.class, BillMixin.class).readValue(new File(fileName), javaType);
    }
}
