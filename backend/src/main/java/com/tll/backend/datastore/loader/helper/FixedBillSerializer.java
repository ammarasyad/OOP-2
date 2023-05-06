package com.tll.backend.datastore.loader.helper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.tll.backend.model.bill.FixedBill;

import java.io.IOException;

public class FixedBillSerializer extends StdSerializer<FixedBill> {

    public FixedBillSerializer() {
        this(null);
    }

    protected FixedBillSerializer(Class<FixedBill> t) {
        super(t);
    }

    @Override
    public void serialize(FixedBill fixedBill, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", fixedBill.getId());
        jsonGenerator.writeNumberField("userId", fixedBill.getUserId());

        jsonGenerator.writeFieldName("cart");
        jsonGenerator.writeStartArray();
        fixedBill.getCart().forEach(el -> {
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
