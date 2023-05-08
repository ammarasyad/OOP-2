package com.tll.backend.datastore.loader.helper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.tll.backend.model.bill.Bill;

import java.io.IOException;

public class BillSerializer extends StdSerializer<Bill> {

    public BillSerializer() {
        this(null);
    }

    protected BillSerializer(Class<Bill> t) {
        super(t);
    }

    @Override
    public void serialize(Bill bill, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", bill.getId());

        jsonGenerator.writeFieldName("cart");
        jsonGenerator.writeStartArray();
        bill.getCart().forEach(el -> {
            try {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeObjectField("barang", el.getValue0());
                jsonGenerator.writeNumberField("jumlah", el.getValue1());
                jsonGenerator.writeEndObject();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
}
